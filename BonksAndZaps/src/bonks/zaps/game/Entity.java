package bonks.zaps.game;

import java.util.ArrayList;
import java.util.Random;
/**
 * Common methods/variables for all Beings
 * @author Kamil
 *
 */
public abstract class Entity implements Being{
	Position position;
	Room currentRoom;
	String name;
	
	/**
	 * (Being interface)
	 */
	public void act() throws CannotActException{
	}
	
	/**
	 * Generalised move function
	 * @throws CannotActException
	 */
	void move() throws CannotActException{
		Random randomize = new Random();
		if (randomize.nextInt(20) > 1){ //entities rarely stay in the same spot
			ArrayList<Room> rooms = currentRoom.getConnectedRooms();
			if (rooms.size() > 0){
				int roomNum = randomize.nextInt(rooms.size());
				currentRoom.removeBeing(this);
				currentRoom = rooms.get(roomNum);
				position.setX(currentRoom.getPosition().getX());
				position.setY(currentRoom.getPosition().getY());
				currentRoom.addBeing(this);
			}
		}
	}
	
	//INTERFACE/GETTERS/SETTERS//
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public Position getLocation(){
		return position;
	}
	
	public void setLocation(Position pos){
		position = pos;
	}
	
	public Room getCurrentRoom(){
		return currentRoom;
	}
	
}
