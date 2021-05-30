/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import Model.Product;
import Model.Store;

public class AddProductCommand implements Command {
	private Store store;
	private Product product;
	private String catalogNumber;

	// constructor
	public AddProductCommand(Store store, String catalogNumber, Product product) {
		this.store = store;
		this.catalogNumber = catalogNumber;
		this.product = product;
	}

	// command
	@Override
	public void execute() {
		store.save();
		store.addProduct(catalogNumber, product);
		store.writeMapToFile();
	}

}
