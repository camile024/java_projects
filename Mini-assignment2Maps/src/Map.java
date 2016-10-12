import java.util.ArrayList;
/**
 * Map class that contains multiple Settlement objects and Road objects connecting them
 * @author kac12
 *
 */
public class Map {

	private ArrayList<Road> roads = new ArrayList<Road>();
	private ArrayList<Settlement> settlements = new ArrayList<Settlement>();
	
	public Map(){
		
	}
	
	/**
	 * Finds a road and returns its index in ArrayList 
	 * Returns -1 if the road has not been found
	 * @param road road name
	 * @param src source settlement's name
	 * @param dest destination settlement's name
	 * @return
	 */
	private int findRoad(String road, String src, String dest){
		for (int i = 0; i < roads.size(); i++){
			Road r = roads.get(i);
			if (r.getName().equals(road) && isValidConnection(src, dest, r)  ){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Checks if the road connects two settlements (doesn't matter which 
	 * one is the source and which one destination).
	 * Used to eliminate a case where source and destination get mixed up
	 * @param src name of one of the settlements
	 * @param dest name of the other settlement
	 * @param r road object that is supposed to connect the settlements
	 * @return true/false
	 */
	private boolean isValidConnection(String src, String dest, Road r){
		String rDest = r.getDestinationSettlement().getName();
		String rSrc = r.getSourceSettlement().getName();
		if ( (src.equals(rSrc) && dest.equals(rDest))
				|| (src.equals(rDest) && dest.equals(rSrc))){
			return true;
		} else
			return false;
	}
	
	/**
	 * Prints out all the map objects neatly to standard output
	 */
	public void display(){
		System.out.println("----Map----\n---SETTLEMENTS:");
		System.out.println(settlements.toString());
		System.out.println("\n\n\n---ROADS:");
		System.out.println(roads.toString());
	}
	
	/**
	 * Finds a settlement and returns its index in ArrayList 
	 * returns -1 if settlement hasn't been found
	 * @param set settlement's name
	 * @return
	 */
	private int findSettlement(String set){
		for (int i = 0; i < settlements.size(); i++){
			if (settlements.get(i).getName().equals(set)){
				return i;
			}
		}
		return -1;	
	}
	
	/**
	 * Finds a road connecting two settlement's and returns its index
	 * returns -1 if there's no connection
	 * @param source
	 * @param dest
	 * @return
	 */
	private int findConnection(String source, String dest){
		String name1, name2;
		for (int i = 0; i < roads.size(); i++){
			name1 = roads.get(i).getDestinationSettlement().getName();
			name2 = roads.get(i).getSourceSettlement().getName();
			if ( (name1.equals(source) && name2.equals(dest)) || (name2.equals(source) && name1.equals(dest))){
				return i;
			}
		}
		return -1;
	}
	
	public void addSettlement(String name, SettlementType type, long pop){
		if(findSettlement(name) != -1){
			System.out.println("A settlement with this name already exists.");
		} else {
			settlements.add(new Settlement(name, type, pop));
		}
	}
	
	public void addRoad(String name, String source, String dest, Classification type, float length){
		if(findRoad(name, source, dest) != -1){
			System.out.println("This road already exists.");
		} else if (findConnection(source, dest) != -1){
			System.out.println("A road between " + source + " and " + dest + " already exists and has a name "
					+ roads.get(findConnection(source, dest)).getName());
		} else {
			if(findSettlement(source) != -1 && findSettlement(dest) != -1){
				Settlement src = settlements.get(findSettlement(source));
				Settlement dst = settlements.get(findSettlement(dest));
				roads.add(new Road(name, src, dst, type, length));
			} else if (findSettlement(source) == -1){
				System.out.println("There's no settlement with a name " + source + ".");
			} else {
				System.out.println("There's no settlement with a name " + dest + ".");
			}
		}
	}
	
	public void removeSettlement(String name){
		int x = findSettlement(name);
		if( x != -1){
			Settlement set = settlements.get(x);
			while (set.getRoads().size() != 0){
				Road r = set.getRoads().get(0);
				String roadName = r.getName();
				String destName = r.getDestinationSettlement().getName();
				String srcName = r.getSourceSettlement().getName();
				if (srcName.equals(name))
					removeRoad(roadName, name, destName);
				else if (destName.equals(name))
					removeRoad(roadName, srcName, name);
			}
			settlements.remove(x);
		} else {
			System.out.println("Settlement doesn't exist.");
		}
	}
	
	public void removeRoad(String name, String src, String dst){
		int x = findRoad(name, src, dst);
		int y; //used to store road's index inside settlements' arraylists
		if( x != -1){
			String destName = roads.get(x).getDestinationSettlement().getName();
			String srcName = roads.get(x).getSourceSettlement().getName(); //just in case values passed to the method (src, dst) are mixed up
			y = roads.get(x).getDestinationSettlement().findRoad(name, srcName);
			roads.get(x).getDestinationSettlement().removeRoad(y); //remove road from its destination settlement
			y = roads.get(x).getSourceSettlement().findRoad(name, destName);
			roads.get(x).getSourceSettlement().removeRoad(y);	//remove road from its source settlement
			roads.remove(x);
		} else {
			System.out.println("Road doesn't exist.");
		}
	}
	
	public void clear(){
		roads.clear();
		settlements.clear();
	}
	
	/**
	 * @return the roads
	 */
	public ArrayList<Road> getRoads() {
		return roads;
	}

	public Road getRoad(int i) {
		return roads.get(i);
	}
	/**
	 * @return the settlements
	 */
	public ArrayList<Settlement> getSettlements() {
		return settlements;
	}
	
	public Settlement getSettlement(int i) {
		return settlements.get(i);
	}
	/**
	 * @param roads the roads to set
	 */

	
}
