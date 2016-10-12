package bonks.zaps.game;

/**
 * Used to store X and Y coordinates together
 * @author Kamil
 *
 */
public class Position {
	private int x;
	private int y;
	
	/**
	 * Default constructor
	 * @param x
	 * @param y
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	//GETTERS/SETTERS//
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Equals method
	 * @param pos
	 * @return
	 */
	public boolean equals(Position pos){
		return (pos.getX() == x && pos.getY() == y);
	}
}
