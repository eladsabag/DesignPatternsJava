/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Memento;

import java.util.LinkedHashMap;
import Model.Product;

public class LinkedHashMapMemento extends MapMemento {

	// constructor
	public LinkedHashMapMemento(LinkedHashMap<String, Product> productsMap) {
		mementoMap = new LinkedHashMap<>();
		if (productsMap != null) {
			if (!productsMap.isEmpty())
				mementoMap.putAll(productsMap);
		}
	}

	public LinkedHashMap<String, Product> getMemento() {
		return (LinkedHashMap<String, Product>) mementoMap;
	}

}
