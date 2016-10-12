package bonks.zaps.game;

import bonks.zaps.gui.Gui;
import bonks.zaps.gui.Menu;
import javafx.stage.Stage;
/**
 * Main Application class
 * @author Kamil
 *
 */
public class Application extends javafx.application.Application{
	
	private int gridX = 20;
	private int gridY = 20;
	private int turns = 30;
	private int bonks = 20;
	private int zaps = 5;
	private int interval = 1000;
	private int chance = 50;
	private final boolean gui = true; //DECIDES IF APP SHOULD RUN IN GRAPHICAL ENVIRONMENT OR JUST AS PLAIN TEXT
	
	public static void main(String[] args) {
		Application app = new Application();
		app.run(args);
	}
	
	/**
	 * Default constructor
	 */
	public Application(){
	
	}
	
	/**
	 * Initializes the application, chooses gui/text (modifable constant up above)
	 * @param args
	 */
	public void run(String[] args){
		System.out.println("\t\t\tBONKS AND ZAPS - THE GAME\n\tTHE CS12320 ASSIGNMENT\t KAC12@ABER.AC.UK - KAMIL CUPIAL");
		if (!gui){
			GameEngine engine = new GameEngine(gridX, gridY, bonks, zaps, turns, chance, interval, false);
			engine.play();
		}else{
			launch(args);
		}
		System.out.println("Application terminating.");
	}
	
	/**
	 * Starts the GUI, proceeds to the Menu class
	 */
	@Override
	public void start(Stage stage){
		Menu gui = new Menu(this);
		gui.showMenu(stage);	
	}
	
	//GETTERS AND SETTERS//
	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

	public int getBonks() {
		return bonks;
	}

	public void setBonks(int bonks) {
		this.bonks = bonks;
	}

	public int getZaps() {
		return zaps;
	}

	public void setZaps(int zaps) {
		this.zaps = zaps;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}
	

}
