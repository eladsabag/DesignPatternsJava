/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import java.util.Iterator;
import java.util.Map.Entry;
import Model.Product;
import Model.Store;

public class RemoveProductCommand implements Command {
	private Store store;
	private String catalogNumber;

	// constructor
	public RemoveProductCommand(Store store, String key) {
		this.store = store;
		catalogNumber = key;
	}

	// command
	@Override
	public void execute() {
		Iterator<Entry<String, Product>> it = store.iterator();
		Entry<String, Product> en;
		while (it.hasNext()) {
			en = it.next();
			if (catalogNumber.equals(en.getKey())) {
				it.remove();
			}
		}
		store.readMapFromFile();
	}

}
