package bonks.zaps.game;

import java.util.ArrayList;

/**
 * Stores all the Rooms and allows access to them
 * @author Kamil
 *
 */
public class GridWorld {
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private int sizeX;
	private int sizeY;
	
	/**
	 * Creates a 1D ArrayList, used to hold a 2D set of Rooms
	 * @param sizeX
	 * @param sizeY
	 */
	public GridWorld(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		int x = 0;
		int y = 0;
		for (int i = 0; i < sizeX * sizeY; i++){
			if (x == sizeX){
				x = 0;
				y++;
			}
			rooms.add(new Room(new Position(x++,y)));	
		}
		connectRooms();
	}
	
	/**
	 * Allows accessing room by giving its coordinates in a 2D array
	 * (no need to convert to 1D-friendly format)
	 * @param x
	 * @param y
	 * @return
	 */
	public Room getRoom(int x, int y){
		if (x >= 0 && y >= 0 && x < sizeX && y < sizeY)
			return rooms.get(sizeX * y + x);
		else
			return null;
	}
	
	/**
	 * Uses Position object to identify Room's location
	 * @param pos
	 * @return
	 */
	public Room getRoom(Position pos){
		return getRoom(pos.getX(), pos.getY());
	}
	
	/**
	 * Connects all rooms to their neighbours
	 */
	private void connectRooms(){
		for (Room r : rooms){
			int posX = r.getPosition().getX();
			int posY = r.getPosition().getY();
			for (int x = posX-1; x <= posX+1; x++){
				for (int y = posY-1; y <= posY+1; y++){
					Room neighbour = getRoom(x,y);
					if(r != neighbour && neighbour != null){
						r.addConnectedRoom(neighbour);
					}
				}
			}
		}
	}
	
	/**
	 * Prints current state of the whole grid to console
	 */
	public void print(){
		for (int x = 0; x < sizeX; x++){
			for (int y = 0; y < sizeY; y++){
				System.out.print(" (" + x + ", " + y + ") [ ");
				Room r = getRoom(x,y);
				for (Being b : r.getBeings()){
					System.out.print(b.getName() + " ");
				}
				System.out.println(" ]");
			}
		}
		System.out.println();
	}
}
