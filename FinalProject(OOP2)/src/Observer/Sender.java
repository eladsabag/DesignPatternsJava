/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Observer;

//observable
public interface Sender {
	void sendMSG(Receiver r, String msg);
}
