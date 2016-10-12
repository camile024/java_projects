package bonks.zaps.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class responsible for manipulating the whole world of Bonks and Zaps
 * @author Kamil
 *
 */
public class GameEngine {
	private GridWorld grid;
	private ArrayList<Being> beings = new ArrayList<Being>();
	private int turns;
	private int turnc = 1;
	private int interval;
	private boolean gui;
	
	/**
	 * If no value specified - use the ones stated in the assignment brief
	 */
	public GameEngine(){
		this(20, 20, 20, 5, 30, 50, -1, true);
	}
	
	/**
	 * Sets up a game
	 * @param sizeX Number of columns
	 * @param sizeY Number of rows
	 * @param bonks Number of Bonks in the beginning
	 * @param zaps 	Number of Zaps in the beginning
	 * @param turns Number of turns to simulate
	 * @param maleChance Chance of a male Bonk being spawn during the initial phase (in %)
	 * @param interval Interval between turns in miliseconds (1000 ms = 1s)
	 * @param gui True if the game is being held by GUI, false if the game is text-only
	 */
	public GameEngine(int sizeX, int sizeY, int bonks, int zaps, int turns, int maleChance, int interval, boolean gui){
		setWorld(sizeX, sizeY, bonks, zaps, maleChance);
		this.interval = interval;
		this.turns = turns;
		this.gui = gui;
	}
	
	/**
	 * Sets up the world
	 * @param sizeX
	 * @param sizeY
	 * @param bonks
	 * @param zaps
	 * @param maleChance
	 */
	public void setWorld(int sizeX, int sizeY, int bonks, int zaps, int maleChance){
		grid = new GridWorld(sizeX, sizeY); //create grid with rooms
		Random randomize = new Random();
		
		//generate bonks//
		for (int i=0; i < bonks; i++){
			int random = randomize.nextInt(100)+1;
			Gender gender = Gender.FEMALE;
			if (random <= maleChance)
				gender = Gender.MALE;
			random = randomize.nextInt(sizeX);
			int random2 = randomize.nextInt(sizeY);
			Room room = grid.getRoom(random, random2);
			beings.add(new Bonk("B" + i + "A", gender, room, true));
		}
		
		//generate zaps//
		for (int i=0; i < zaps; i++){
			int random = randomize.nextInt(sizeX);
			int random2 = randomize.nextInt(sizeY);
			Room room = grid.getRoom(random, random2);
			beings.add(0, new Zap("Z" + i, room)); //add zaps to the beginning of the list, so they have priority when acting
		}
		grid.print();
	}
	
	/**
	 * Performs 1 turn if handled by GUI, whole simulation if text-only
	 */
	public void play(){
		if (!gui){
			for (int i = 1; i <= turns; i++){
				System.out.println("---------[ TURN " + i + " ]----------");
				doActing();
				doMoving();
				grid.print();
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					
				}
				
			}
		} else {
			System.out.println("---------[ TURN " + turnc + " ]----------");
			turnc++;
			doActing();
			doMoving();
			grid.print();
		}
	}

	/**
	 * Forces all Beings to act (1 by 1)
	 */
	private void doActing(){
		int size = beings.size();
		for (int i=0; i < size; i++){ //can't use foreach, list may be modified during iterating
			Being b = beings.get(i);
			try{
				b.act();
			}catch (CannotActException ex){
			
			}
			if (b instanceof Bonk){
				if (((Bonk) b).isAfterBirth()){
					beings.add(((Bonk) b).getLatestChild());
				}
			}
		}
	}
	
	/**
	 * Forces all Beings to move (1 by 1)
	 */
	private void doMoving(){
		for (Being b : beings){
			if (b instanceof Entity){
				try{
					((Entity) b).move();
				}catch (CannotActException ex){
					
				}
			}
		}
	}
	
	//GETTERS/SETTERS//
	private int getTurn(){
		return turnc;
	}
	
	public Room getRoom(Position pos){
		return grid.getRoom(pos);
	}
	
	public Room getRoom(int x, int y){
		return grid.getRoom(x, y);
	}
}
