/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package Program;

import Controller.Controller;
import Model.Model;
import View.AbstractStoreView;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	// main method
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Model theModel = new Model();
		AbstractStoreView theView = new View(primaryStage);
		Controller theController = new Controller(theModel, theView);
	}

}
