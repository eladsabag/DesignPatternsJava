/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Listeners;

public interface EventsListener {
	void fireActionCompleted();
	void fireActionFailed();
	void fireIllegalPrice();
	void fireEmptyDetails();
	void fireProductNotFound();
	void fireMapIsEmpty();
	void fireInvalidMobileNumber();
}
