package bonks.zaps.game;

/**
 * Common methods and variables for living entities
 * @author Kamil
 *
 */
public abstract class Mortal extends Entity{
	Gender gender;
	boolean reproduce;
	boolean alive = true;
	
	/**
	 * Gender
	 * @return
	 */
	public Gender getGender(){
		return gender;
	}
	
	/**
	 * Decides if an Entity can reproduce
	 * @return
	 */
	public boolean canReproduce(){
		return reproduce;
	}
	
	/**
	 * Check if the entity is alive
	 * @return
	 */
	public boolean isAlive(){
		return alive;
	}
	
	/**
	 * Kills the entity
	 */
	public void kill(){
		alive = false;
		reproduce = false;
		name = name.substring(0, name.length()-1) + 'D';
	}
	
	/**
	 * Brings the entity back to life
	 */
	public void resurrect(){
		alive = true;
	}
	
	/**
	 * Sets the ability to reproduce
	 * @param newRep
	 */
	public void setFertility(boolean newRep){
		reproduce = newRep;
	}
	
	/**
	 * Sets gender of the Entity
	 * @param gender
	 */
	public void setGender(Gender gender){
		this.gender = gender;
	}
	

}
