/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Memento;

import java.util.Comparator;
import java.util.TreeMap;

import Model.Product;

public class TreeMapMemento extends MapMemento {

	// constructor
	public TreeMapMemento(TreeMap<String, Product> productsMap, Comparator<String> comparator) {
		mementoMap = new TreeMap<>(comparator);
		mementoMap.putAll(productsMap);
	}

	public TreeMap<String, Product> getMemento() {
		if (mementoMap.isEmpty())
			return null;
		return (TreeMap<String, Product>) mementoMap;
	}

}
