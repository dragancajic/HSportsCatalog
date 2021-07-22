package org.eu5.learn_pisio.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CatalogServlet
 */
// support for async
@WebServlet(urlPatterns = "/CatalogServlet", asyncSupported = true)
public class CatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatalogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append(request.getParameter("name"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		// ~ Async support for servlets
		
		// We get a request in and that's handled in a Thread 1.
		// We then use the AsyncContext to create Thread 2 which is going to respond to the request.
		
		// async variable
		AsyncContext asyncContext = request.startAsync();
		
		// start a new thread (Thread 2)
		asyncContext.start(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					System.out.println("Print the response");
					System.out.println("Response returned by: " + Thread.currentThread().getName()); // Thread 2
					returnResponse(request, response);
					asyncContext.complete();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		// additional logging
		System.out.println("Initial Request: " + Thread.currentThread().getName()); // Thread 1
		
		//returnResponse(request, response);
		
		// ~ So, this is a look at async support for servlets and we can leverage this when we need
		// to conserve resources on our servlet container.
	}

	private void returnResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// collect information from the HTTP request [get parameters from the form] 
		String name = request.getParameter("name");
		String manufacturer = request.getParameter("manufacturer");
		String sku = request.getParameter("sku");
		
		// add an item to the catalog
		Catalog.addItem(new CatalogItem(name, manufacturer, sku));
		
		// ~ generate response back to the browser
		
		// set header and add cookie
		response.setHeader("someHeader", "someHeaderValue");
		response.addCookie(new Cookie("someCookie", "someCookieValue"));
		
		// start generating the actual response body
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("\t<meta charset=\"utf-8\" />");
		out.println("\t<title>H+ Sport Catalog</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("\t<table>");
		
		for (CatalogItem item : Catalog.getItems()) {
			out.println("\t\t<tr>");
			out.println("\t\t\t<td>");
			out.println("\t\t\t\t" + item.getName());
			out.println("\t\t\t</td>");
			out.println("\t\t</tr>");
		}
		
		out.println("\t</table>");
		out.println("</body>");
		out.println("</html>");
	}

}
