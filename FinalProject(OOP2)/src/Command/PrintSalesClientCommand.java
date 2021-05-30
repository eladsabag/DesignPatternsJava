/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import Model.Store;
import View.AbstractStoreView;

public class PrintSalesClientCommand implements Command {
	private Store store;
	private AbstractStoreView view;

	// constructor
	public PrintSalesClientCommand(Store store, AbstractStoreView view) {
		this.store = store;
		this.view = view;
	}

	// command
	@Override
	public void execute() {
		view.printSalesList(store.getNames());
	}

}
