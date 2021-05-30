/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Controller;

import Listeners.EventsListener;
import Listeners.UIEventsListener;
import Model.Model;
import View.AbstractStoreView;

public class Controller implements EventsListener, UIEventsListener {
	private Model theModel;
	private AbstractStoreView theView;

	// constructor
	public Controller(Model theModel, AbstractStoreView theView) {
		this.theModel = theModel;
		this.theView = theView;
		theModel.registerListener(this);
		theView.registerListener(this);
		theView.show(theModel.getSavingChoice());
	}

	// UIEventListener
	@Override
	public void setSavingChoice(int savingChoice) {
		theModel.setSavingChoice(savingChoice, theView);
	}

	@Override
	public boolean addProduct(String catalogNumber, String productName, int supplierPrice, int storePrice, String name,
			String mobileNumber, boolean notifications) {
		return theModel.addProduct(catalogNumber, productName, supplierPrice, storePrice, name, mobileNumber,
				notifications);
	}

	@Override
	public boolean showSpecificProduct(String catalogNumber) {
		return theModel.showSpecificProduct(catalogNumber, theView);
	}

	@Override
	public boolean printProductsMap() {
		return theModel.PrintProductsMap(theView);
	}

	@Override
	public void undo() {
		theModel.undo();
	}

	@Override
	public void removeAllProducts() {
		theModel.removeAllProducts();
	}

	@Override
	public boolean removeOneProduct(String catalogNumber) {
		return theModel.removeProduct(catalogNumber);
	}

	@Override
	public void storeProfits() {
		theModel.storeProfits(theView);
	}

	@Override
	public void sendUpdates(String msg) {
		theModel.sendSales(msg, theView);
	}

	@Override
	public boolean PrintSaleClients() {
		return theModel.printSalesClient(theView);
	}

	@Override
	public boolean productProfit(String catalogNumber) {
		return theModel.showProductProfits(catalogNumber, theView);
	}

	@Override
	public boolean isMapEmpty() {
		return theModel.isMapEmpty();
	}

	@Override
	public boolean isKeyExist(String key) {
		return theModel.isKeyExist(key);
	}

	// EventListener
	@Override
	public void fireActionCompleted() {
		theView.actionCompleted("Action Completed!");
	}

	@Override
	public void fireActionFailed() {
		theView.actionFailed("Action Failed!");
	}

	@Override
	public void fireIllegalPrice() {
		theView.illegalPrice("There is Illegal price. Please try again!");
	}

	@Override
	public void fireEmptyDetails() {
		theView.emptyDetails("A field cannot contain empty details.Please try Again!");
	}

	@Override
	public void fireProductNotFound() {
		theView.productNotFound("Product not found...");
	}

	@Override
	public void fireMapIsEmpty() {
		theView.mapIsEmpty("Map is empty...");
	}

	@Override
	public void fireInvalidMobileNumber() {
		theView.invalidNumber("Invalid phone number...");
	}

}
