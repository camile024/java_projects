package bonks.zaps.gui;

import java.util.ArrayList;

import bonks.zaps.game.Application;
import bonks.zaps.game.Being;
import bonks.zaps.game.Bonk;
import bonks.zaps.game.GameEngine;
import bonks.zaps.game.Room;
import bonks.zaps.game.Zap;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Simulation class responsible for displaying current state of the game and running the game using GUI
 * @author Kamil
 *
 */
public class Simulation extends Gui {
	private int gridX = 20;
	private int gridY = 20;
	private int turns = 30;
	private int bonks = 20;
	private int zaps = 5;
	private int interval = 1000;
	private int chance = 50;
	private int turnsPassed;
	private GameEngine engine;
	GridPane grid = new GridPane();
	
	/**
	 * Simulation object constructor - getting values set earlier
	 * @param app Application object to get values from
	 */
	public Simulation(Application app) {
		super(app);
		gridX = app.getGridX();
		gridY = app.getGridY();
		turns = app.getTurns();
		bonks = app.getBonks();
		zaps = app.getZaps();
		interval = app.getInterval();
		chance = app.getChance();
		engine = new GameEngine(gridX, gridY, bonks, zaps, turns, chance, interval, true);
		turnsPassed = 0;
		grid.setHgap(3);
		grid.setVgap(3);
		grid.setPadding(new Insets(0, 0, 0, 0));
	}
	
	/**
	 * Performs the turn after clicking the button
	 * @param btn button clicked
	 * @param caller simulation Stage object, where the button was clicked
	 */
	private void clickTurn(Button btn, Stage caller){
		if(turnsPassed < turns){ //do the code if we still have turns to go
			btn.setText("Next Turn [" + ++turnsPassed + "/" + turns + "]");
			engine.play();
			updateGrid();
			
		} else {
			showError("Maximum number of turns reached.", caller);
		}
	}
	
	/**
	 * Refreshes all values visible on the simulation screen according to GridWorld status
	 */
	private void updateGrid(){
		for (int x = 0; x < gridX; x++){
			for (int y = 0; y < gridY; y++){
				ArrayList<Being> beings = engine.getRoom(x, y).getBeings(); //get the beings and get their numbers
				int b = 0;
				int d = 0;
				int z = 0;
				for (Being bx : beings){
					if (bx instanceof Zap){
						z++;
					}else if (bx instanceof Bonk){
						if (((Bonk) bx).isAlive()){
							b++;
						}else{
							d++;
						}
					}
				}
				ObservableList<Node> items = getHBoxFromGrid(x,y).getChildren();
				for (Node n : items){
					if (n instanceof Label){
						Label lab = ((Label) n);
						String id = lab.getId();
						switch(id){ //set text inside labels to corresponding numbers of beings
						case "emptybonk": //'empty-' id for different styling
						case "bonk":
							lab.setText(String.valueOf(b));
							if (b == 0)
								lab.setId("emptybonk");
							else
								lab.setId("bonk");
							break;
						case "emptydead":
						case "dead":
							lab.setText(String.valueOf(d));
							if (d == 0)
								lab.setId("emptydead");
							else
								lab.setId("dead");
							break;
						case "emptyzap":
						case "zap":
							lab.setText(String.valueOf(z));
							if (z == 0)
								lab.setId("emptyzap");
							else
								lab.setId("zap");
							break;
						default:
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Gets the HBox from specified position x and y inside the grid
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return HBox on x, y
	 */
	@SuppressWarnings("static-access")
	private HBox getHBoxFromGrid(int x, int y){
		for (Node n : grid.getChildren()){
			if (n instanceof HBox && grid.getRowIndex(n) == x && grid.getColumnIndex(n) == y){
				return (HBox)n;
			}
		}
		return null;
	}
	
	/**
	 * Shows a detailed list of beings after clicking on a cell
	 * @param x x coordinate of the Room
	 * @param y y coordinate of the room
	 * @param caller stage where the list is called from
	 */
	private void showList(int x, int y, Stage caller){
		Room r = engine.getRoom(x, y);
		ArrayList<Being> beings = r.getBeings();
		ArrayList<Node> labels = new ArrayList<Node>();
		for (Being b : beings){	//sets the IDs for styling purposes
			Label lab = new Label(b.getName());
			if (b instanceof Bonk)
				if (((Bonk) b).isAlive())
					lab.setId("bonk");
				else
					lab.setId("dead");
			else if (b instanceof Zap)
				lab.setId("zap");
			labels.add(lab);
		}
		FlowPane flow = addFlowPane(Double.valueOf(6), Double.valueOf(3), new Insets(5,0,5,0), labels); //displays everything neatly on a flowpane
		Stage stage = new Stage();
		Scene scene = new Scene(flow, 300, 300);
		
		scene.getStylesheets().add("styles/menu.css");
		stage.setTitle("Room [" + x + ", " + y + "]");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(caller);
		stage.setScene(scene);
		stage.show();	
	}
	/**
	 * Starts the simulation - prepares the display and gets the thing going according to settings
	 */
	@SuppressWarnings("static-access")
	public void begin(){
		 ArrayList<Node> nodes = new ArrayList<Node>();
		 Stage stage = new Stage();
		for (int x = 0; x < gridX; x++){
			for (int y = 0; y < gridY; y++){
				//LABELS WITH DATA STORED IN HBOXES //
				Label label = new Label("0");
				label.setId("emptybonk");
				nodes.add(label); //bonk
				
				label = new Label(":");
				label.setId("colon");
				nodes.add(label);
				
				label = new Label("0");
				label.setId("emptydead");
				nodes.add(label); //dead
				
				label = new Label(":");
				label.setId("colon");
				nodes.add(label);
				
				label = new Label("0");
				label.setId("emptyzap");
				nodes.add(label); //zaps
				
				HBox hBox = addHBox(0, new Insets(5), nodes);
				grid.add(hBox, x, y);
				hBox.setOnMouseClicked(e -> showList(grid.getRowIndex(hBox), grid.getColumnIndex(hBox), stage)); //make hBox show a list of all entities when clicked
				nodes.clear();
			}
		}
		
		//BOTTOM BOX (EXIT + NEXTTURN BUTTONS)//
		Button btn1 = new Button("Next Turn [" + turnsPassed + "/" + turns + "]");
		btn1.setId("turnbtn");
		Button btn2 = new Button("Exit");
		btn1.setOnAction(e -> clickTurn(btn1, stage));
		nodes.add(btn1);
		nodes.add(btn2);
		grid.add(addHBox(5, new Insets(5), nodes), 0, gridY, gridX, 1);
			
		//prepare the stage//
		Scene scene = new Scene(grid, gridX * 45, gridY * 35 + 25);
		scene.getStylesheets().add("styles/menu.css");
		stage.setTitle("Simulation");
		stage.setScene(scene);
		stage.show();
		btn2.setOnAction(e -> System.exit(0));
		updateGrid();
		Task<Void> task = new Task<Void>() { //runs the turn in a separate task to keep refreshing the GUI screen
            @Override 
            public Void call() throws Exception {
            	for (int i=0; i < turns; i++){
            		engine.play();
            		Platform.runLater(new Runnable() {	//to get rid of errors claiming that modifying UI has to be done in the UI thread
                    	@Override public void run() {
                        	updateGrid();
                    	}
                	});
					Thread.sleep(interval);
            	}
				return null;
            }
        };
		if (interval != -1){
			btn1.setVisible(false);
			new Thread(task).start();
			updateGrid();
		}
	}
		
	
}
