/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package View;

import java.util.ArrayList;
import Listeners.UIEventsListener;
import Model.Product;

public interface AbstractStoreView {
	// UI
	void setSavingChoice(int savingChoice);
	boolean addProduct(String catalogNumber, String productName, int supplierPrice, int storePrice, String name,
			String mobileNumber, boolean notifications);			
	boolean showSpecificProduct(String catalogNumber);
	void showProfitOfProduct(Product product, int profit);	
	boolean printProductsMap();
	void removeAllProducts();
	boolean PrintSaleClients();
	boolean removeOneProduct(String catalogNumber);
	void sendUpdates(String msg);
	void showStoreProfits(int totalProfits);
	void undo();
	void registerListener(UIEventsListener listener);

	// Events
	void actionFailed(String msg);
	void actionCompleted(String msg);
	void illegalPrice(String msg);
	void invalidNumber(String msg);
	void emptyDetails(String string);
	void productNotFound(String string);
	void mapIsEmpty(String string);

	// Methods
	void printProduct(String p);
	void printMap(String string);
	void savingChoice(int savingChoice);
	void storeProfits();
	boolean productProfit(String catalogNumber);
	void printSalesList(ArrayList<String> names);
	void show(int savingChoice);
	boolean mapIsEmpty();
	boolean isKeyExist(String key);
	void setMessage(String string);
}

