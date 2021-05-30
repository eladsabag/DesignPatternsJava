/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import Model.Store;
import View.AbstractStoreView;

public class SavingChoiceCommand implements Command {
	private Store store;
	private int savingChoice;
	private AbstractStoreView view;

	// constructor
	public SavingChoiceCommand(Store store, int savingChoice, AbstractStoreView theView) {
		this.store = store;
		this.savingChoice = savingChoice;
		this.view = theView;
	}

	// command
	@Override
	public void execute() {
		store.setSavingChoice(savingChoice);
		view.savingChoice(savingChoice);
	}

}
