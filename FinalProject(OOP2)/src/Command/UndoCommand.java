/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import Memento.MapMemento;
import Model.Store;

public class UndoCommand implements Command {
	private Store store;
	private MapMemento memento;

	// constructor
	public UndoCommand(Store store, MapMemento memento) {
		this.store = store;
		this.memento = memento;
	}

	// command
	@Override
	public void execute() {
		store.setAllProduct(memento.getMemento());
		store.setMemento(null);
		store.getReceivers().clear();
		store.setTotalProfit(0);
		store.writeMapToFile();
		store.readMapFromFile();
	}

}
