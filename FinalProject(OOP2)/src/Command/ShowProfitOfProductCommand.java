/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import Model.Product;
import Model.Store;
import View.AbstractStoreView;

public class ShowProfitOfProductCommand implements Command {
	private Store store;
	private AbstractStoreView view;
	private String catalogNumber;

	// constructor
	public ShowProfitOfProductCommand(Store store, AbstractStoreView view, String catalogNumber) {
		this.store = store;
		this.view = view;
		this.catalogNumber = catalogNumber;
	}

	// command
	@Override
	public void execute() {
		Product p = store.getAllProducts().get(catalogNumber);
		view.showProfitOfProduct(p, p.getStorePrice() - p.getSupplierPrice());
	}

}
