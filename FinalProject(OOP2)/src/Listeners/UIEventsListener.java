/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Listeners;

public interface UIEventsListener {
	void setSavingChoice(int savingChoice);
	boolean addProduct(String catalogNumber, String productName, int supplierPrice, int storePrice, String name,
			String mobileNumber, boolean notifications);
	boolean showSpecificProduct(String catalogNumber);
	void undo();
	boolean printProductsMap();
	void removeAllProducts();
	boolean removeOneProduct(String catalogNumber);
	void storeProfits();
	void sendUpdates(String msg);
	boolean PrintSaleClients();
	boolean productProfit(String catalogNumber);
	boolean isMapEmpty();
	boolean isKeyExist(String key);
}
