package bonks.zaps.game;

import java.util.ArrayList;

/**
 * Stores Beings (Zaps/Bonks)
 * @author Kamil
 *
 */
public class Room {
	private Position position;
	private ArrayList<Being> beings = new ArrayList<Being>();
	private ArrayList<Room> connectedRooms = new ArrayList<Room>();
	
	/**
	 * Allows the Room to tell its place inside the grid
	 * @param position
	 */
	public Room(Position position){
		this.position = position;
	}
	
	//SETTERS/GETTERS/MODIFIERS
	public void addBeing(Being being){
			beings.add(being);
	}
	
	public void removeBeing(Being being){
		beings.remove(being);
	}
	
	public ArrayList<Being> getBeings(){
		return beings;
	}
	
	public Position getPosition(){
		return position;
	}
	
	/**
	 * Adds given Room to the list of neighbouring Rooms
	 * @param room
	 */
	public void addConnectedRoom(Room room){
		connectedRooms.add(room);
	}
	
	public Being getBeing(int i){
		return beings.get(i);
	}
	
	public ArrayList<Room> getConnectedRooms(){
		return connectedRooms;
	}
}
