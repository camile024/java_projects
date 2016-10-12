package bonks.zaps.gui;

import java.util.ArrayList;

import bonks.zaps.game.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Used to display and navigate around the menu
 * @author Kamil
 *
 */
public class Menu extends Gui{

	/**
	 * Gets/sets the game values refering to Application object
	 * @param app
	 */
	public Menu(Application app) {
		super(app);
	}
	
	/**
	 * Shows the main menu
	 * @param stage
	 */
	public void showMenu(Stage stage){
		//labels//
		Label message = new Label("Bonks & Zaps");
		Label footer1 = new Label("kac12@aber.ac.uk");
		Label footer2 = new Label("Kamil Cupial");
		footer2.setAlignment(Pos.BOTTOM_RIGHT);
		message.setId("title");
		
		//buttons//
		Button startSimBtn = new Button("Start the simulation");	
		Button settingsBtn = new Button("Settings");	
		Button exitBtn = new Button("Exit");
		startSimBtn.setMinWidth(120);
		settingsBtn.setMinWidth(120);
		exitBtn.setMinWidth(120);
		startSimBtn.setOnAction(e -> menuSimulation(stage));
		settingsBtn.setOnAction(e -> menuShowSettings(stage));	
		exitBtn.setOnAction(e -> System.exit(0));	
		
		//prepare panes//
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(message);
		nodes.add(startSimBtn);
		nodes.add(settingsBtn);
		nodes.add(exitBtn);
		VBox vBox = addVBox(5, new Insets(10), nodes);
		vBox.setAlignment(Pos.TOP_CENTER);
		
		nodes.clear();
		nodes.add(footer1);
		nodes.add(footer2);
		HBox hBox = addHBox(90, new Insets(10), nodes);
		
		BorderPane root = new BorderPane();
		root.setCenter(vBox);
		root.setBottom(hBox);
		
		//prepare the stage//
		Scene scene = new Scene(root, 300, 200);
		scene.getStylesheets().add("styles/menu.css");
		stage.setTitle("Bonks and Zaps - CS12320 Main Assignment");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Proceeds to the simulation itself
	 * @param s used to close the main menu window
	 */
	private void menuSimulation(Stage s){
		Simulation sim = new Simulation(app);
		s.close();
		sim.begin();
	}
	
	/**
	 * Shows the settings window, allows changing of settings
	 * @param menu caller window
	 */
	private void menuShowSettings(Stage menu){
		//labels//
		Label set1 = new Label("The simulation should happen: ");
		Label set2 = new Label("Grid size: ");
		Label set3 = new Label("Number of turns: ");
		Label set4 = new Label("Interval in miliseconds: ");
		Label set5 = new Label("Chance of male Bonks being created: ");
		Label set6 = new Label("Number of Bonks: ");
		Label set7 = new Label("Number of Zaps: ");
		Label set8 = new Label("General");
		Label set9 = new Label("Other");
		set8.setId("subtitle"); set9.setId("subtitle");
		
		//text fields//
		TextField txt1 = new TextField(String.valueOf(app.getGridX()));
		TextField txt2 = new TextField(String.valueOf(app.getGridY()));
		TextField txt3 = new TextField(String.valueOf(app.getTurns()));
		TextField txt4 = new TextField(String.valueOf(app.getInterval()));
		TextField txt5 = new TextField(String.valueOf(app.getChance()));
		TextField txt6 = new TextField(String.valueOf(app.getBonks()));
		TextField txt7 = new TextField(String.valueOf(app.getZaps()));
		txt1.setMaxWidth(40); txt2.setMaxWidth(40); txt3.setMaxWidth(40); txt4.setMaxWidth(40);
		txt5.setMaxWidth(40); txt6.setMaxWidth(40); txt7.setMaxWidth(40);
		
		//radios//
		RadioButton rbtn1 = new RadioButton("Manually");
		RadioButton rbtn2 = new RadioButton("Automatically");
		if (app.getInterval() == -1){
			rbtn1.setSelected(true);
		}else{
			rbtn2.setSelected(true);
		}
		rbtn1.setOnAction(e -> { //manual
			rbtn2.setSelected(false);
			rbtn1.setSelected(true);
			txt4.setText("-1");
			txt4.setEditable(false);
		});
		rbtn2.setOnAction(e -> { //automatic
			rbtn1.setSelected(false);
			rbtn2.setSelected(true);
			txt4.setEditable(true);
			txt4.setText("1000");
			txt4.setText(String.valueOf(app.getInterval()));
		});
		
		//buttons//
		Button btn1 = new Button("Reset to meet Assignment Brief requirements");
		Button btn2 = new Button("Back to menu");
		Button btn3 = new Button("Accept changes");
		
	
		//nodes & panes//
		ArrayList<Node> simProg = new ArrayList<Node>();
		simProg.add(rbtn1);
		simProg.add(rbtn2);
		HBox hbox = addHBox(5, new Insets(5), simProg);
		
		simProg.clear();
		simProg.add(set8);
		simProg.add(set1);
		simProg.add(hbox);
		VBox main1 = addVBox(5, new Insets(5), simProg); //automation
		
		simProg.clear();
		simProg.add(set2);
		simProg.add(txt1);
		simProg.add(txt2);
		HBox main2 = addHBox(5, new Insets(5), simProg); //grid
		
		simProg.clear();
		simProg.add(set3);
		simProg.add(txt3);
		HBox main3 = addHBox(5, new Insets(5), simProg); //turns
		
		simProg.clear();
		simProg.add(set4);
		simProg.add(txt4);
		HBox main4 = addHBox(5, new Insets(5), simProg); //interval
		
		simProg.clear();
		simProg.add(set5);
		simProg.add(txt5);
		HBox main5 = addHBox(5, new Insets(5), simProg); //chance
		
		simProg.clear();
		simProg.add(set6);
		simProg.add(txt6);
		HBox main6 = addHBox(5, new Insets(5), simProg); //bonks
		
		simProg.clear();
		simProg.add(set7);
		simProg.add(txt7);
		HBox main7 = addHBox(5, new Insets(5), simProg); //zaps
		
		simProg.clear();
		simProg.add(set9);
		VBox otherLabel = addVBox(5, new Insets(5), simProg);
		
		ArrayList<Node> finalSet = new ArrayList<Node>();
		finalSet.add(main1);
		finalSet.add(main2);
		finalSet.add(main3);
		finalSet.add(main5);
		finalSet.add(main6);
		finalSet.add(main7);
		VBox general = addVBox(5, new Insets(5), finalSet); //GENERAL
		
		finalSet.clear();
		finalSet.add(otherLabel);
		finalSet.add(main4);
		finalSet.add(main5);
		VBox other = addVBox(5, new Insets(5), finalSet); //OTHER
		
		finalSet.clear();
		finalSet.add(btn3);
		finalSet.add(btn2);
		finalSet.add(btn1);
		HBox buttons = addHBox(5, new Insets(5), finalSet); //BUTTONS
		
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		
		Separator sep2 = new Separator();
		sep2.setOrientation(Orientation.HORIZONTAL);
		
		finalSet.clear();
		finalSet.add(general);
		finalSet.add(sep);
		finalSet.add(other);
		HBox settings = addHBox(5, new Insets(5, 5, 5, 5), finalSet); //GENERAL + OTHER
		
		finalSet.clear();
		finalSet.add(settings);
		finalSet.add(sep2);
		finalSet.add(buttons);
		VBox veryFinalIPromise = addVBox(5, new Insets(5), finalSet); //GENERAL + OTHER + BUTTONS (FINAL)
		
		//prepare the stage//
		Stage stage = new Stage();
		Scene scene = new Scene(veryFinalIPromise, 580, 320);
		scene.getStylesheets().add("styles/menu.css");
		stage.setTitle("Settings");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(menu);
		stage.setScene(scene);
		stage.show();
		
		//button events//
		btn2.setOnAction(e -> {
			stage.close();
		});
		btn1.setOnAction(e -> { //reset
			app.setGridX(20);
			app.setGridY(20);
			app.setTurns(30);
			app.setBonks(20);
			app.setZaps(5);
			app.setInterval(1000);
			app.setChance(50);
			stage.close();
		});
		btn3.setOnAction(e -> { //accept
			ArrayList<TextField> toCheck = new ArrayList<TextField>();
			toCheck.add(txt1);
			toCheck.add(txt2);
			toCheck.add(txt3);
			toCheck.add(txt4);
			toCheck.add(txt5);
			toCheck.add(txt6);
			toCheck.add(txt7);
			if (checkNumbers(toCheck)){
				app.setGridX(Integer.parseInt(txt1.getText()));
				app.setGridY(Integer.parseInt(txt2.getText()));
				app.setTurns(Integer.parseInt(txt3.getText()));
				app.setBonks(Integer.parseInt(txt6.getText()));
				app.setZaps(Integer.parseInt(txt7.getText()));
				app.setInterval(Integer.parseInt(txt4.getText()));
				app.setChance(Integer.parseInt(txt5.getText()));
				stage.close();
			} else {
				showError("One of the fields has improper value.", stage);
			}
		});
	}
	
	/**
	 * Checks if given TextFields are all numbers and greater or equal to -1
	 * @param arr ArrayList of TextFields to check
	 * @return
	 */
	private boolean checkNumbers(ArrayList<TextField> arr){
		for (TextField t : arr){
			String text = t.getText();
			if (!text.matches("\\d+") && !text.equals("-1")){
				return false;
			}
		}
		return true;
	}
}
