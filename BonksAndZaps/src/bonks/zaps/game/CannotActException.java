package bonks.zaps.game;

/**
 * Exception thrown if a Being can act for some reason
 * @author Kamil
 *
 */
public class CannotActException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CannotActException(){
	}
	
	public CannotActException(String message){
		super(message);
	}
	
}
