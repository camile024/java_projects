import java.util.ArrayList;

/**
 * A settlement with all the roads connecting to it, population and a type
 * @author kac12
 *
 */
public class Settlement {
	/**
	 * Maximum number of roads connected to a settlement
	 */
	public static final int MAX_ROADS = 100;
	private String name;
	private long population;
	private SettlementType kind;
	private ArrayList<Road> roads = new ArrayList<Road>();
	
	/**
	 * Adds a road connecting to the settlement
	 * @param road a road that connects to the settlement
	 */
	public void addRoad(Road road){
		if (roads.size() < MAX_ROADS){
			roads.add(road);
		} else {
			System.out.println("Settlement reached max roads. Road couldn't be added.");	
		}
	}
	
	/**
	 * Removes a road from roads ArrayList
	 * @param road index of the road to remove
	 */
	public void removeRoad(int road){
		if (roads.get(road) != null){
			roads.remove(road);	
		} else {
			System.out.println("Road not found.");
		}
	}
	
	/**
	 * Finds a road and returns its index in the arraylist
	 * Returns -1 if hasn't been found
	 * @param road name of the road
	 * @param dest name of the other settlement, the road is connected to
	 * @return
	 */
	public int findRoad(String road, String dest){
		for (Road x : roads){
			String rName = x.getName();
			String srcName = x.getSourceSettlement().getName();
			String destName = x.getDestinationSettlement().getName();
			if ( 
					rName.equals(road) && 
					( destName.equals(dest) || srcName.equals(dest) )
				){
				return roads.indexOf(x);
			}
		}
		return -1;
	}
	
	/**
	 * Initialises a settlement
	 * @param name name of new settlement
	 * @param k type of the settlement (TOWN, VILLAGE, HAMLET, CITY)
	 */
	public Settlement(String name, SettlementType k, long pop){
		this.name = name;
		kind = k;
		population = pop;
	}
	
	public String toString(){
		String result = "";
		for (Road x : roads){
			if (this == x.getDestinationSettlement()){
				result += ("\n\t\t" + x.getName() + " connected to " + x.getSourceSettlement().getName());
			} else {
				result += ("\n\t\t" + x.getName() + " connected to " + x.getDestinationSettlement().getName());
			}
		}
		return ("\n>>Settlement name: " + name + " | population: " + population + " | type: " + kind + "\n "
				+ "\troads: " + result + "");
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the population
	 */
	public long getPopulation() {
		return population;
	}
	/**
	 * @param population the population to set
	 */
	public void setPopulation(long population) {
		this.population = population;
	}
	/**
	 * @return the kind
	 */
	public SettlementType getKind() {
		return kind;
	}
	/**
	 * @param kind the kind to set
	 */
	public void setKind(SettlementType kind) {
		this.kind = kind;
	}
	/**
	 * @return the roads
	 */
	public ArrayList<Road> getRoads() {
		return roads;
	}
	/**
	 * @param roads the roads to set
	 */
	public void setRoads(ArrayList<Road> roads) {
		this.roads = roads;
	}
	/**
	 * @return the numRoads
	 */
	public int getNumRoads() {
		return roads.size();
	}

}
