/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Model;

import Observer.Message;
import Observer.Receiver;
import Observer.Sender;

public class Client implements Sender, Receiver {
	private String name;
	private String mobileNumber;
	private boolean notifications;

	// constructor
	public Client(String name, String mobileNumber, boolean notifications) {
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.notifications = notifications;
	}

	// setters & getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setNotifications(boolean notifications) {
		this.notifications = notifications;
	}

	public boolean isNotifications() {
		return notifications;
	}

	// equals
	public boolean equals(Client c) {
		return mobileNumber.equals(c.mobileNumber);
	}

	// observer
	@Override
	public void receiveMSG(Sender s, Message msg) {
		sendMSG((Receiver) s, name);
	}

	@Override
	public void sendMSG(Receiver r, String msg) {
		if (r instanceof Store)
			r.receiveMSG(this, new Message(msg));
	}

}
