package org.eu5.learn_pisio.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class CatalogItemFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// a catalog item that we're going to bind the form to
	private CatalogItem item = new CatalogItem();
	
	// catalog, which will contain all the items, so when we do create
	// a new item with the form we're going to add it to this list
	private List<CatalogItem> items = new ArrayList<>();

	// Instead of navigating to a static Facelet, we've defined a new method:
	public String addItem() {
		long itemId = this.items.size() + 1;
		
		this.items.add(new CatalogItem(itemId, this.item.getName(), this.item.getManufacturer(),
				this.item.getDescription(), this.item.getAvailableDate()));
		
		this.items.stream().forEach(item -> {
			System.out.println(item.toString());
		});
		
		return "list?faces-redirect=true"; // name of facelet is list!
	}
	// This method's going to return a String, name of that String is going to be
	// the name of the Facelet that we'll navigate to once this method's logic is
	// executed.
	
	public CatalogItem getItem() {
		return item;
	}

	public void setItem(CatalogItem item) {
		this.item = item;
	}

	public List<CatalogItem> getItems() {
		return items;
	}

	public void setItems(List<CatalogItem> items) {
		this.items = items;
	}
}
