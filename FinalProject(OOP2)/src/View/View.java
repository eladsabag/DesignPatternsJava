/**
 * @author Elad Sabag - 207116112
 * @author Aviad Hedvat - 207115205
 **/
package View;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import Listeners.UIEventsListener;
import Model.Product;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class View extends Application implements AbstractStoreView {
	private Vector<UIEventsListener> allListeners;
	private Scene chooseScene, addProductScene, clientsScene, sendUpdatesScene, showProductScene,
			removeAllProductsScene, removeOneProductScene, menuScene, checkOutScene, showProductsScene,
			showSpecificProductScene, profitsScene, profitScene, oneProductProfitScene;
	private String printMap, printProduct, profits, profit, message;
	private ArrayList<String> names;
	private Stage stage;
	private int savingChoice = -1;
	private boolean undoOnce = false;

	// constructor
	public View(Stage primaryStage) throws Exception {
		allListeners = new Vector<UIEventsListener>();
		primaryStage.setTitle("Products Summary");

		// choosing scene
		Label l = new Label("Choose your saving choice:");
		Label l1 = new Label("\n*Note: if you won't select saving choice the system will use ALPHABETICAL.");
		l.setFont(Font.font("Arial Black", 20));
		l1.setFont(Font.font("Arial Black", 10));
		l.setTextFill(Color.WHITE);
		l1.setTextFill(Color.WHITE);
		Button choose = new Button("Choose");
		choose.setFont(Font.font("Arial", 15));
		choose.setTextFill(Color.BLACK);
		choose.setScaleX(1.2);
		choose.setScaleY(1.2);
		ChoiceBox<String> savingBox = new ChoiceBox<String>();
		savingBox.getItems().addAll("ALPHABETICAL", "REVERSED_ALPHABETICAL", "ORDER_INSERTION");
		savingBox.getSelectionModel().selectFirst();
		savingBox.setScaleX(2.2);
		savingBox.setScaleY(1.2);
		choose.setOnAction(e -> {
			setSavingChoice(savingBox.getSelectionModel().getSelectedIndex() + 1);
			primaryStage.setScene(menuScene);
		});
		VBox v1 = new VBox(10);
		v1.getChildren().addAll(l, l1, savingBox, choose);
		v1.setAlignment(Pos.CENTER);
		v1.setPadding(new Insets(10));
		StackPane pane = new StackPane();
		pane.getChildren().addAll(v1);
		pane.setBackground(new Background(new BackgroundImage(new Image("file:supermarket.jpg", 600, 600, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT)));
		chooseScene = new Scene(pane, 600, 600);

		// add product scene
		Label note = new Label(
				"*Note : Using catalog number you already used will change the product details and undo button will not be available!");
		note.setFont(Font.font("Aharoni", 10));
		Label product = new Label("Product:");
		Label catalogNumber = new Label("Catalog Number:");
		Label productName = new Label("Product Name:");
		Label supplierPrice = new Label("Supplier Price:");
		Label storePrice = new Label("Store Price:");
		Label clientName = new Label("Client Name:");
		Label mobileNumber = new Label("Mobile Number:");
		Label notifications = new Label("Would like to get notifications about sales?");
		TextField tx1 = new TextField();
		tx1.setPromptText("Write your text here...");
		TextField tx2 = new TextField();
		tx2.setPromptText("Write your text here...");
		TextField tx3 = new TextField();
		tx3.setPromptText("Write your text here...");
		TextField tx4 = new TextField();
		tx4.setPromptText("Write your text here...");
		TextField tx5 = new TextField();
		tx5.setPromptText("Write your text here...");
		TextField tx6 = new TextField();
		tx6.setPromptText("Write your text here...");
		RadioButton rd = new RadioButton();
		Button undo = new Button("Undo");
		undo.setDisable(true);
		undo.setOnAction(e -> {
			if (undoOnce) {
				undo();
				undo.setDisable(true);
				undoOnce = false;
				if (mapIsEmpty()) {
					primaryStage.setScene(chooseScene);
				}
			}
		});
		Button confirm = new Button("Confirm");
		confirm.setOnAction(e -> {
			try {
				boolean isExist = false;
				isExist = isKeyExist(tx1.getText());
				String priceOfSupplier = tx3.getText(), sellingPrice = tx4.getText();
				if (priceOfSupplier.isEmpty())
					priceOfSupplier = "0";
				if (sellingPrice.isEmpty())
					sellingPrice = "0";
				if (addProduct(tx1.getText(), tx2.getText(), Integer.parseInt(priceOfSupplier),
						Integer.parseInt(sellingPrice), tx5.getText(), tx6.getText(), rd.isSelected())) {
					tx1.clear();
					tx2.clear();
					tx3.clear();
					tx4.clear();
					tx5.clear();
					tx6.clear();
					rd.setSelected(false);
					undo.setDisable(false);
					undoOnce = true;
					if (isExist)
						undo.setDisable(true);
				}
			} catch (NumberFormatException e1) {
				addProduct("A", "A", -1, -1, "A", "A", false);
			}
		});
		Button toMenu = new Button("Menu");
		toMenu.setOnAction(e -> {
			tx1.clear();
			tx2.clear();
			tx3.clear();
			tx4.clear();
			tx5.clear();
			tx6.clear();
			rd.setSelected(false);
			undo.setDisable(true);
			undoOnce = false;
			primaryStage.setScene(menuScene);
		});
		VBox v2 = new VBox(10);
		v2.setPadding(new Insets(100));
		v2.setBackground(new Background(new BackgroundFill(Color.BLANCHEDALMOND, CornerRadii.EMPTY, null)));
		v2.getChildren().addAll(product, catalogNumber, tx1, productName, tx2, supplierPrice, tx3, storePrice, tx4,
				clientName, tx5, mobileNumber, tx6, notifications, rd, confirm, undo, toMenu, note);
		v2.setAlignment(Pos.BASELINE_CENTER);
		StackPane pane1 = new StackPane(v2);
		addProductScene = new Scene(pane1, 800, 800);

		// menu scene
		Button addProducts = new Button("Add Products");
		addProducts.setOnAction(e -> primaryStage.setScene(addProductScene));

		// show products scene
		Button showProducts = new Button("Show Products");
		showProducts.setOnAction(e -> {
			if (printProductsMap()) {
				VBox v3 = new VBox(10);
				v3.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, CornerRadii.EMPTY, null)));
				Label allProducts = new Label("Your Products:");
				allProducts.setFont(Font.font("castellar", 10));
				Label allProducts1 = new Label(printMap);
				allProducts1.setFont(Font.font("castellar", 15));
				ScrollPane s1 = new ScrollPane();
				s1.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, null)));
				s1.setPrefSize(600, 600);
				s1.setContent(allProducts1);
				Button toMenu1 = new Button("Menu");
				toMenu1.setOnAction(e1 -> primaryStage.setScene(menuScene));
				v3.getChildren().addAll(allProducts, s1, toMenu1);
				v3.setAlignment(Pos.CENTER);
				showProductsScene = new Scene(v3, 700, 700);
				primaryStage.setScene(showProductsScene);
				printMap = "";
			}
		});

		// show specific product scene
		TextField tx7 = new TextField();
		tx7.setPromptText("Catalog number of product");
		tx7.setScaleX(1);
		tx7.setScaleY(1);
		Button showSpecificProduct = new Button("Show Product");
		showSpecificProduct.setOnAction(e -> {
			if (!mapIsEmpty()) {
				Button toMenu2 = new Button("Menu");
				toMenu2.setOnAction(e2 -> {
					tx7.clear();
					primaryStage.setScene(menuScene);
				});
				Label l2 = new Label("Write the catalog of the product you want to find:");
				Button confirm1 = new Button("Confirm");
				confirm1.setOnAction(e1 -> {
					if (showSpecificProduct(tx7.getText())) {
						Label l3 = new Label(printProduct);
						l3.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, null)));
						VBox v4 = new VBox(10);
						v4.getChildren().addAll(l3, toMenu2);
						v4.setAlignment(Pos.CENTER);
						showProductScene = new Scene(v4, 600, 600);
						primaryStage.setScene(showProductScene);
						tx7.clear();
						printProduct = "";
					}
				});
				VBox v5 = new VBox(10);
				v5.setPadding(new Insets(100));
				v5.getChildren().addAll(l2, tx7, confirm1, toMenu2);
				v5.setAlignment(Pos.CENTER);
				showSpecificProductScene = new Scene(v5, 600, 600);
				primaryStage.setScene(showSpecificProductScene);
			}
		});

		// remove all products scene
		Button removeProducts = new Button("Remove All Products");
		removeProducts.setOnAction(e -> {
			if (!mapIsEmpty()) {
				Label l4 = new Label("Would you like to remove all products?");
				Button no = new Button("No");
				no.setOnAction(toMenu.getOnAction());
				Button yes = new Button("Yes");
				yes.setOnAction(e1 -> {
					removeAllProducts();
					primaryStage.setScene(chooseScene);
				});
				VBox v6 = new VBox(10);
				v6.getChildren().addAll(l4, yes, no);
				v6.setAlignment(Pos.CENTER);
				removeAllProductsScene = new Scene(v6, 600, 600);
				primaryStage.setScene(removeAllProductsScene);
			}
		});

		// remove one product scene
		Button removeOneProduct = new Button("Remove One Product");
		removeOneProduct.setOnAction(e -> {
			if (!mapIsEmpty()) {
				Button toMenu3 = new Button("Menu");
				toMenu3.setOnAction(e3 -> primaryStage.setScene(menuScene));
				Label l5 = new Label("Write the catalog of the product you want to remove:");
				TextField tx8 = new TextField();
				tx8.setPromptText("Write your text here...");
				Button confirm2 = new Button("Confirm");
				confirm2.setOnAction(e2 -> {
					if (removeOneProduct(tx8.getText())) {
						if (mapIsEmpty())
							primaryStage.setScene(chooseScene);
						else
							primaryStage.setScene(menuScene);
					}
				});
				VBox v7 = new VBox(10);
				v7.getChildren().addAll(l5, tx8, confirm2, toMenu3);
				v7.setAlignment(Pos.CENTER);
				v7.setPadding(new Insets(100));
				removeOneProductScene = new Scene(v7, 600, 600);
				primaryStage.setScene(removeOneProductScene);
			}
		});

		// profits scene
		Button showStoreProfits = new Button("Store Profits");
		showStoreProfits.setOnAction(e -> {
			if (!mapIsEmpty()) {
				storeProfits();
				Label storeProfits = new Label("The store total profits are : " + profits + ".");
				storeProfits.setFont(Font.font("castellar", 15));
				Button toMenu4 = new Button("Menu");
				toMenu4.setOnAction(e4 -> primaryStage.setScene(menuScene));
				VBox v8 = new VBox(10);
				v8.getChildren().addAll(storeProfits, toMenu4);
				v8.setAlignment(Pos.CENTER);
				profitsScene = new Scene(v8, 600, 600);
				primaryStage.setScene(profitsScene);
			}
		});

		// one product profit scene
		Button showOneProductProfit = new Button("Product Profit");
		showOneProductProfit.setOnAction(e -> {
			if (!mapIsEmpty()) {
				Button toMenu5 = new Button("Menu");
				toMenu5.setOnAction(e5 -> primaryStage.setScene(menuScene));
				TextField tx9 = new TextField();
				tx9.setPromptText("Write your text here...");
				Label productProfit = new Label("Choose the catalog number of the product:");
				productProfit.setFont(Font.font(15));
				Button confirm3 = new Button("Confirm");
				confirm3.setOnAction(e3 -> {
					if (productProfit(tx9.getText())) {
						Label l6 = new Label(profit);
						VBox v9 = new VBox(10);
						v9.getChildren().addAll(l6, toMenu5);
						v9.setAlignment(Pos.CENTER);
						profitScene = new Scene(v9, 600, 600);
						primaryStage.setScene(profitScene);
					}
				});
				VBox v10 = new VBox(10);
				v10.setPadding(new Insets(100));
				v10.getChildren().addAll(productProfit, tx9, confirm3, toMenu5);
				v10.setAlignment(Pos.CENTER);
				oneProductProfitScene = new Scene(v10, 600, 600);
				primaryStage.setScene(oneProductProfitScene);
			}
		});

		// send updates scene
		Button sendUpdates = new Button("Send Updates");
		sendUpdates.setOnAction(e -> {
			if (!mapIsEmpty()) {
				Button toMenu6 = new Button("Menu");
				toMenu6.setOnAction(e6 -> primaryStage.setScene(menuScene));
				Label update = new Label("Write the update you want to send:");
				TextField tx10 = new TextField();
				tx10.setPromptText("Write your text here...");
				Button confirm4 = new Button("Confirm");
				confirm4.setOnAction(e4 -> {
					sendUpdates(tx10.getText());
					tx10.clear();
					primaryStage.setScene(menuScene);
				});
				VBox v11 = new VBox(10);
				v11.setPadding(new Insets(100));
				v11.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, null)));
				v11.getChildren().addAll(update, tx10, confirm4, toMenu6);
				v11.setAlignment(Pos.CENTER);
				sendUpdatesScene = new Scene(v11, 600, 600);
				primaryStage.setScene(sendUpdatesScene);
			}
		});

		// clients scene
		Button printSaleClients = new Button("Print Sale Clients");
		printSaleClients.setOnAction(e -> {
			Label l7 = new Label("*Note: List of last clients who received last message:\n" + message);
			l7.setFont(Font.font(20));
			ScrollPane s2 = new ScrollPane();
			s2.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, null)));
			s2.setPrefSize(600, 600);
			s2.setContent(l7);
			Button toMenu7 = new Button("Menu");
			toMenu7.setDisable(true);
			toMenu7.setOnAction(e8 -> {
				l7.setText("");
				names = null;
				primaryStage.setScene(menuScene);
			});
			if (PrintSaleClients()) {
				// Thread
				Thread t = new Thread(() -> {
					try {
						if (names != null) {
							for (int i = 0; i < names.size(); i++) {
								String name = names.get(i);
								Thread.sleep(2000);
								Platform.runLater(() -> {
									l7.setText(l7.getText() + "\n" + name);
								});
							}
							toMenu7.setDisable(false);
						}
					} catch (InterruptedException ex) {

					}
				});
				t.start();
				VBox v12 = new VBox(10, s2, toMenu7);
				v12.setAlignment(Pos.CENTER);
				clientsScene = new Scene(v12, 600, 600);
				primaryStage.setScene(clientsScene);
			}
		});

		Button finish = new Button("---Finish---");
		finish.setOnAction(e -> primaryStage.setScene(checkOutScene));
		VBox v13 = new VBox(10);
		v13.setPadding(new Insets(100));
		v13.getChildren().addAll(addProducts, showProducts, showSpecificProduct, removeProducts, removeOneProduct,
				showStoreProfits, showOneProductProfit, sendUpdates, printSaleClients, finish);
		v13.setAlignment(Pos.CENTER);
		StackPane pane2 = new StackPane();
		pane2.getChildren().addAll(v13);
		pane2.setBackground(new Background(new BackgroundImage(new Image("file:supermarket.jpg", 600, 600, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT)));
		menuScene = new Scene(pane2, 600, 600);

		// Checkout Scene
		Button goodBye = new Button("GoodBye!");
		goodBye.setOnAction(e -> primaryStage.close());
		VBox v14 = new VBox(10);
		v14.getChildren().addAll(goodBye);
		v14.setAlignment(Pos.CENTER);
		StackPane pane3 = new StackPane();
		pane3.getChildren().addAll(v14);
		pane3.setBackground(new Background(new BackgroundImage(
				new Image("file:Seven_eleven.png", 600, 600, false, true), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		checkOutScene = new Scene(pane3, 600, 600);

		stage = primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	public void show(int savingChoice) {
		savingChoice(savingChoice);
		if (this.savingChoice == -1)
			stage.setScene(chooseScene);
		else
			stage.setScene(menuScene);
		stage.show();
	}

	@Override
	public void savingChoice(int savingChoice) {
		this.savingChoice = savingChoice;
	}

	@Override
	public void registerListener(UIEventsListener listener) {
		allListeners.add(listener);
	}

	// UIListeners
	@Override
	public boolean productProfit(String catalogNumber) {
		for (UIEventsListener l : allListeners)
			return l.productProfit(catalogNumber);
		return false;
	}

	@Override
	public boolean PrintSaleClients() {
		for (UIEventsListener l : allListeners)
			return l.PrintSaleClients();
		return false;
	}

	@Override
	public void sendUpdates(String msg) {
		for (UIEventsListener l : allListeners)
			l.sendUpdates(msg);
	}

	@Override
	public void storeProfits() {
		for (UIEventsListener l : allListeners)
			l.storeProfits();
	}

	@Override
	public boolean removeOneProduct(String catalogNumber) {
		for (UIEventsListener l : allListeners)
			return l.removeOneProduct(catalogNumber);
		return false;
	}

	@Override
	public void removeAllProducts() {
		for (UIEventsListener l : allListeners)
			l.removeAllProducts();
	}

	@Override
	public void setSavingChoice(int savingChoice) {
		for (UIEventsListener l : allListeners)
			l.setSavingChoice(savingChoice);
	}

	@Override
	public boolean addProduct(String catalogNumber, String productName, int supplierPrice, int storePrice, String name,
			String mobileNumber, boolean notifications) {
		for (UIEventsListener l : allListeners)
			return l.addProduct(catalogNumber, productName, supplierPrice, storePrice, name, mobileNumber,
					notifications);
		return false;
	}

	@Override
	public boolean showSpecificProduct(String catalogNumber) {
		for (UIEventsListener l : allListeners)
			return l.showSpecificProduct(catalogNumber);
		return false;
	}

	@Override
	public boolean mapIsEmpty() {
		for (UIEventsListener l : allListeners)
			return l.isMapEmpty();
		return false;
	}

	@Override
	public void undo() {
		for (UIEventsListener l : allListeners)
			l.undo();
	}

	@Override
	public boolean printProductsMap() {
		for (UIEventsListener l : allListeners)
			return l.printProductsMap();
		return false;
	}

	@Override
	public boolean isKeyExist(String key) {
		for (UIEventsListener l : allListeners)
			return l.isKeyExist(key);
		return false;
	}

	// commands
	@Override
	public void setMessage(String msg) {
		message = msg;
	}

	@Override
	public void printSalesList(ArrayList<String> names) {
		this.names = names;
	}

	@Override
	public void printMap(String printMap) {
		this.printMap = printMap;
	}

	@Override
	public void printProduct(String printProduct) {
		this.printProduct = printProduct;
	}

	@Override
	public void showProfitOfProduct(Product product, int profit) {
		this.profit = product.toString() + "\nProfit : " + profit;
	}

	@Override
	public void showStoreProfits(int totalProfits) {
		this.profits = "" + totalProfits;
	}

	// EventListeners
	@Override
	public void actionFailed(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void actionCompleted(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void illegalPrice(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void emptyDetails(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void productNotFound(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void mapIsEmpty(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void invalidNumber(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

}
