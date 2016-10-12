package bonks.zaps.game;

import java.util.ArrayList;
import java.util.Random;
/**
 * Bonk class
 * @author Kamil
 *
 */
public class Bonk extends Mortal {
	
	
	private ArrayList<Bonk> children = new ArrayList<Bonk>();
	private boolean afterBirth = false;
	//from Mortal/entity: gender, reproduce, name, alive, currentRoom, position
	
	/**
	 * Assigns parameters to a newly created Bonk
	 * @param name
	 * @param gender
	 * @param room
	 * @param adult decides if the Bonk can reproduce in this turn
	 */
	public Bonk(String name, Gender gender, Room room, Boolean adult){
		this.name = name;
		this.gender = gender;
		this.reproduce = adult;
		currentRoom = room;
		position = new Position(room.getPosition().getX(), room.getPosition().getY());
		room.addBeing(this);
	}
	
	/**
	 * Another constructor, skips checking for being an adult, assumes a new Bonk is born thus being a child
	 * and not being able to reproduce yet
	 * @param name
	 * @param gender
	 * @param room
	 */
	public Bonk(String name, Gender gender, Room room){
		this(name, gender, room, false);
	}
	
	/**
	 * Act method implemented from Being interface, basically just makes Bonks reproduce
	 */
	public void act() throws CannotActException{
		if (alive){
			if (reproduce){
				reproduce();
			}
		}else{
			throw new CannotActException("Can't act - being is dead");
		}

	}
	
	/**
	 * Move method inherited from Entity class. Moves Bonk to another
	 * Room and resets the variables
	 */
	@Override
	public void move() throws CannotActException{
		if (alive){
			super.move();
			reproduce = true;
			afterBirth = false;
		} else {
			throw new CannotActException("Can't move - being is dead.");
		}
	}
	
	/**
	 * Used for reproducing Bonks of different Gender
	 */
	private void reproduce(){
		int size = currentRoom.getBeings().size();
		for (int i = 0; i < size; i++){ //can't use foreach, list will be modified during iterating
			Being b = currentRoom.getBeing(i);
			if (b != this && b instanceof Bonk){
				Bonk x = (Bonk)b;
				if (x.getGender() != gender && x.canReproduce()){ 
					reproduce = false;
					x.setFertility(false);
					String name1 = ("B" + name.substring(1, name.length()-1));
					name1 = name1.concat("-" + x.getName().substring(1, x.getName().length()-1) + "A");  //playing with parents' names to generate a new one
					Random randomize = new Random();
					Gender babyGender;
					if (randomize.nextInt(2) == 1){ //50/50 chance (independent of the chance during initial state)
						babyGender = Gender.FEMALE;
					} else {
						babyGender = Gender.MALE;
					}
					Bonk baby = new Bonk(name1, babyGender, currentRoom);
					this.addChild(baby);
					x.addChild(baby);
					afterBirth = true; //adding only to 1 parent so the engine doesn't add the new bonk to the array twice
				}
			}
		}
	}
	
	//GETTERS/SETTERS//
	public int getNumOfChildren(){
		return children.size();
	}
	
	public void addChild(Bonk child){
		children.add(child);
	}

	public boolean isAfterBirth(){
		return afterBirth;
	}
	
	/**
	 * Returns the latest born child
	 * @return
	 */
	public Bonk getLatestChild(){
		return children.get(children.size()-1);
	}
	
	public Gender getGender(){
		return gender;
	}
}
