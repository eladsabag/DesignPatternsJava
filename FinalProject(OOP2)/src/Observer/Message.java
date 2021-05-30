/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
	private String msg;
	private LocalDateTime sent;

	// constructor
	public Message(String msg) {
		this.msg = msg;
		this.sent = LocalDateTime.now();
	}

	// setters & getters
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setSent(LocalDateTime sent) {
		this.sent = sent;
	}

	public LocalDateTime getSent() {
		return sent;
	}

	public String getTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return sent.format(formatter);
	}

	// toString
	@Override
	public String toString() {
		return String.format("Date: %s\nMessage: %s\n", getTime(), msg);
	}

}
