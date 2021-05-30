/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import Model.Product;
import Model.Store;
import View.AbstractStoreView;

public class PrintProductsMapCommand implements Command {
	private AbstractStoreView theView;
	private Store store;
	private Product product;

	// constructor
	public PrintProductsMapCommand(Store store, AbstractStoreView view) {
		this.store = store;
		theView = view;
	}

	// command
	@Override
	public void execute() {
		Set<Entry<String, Product>> set = store.getAllProducts().entrySet();
		Iterator<Entry<String, Product>> it = set.iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, Product> en = it.next();
			product = en.getValue();
			sb.append("Details of Product " + en.getKey() + ":\n" + product.toString());
		}
		theView.printMap(sb.toString());
	}

}
