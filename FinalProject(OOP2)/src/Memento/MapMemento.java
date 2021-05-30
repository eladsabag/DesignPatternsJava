/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Memento;

import java.util.Map;
import Model.Product;

//memento
public abstract class MapMemento {
	protected Map<String, Product> mementoMap;

	public Map<String, Product> getMemento() {
		return mementoMap;
	}

}
