/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Model;

import java.util.Vector;
import Command.AddProductCommand;
import Command.Command;
import Command.PrintProductsMapCommand;
import Command.ShowSpecificProductCommand;
import Command.UndoCommand;
import Command.SendSalesCommand;
import Command.PrintSalesClientCommand;
import Command.RemoveProductCommand;
import Command.RemoveAllProductsCommand;
import Command.SavingChoiceCommand;
import Command.ShowProfitOfProductCommand;
import Command.ProfitOfAllProductsCommand;
import Listeners.EventsListener;
import Observer.Message;
import View.AbstractStoreView;

public class Model {
	public static final int ALPHABETICAL = 1;
	public static final int REVERSED_ALPHABETICAL = 2;
	public static final int ORDER_INSERTION = 3;
	private Store store;
	private Command cmd;
	private Vector<EventsListener> listeners;

	// constructor
	public Model() {
		listeners = new Vector<EventsListener>();
		store = Store.getInstance();
		readProducts();
	}

	// setters & getters
	public int getSavingChoice() {
		return store.getSavingChoice();
	}

	public void setSavingChoice(int choice, AbstractStoreView view) {
		if (choice != 1 && choice != 2 && choice != 3) {
			choice = 1;
		}
		cmd = new SavingChoiceCommand(store, choice, view);
		cmd.execute();
	}

	// command
	public boolean addProduct(String catalogNumber, String productName, int supplierPrice, int storePrice, String name,
			String mobileNumber, boolean notifications) {
		if (catalogNumber.isEmpty() || productName.isEmpty() || mobileNumber.isEmpty()) {
			fireEmptyDetails();
			return false;
		} else if (catalogNumber.startsWith(" ") || productName.startsWith(" ") || mobileNumber.startsWith(" ")) {
			fireEmptyDetails();
			return false;
		} else if (storePrice < 0 || supplierPrice < 0) {
			fireIllegalPrice();
			return false;
		}
		boolean isOK = true;
		for (int i = 0; i < mobileNumber.length(); i++) {
			if (!Character.isDigit(mobileNumber.charAt(i))) {
				isOK = false;
				break;
			}
		}
		if (!isOK) {
			fireInvalidMobileNumber();
			return false;
		} else {
			Client c = new Client(name, mobileNumber, notifications);
			Product p = new Product(productName, supplierPrice, storePrice, c);
			cmd = new AddProductCommand(store, catalogNumber, p);
			cmd.execute();
			fireActionCompleted();
			return true;
		}
	}

	public boolean showSpecificProduct(String catalogNumber, AbstractStoreView view) {
		if (catalogNumber == null || catalogNumber.isEmpty()) {
			fireEmptyDetails();
			return false;
		} else if (store.getAllProducts().get(catalogNumber) == null) {
			fireProductNotFound();
			return false;
		} else {
			cmd = new ShowSpecificProductCommand(store, catalogNumber, view);
			cmd.execute();
			return true;
		}
	}

	public boolean PrintProductsMap(AbstractStoreView view) {
		if (store.getAllProducts().isEmpty()) {
			fireMapIsEmpty();
			return false;
		} else {
			cmd = new PrintProductsMapCommand(store, view);
			cmd.execute();
			return true;
		}
	}

	public void undo() {
		cmd = new UndoCommand(store, store.getMemento());
		cmd.execute();
		fireActionCompleted();
	}

	public void sendSales(String saleMsg, AbstractStoreView view) {
		if (saleMsg == null || saleMsg.isEmpty())
			fireEmptyDetails();
		else if (store.getReceivers().isEmpty())
			fireActionFailed();
		else {
			cmd = new SendSalesCommand(new Message(saleMsg), view);
			cmd.execute();
			fireActionCompleted();
		}
	}

	public boolean printSalesClient(AbstractStoreView view) {
		if (store.getNames().isEmpty()) {
			fireActionFailed();
			return false;
		} else {
			cmd = new PrintSalesClientCommand(store, view);
			cmd.execute();
			return true;
		}
	}

	public boolean removeProduct(String catalogNumber) {
		if (catalogNumber == null || catalogNumber.isEmpty()) {
			fireEmptyDetails();
			return false;
		} else if (store.getAllProducts().get(catalogNumber) == null) {
			fireProductNotFound();
			return false;
		} else {
			cmd = new RemoveProductCommand(store, catalogNumber);
			cmd.execute();
			fireActionCompleted();
			return true;
		}
	}

	public void removeAllProducts() {
		if (store.getAllProducts().isEmpty()) {
			fireMapIsEmpty();
		} else {
			cmd = new RemoveAllProductsCommand(store);
			cmd.execute();
			fireActionCompleted();
		}
	}

	public void storeProfits(AbstractStoreView view) {
		if (store.getAllProducts().isEmpty()) {
			fireMapIsEmpty();
		} else {
			cmd = new ProfitOfAllProductsCommand(store, view);
			cmd.execute();
		}
	}

	public boolean showProductProfits(String catalogNumber, AbstractStoreView view) {
		catalogNumber = catalogNumber.trim();
		if (catalogNumber.isEmpty() || catalogNumber.equals("")) {
			fireEmptyDetails();
			return false;
		} else if (store.getAllProducts().get(catalogNumber) == null) {
			fireProductNotFound();
			return false;
		} else {
			cmd = new ShowProfitOfProductCommand(store, view, catalogNumber);
			cmd.execute();
			return true;
		}
	}

	public boolean readProducts() {
		store.readMapFromFile();
		if (store.getAllProducts() == null || store.getAllProducts().isEmpty())
			return false;
		return true;
	}

	public boolean isMapEmpty() {
		if (store.getAllProducts() == null || store.getAllProducts().isEmpty()) {
			fireMapIsEmpty();
			return true;
		}
		return false;
	}

	public boolean isKeyExist(String key) {
		Product p = store.getAllProducts().get(key);
		if (p == null)
			return false;
		return true;
	}

	public void registerListener(EventsListener listener) {
		listeners.add(listener);
	}

	// EventListener
	public void fireActionFailed() {
		for (EventsListener l : listeners)
			l.fireActionFailed();
	}

	public void fireActionCompleted() {
		for (EventsListener l : listeners)
			l.fireActionCompleted();
	}

	public void fireIllegalPrice() {
		for (EventsListener l : listeners)
			l.fireIllegalPrice();
	}

	public void fireEmptyDetails() {
		for (EventsListener l : listeners)
			l.fireEmptyDetails();
	}

	public void fireProductNotFound() {
		for (EventsListener l : listeners)
			l.fireProductNotFound();
	}

	public void fireMapIsEmpty() {
		for (EventsListener l : listeners)
			l.fireMapIsEmpty();
	}

	public void fireInvalidMobileNumber() {
		for (EventsListener l : listeners)
			l.fireInvalidMobileNumber();
	}

}
