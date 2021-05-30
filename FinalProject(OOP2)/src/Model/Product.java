/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Model;

public class Product {
	private String productName;
	private int supplierPrice;
	private int storePrice;
	private Client client;

	// constructor
	public Product(String productName, int supplierPrice, int storePrice, Client client) {
		this.productName = productName;
		this.supplierPrice = supplierPrice;
		this.storePrice = storePrice;
		this.client = client;
	}

	// setters & getters
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(int storePrice) {
		this.storePrice = storePrice;
	}

	public int getSupplierPrice() {
		return supplierPrice;
	}

	public void setSupplierPrice(int supplierPrice) {
		this.supplierPrice = supplierPrice;
	}

	public Client getClient() {
		return client;
	}

	public void setBuyer(Client client) {
		this.client = client;
	}

	// toString
	@Override
	public String toString() {
		return "Product Name: " + productName + " \nSupplier Price: " + supplierPrice + "\nStore Price: " + storePrice
				+ "\nClient Details: Name - " + client.getName() + " , Mobile - " + client.getMobileNumber()
				+ " , Notification - " + client.isNotifications() + ".\n\n";
	}

}
