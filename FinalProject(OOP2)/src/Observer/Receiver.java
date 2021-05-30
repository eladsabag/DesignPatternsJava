/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Observer;

//observer
public interface Receiver {
	void receiveMSG(Sender s, Message msg);
}
