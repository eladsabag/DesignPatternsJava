/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import Model.Product;
import Model.Store;
import View.AbstractStoreView;

public class ShowSpecificProductCommand implements Command {
	private AbstractStoreView theView;
	private Store store;
	private String catalogNumber;

	// constructor
	public ShowSpecificProductCommand(Store store, String catalogNumber, AbstractStoreView view) {
		this.store = store;
		this.catalogNumber = catalogNumber;
		this.theView = view;
	}

	// command
	@Override
	public void execute() {
		Product p = store.getAllProducts().get(catalogNumber);
		theView.printProduct(p.toString());
	}

}
