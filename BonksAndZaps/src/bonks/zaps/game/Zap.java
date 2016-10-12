package bonks.zaps.game;

/**
 * Zap class
 * @author Kamil
 *
 */
public class Zap extends Entity{
	//from Entity: position, currentRoom, name
	/**
	 * Standard constructor
	 * @param name
	 * @param room
	 */
	public Zap(String name, Room room){
		this.name = name;
		currentRoom = room;
		position = room.getPosition();
		room.addBeing(this);
	}
	
	/**
	 * Kills all Bonks in the room
	 */
	public void act(){
		for (Being b : currentRoom.getBeings()){
			if (b instanceof Bonk && ((Bonk) b).isAlive()){
				((Bonk) b).kill();
			}
		}
	}
	

	

	


}
