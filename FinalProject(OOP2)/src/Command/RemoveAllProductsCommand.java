/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import java.util.Iterator;
import java.util.Map.Entry;
import Model.Product;
import Model.Store;

public class RemoveAllProductsCommand implements Command {
	private Store store;

	// constructor
	public RemoveAllProductsCommand(Store store) {
		this.store = store;
	}

	// command
	@Override
	public void execute() {
		Iterator<Entry<String, Product>> it = store.iterator();
		Entry<String, Product> en;
		while (it.hasNext()) {
			en = it.next();
			it.remove();
		}
		store.readMapFromFile();
	}

}
