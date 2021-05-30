/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Command;

import Model.Store;
import Observer.Message;
import Observer.Receiver;
import View.AbstractStoreView;

public class SendSalesCommand implements Command {
	private Store store;
	private Message msg;
	private AbstractStoreView view;

	// constructor
	public SendSalesCommand(Message msg, AbstractStoreView view) {
		store = Store.getInstance();
		this.msg = msg;
		this.view = view;
	}

	// command
	@Override
	public void execute() {
		store.getNames().clear();
		for (Receiver r : store.getReceivers())
			store.sendMSG(r, msg.getMsg());
		view.setMessage(msg.toString());
	}

}
