import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;


public class Application {
	private java.util.Scanner sc;
	private String lastFileName;
	/**
	 * Main function
	 * @param args no arguments expected
	 */
	public static void main(String[] args) {
		Application app = new Application();
		app.run();	
	}
	
	/**
	 * Application constructor
	 */
	public Application() {
		sc = new java.util.Scanner(System.in);
		lastFileName = "Autosave";
	}
	
	/**
	 * Main application function - everything happens here
	 */
	public void run(){
		Map map = new Map();
		sc.useDelimiter("\r?\n|\r");
		initMap(map);	//Load from most recent file automatically
		char choice = printMenu();
		while (choice != 'q'){ 	//check if choice is not to quit. 
			switch(choice){		//If that check is true - stay inside the menu loop.
			case '1':
				map.display(); 
				break;
			case '2':
				addSettlement(map); 
				break;
			case '3':
				removeSettlement(map); 
				break;
			case '4':
				addRoad(map);
				break;
			case '5':
				removeRoad(map);
				break;
			case '6':	//save to file option
				userSave(map);
				break;
			
			case '7':	//load from file option
				userLoad(map);
				break;
			case '8':
				askForSettlementType();
			default:
				System.out.println("Invalid choice.");
				break;
			}
			choice = printMenu();
		}
		try { 						//autosave on quit
			save("autosave", map); //save data to a default file
			saveFileName();			//save filename for the next app startup (if app is closed properly)
		} catch(IOException exc2) { 
			System.out.println("Error while saving.");
		}
		sc.close();
	}
	
	/**
	 * Loads the most recent files to the given Map object (can be overwritten
	 * if user decides to load from a custom file)
	 * Usually 'autosave'. Could be different if app wasn't closed properly the last time.
	 * If no file is found, user is left with empty map.
	 * @param map
	 */
	public void initMap(Map map){
		System.out.println("Loading initial data...");
		System.out.println("Loading filename...");
		boolean success = false;
		try {
			lastFileName = loadFileName();
			System.out.println("Loading from " + lastFileName + "...");
			success = true;
		} catch (IOException io){
			System.out.println("Filename couldn't be loaded. Starting with empty map.");
		}
		if (success == true)
			try {
				load(lastFileName, map);
			} catch (IOException io){
				System.out.println("Error while loading data. Starting with empty map.");
				map.clear();
			}
	}
	
	/**
	 * Removes a road of a given name
	 * @param map map object to remove the road from
	 */
	private void removeRoad(Map map){
		String name, dest, src;
		System.out.print("Name of the road: ");
		name = sc.next();
		System.out.print("Source settlement: ");
		src = sc.next();
		System.out.print("Destination settlement: ");
		dest = sc.next();
		map.removeRoad(name, src, dest);
	}
	
	/**
	 * Creates a new road in a given Map object
	 * @param map Map object to add the road to
	 */
	private void addRoad(Map map){
		String name, nameS, nameD;
		float pop;
		Classification type2;
		System.out.print("Name of the road: ");
		name = sc.next();
		System.out.print("Length of the road: ");
		pop = sc.nextFloat();
		type2 = getRoadType();
		System.out.print("Name of the source settlement: ");
		nameS = sc.next();
		System.out.print("Name of the destination settlement: ");
		nameD = sc.next();
		map.addRoad(name, nameS, nameD, type2, pop);
	}
	
	/**
	 * Lets user add a settlement in a given Map object
	 * @param map Map object to add the settlement to
	 */
	private void addSettlement(Map map){
		String name;
		SettlementType type;
		long pop;
		System.out.print("Name of the settlement: ");
		name = sc.next();
		System.out.print("Population of the settlement: ");
		pop = sc.nextInt();
		type = getSettlementType();
		map.addSettlement(name, type, pop);
	}
	
	/**
	 * Let's user remove a settlement (using its name) from a given Map object
	 * @param map Map object to remove settlement from
	 */
	private void removeSettlement(Map map){
		String name;
		System.out.print("Name of the settlement: ");
		name = sc.next();
		map.removeSettlement(name);
	}
	
	/**
	 * Lets user load data from file
	 * @param map Map object to load the data to
	 */
	private void userLoad(Map map){
		String name;
		System.out.print("\nEnter filename (default: autosave): ");
		name = sc.next();
		try { load(name, map); } catch(IOException exc2) {
			System.out.println("Error while loading.");
		};
	}
	
	/**
	 * Loads data to the application
	 * @param fileName name of the file group
	 * @param map Map object to load the roads and settlements to
	 * @throws IOException File error
	 */
	private void load(String fileName, Map map) throws IOException{
		map.clear();
		System.out.println("Loading file...");
		loadSettlements(fileName + "_settlements.txt", map);
		loadRoads(fileName + "_roads.txt", map);
		System.out.println("File loaded successfully.");
	}
	
	/**
	 * Returns filename of the most recent data
	 * @return	filename
	 * @throws IOException
	 */
	private String loadFileName() throws IOException {
		Scanner file = new Scanner(new FileReader("mapFileName"));
		file.useDelimiter("\r?\n|\r");
		String result = file.nextLine();
		file.close();
		return result;
	}
	
	/**
	 * Loads settlements from file
	 * @param fileName name of the file
	 * @param map Map object to load the roads and settlements to
	 * @throws IOException
	 */
	private void loadSettlements(String fileName, Map map) throws IOException{
		String name;
		long pop;
		String kind;
		Scanner file = new Scanner(new FileReader(fileName));
		file.useDelimiter("\r?\n|\r");
		int numOfSettlements = Integer.parseInt(file.nextLine());
		for (int j = 0; j < numOfSettlements; j++){
			name = file.nextLine();
			pop = Integer.parseInt(file.nextLine());
			kind = file.nextLine();
			map.addSettlement(name, SettlementType.valueOf(kind), pop);
		}
		file.close();
	}
	
	/**
	 * Loads roads from file
	 * @param fileName name of the file
	 * @param map Map object to load the roads and settlements to
	 * @throws IOException
	 */
	private void loadRoads(String fileName, Map map) throws IOException{
		String name;
		float length;
		String classification;
		Scanner file = new Scanner(new FileReader(fileName));
		file.useDelimiter("\r?\n|\r");
		int numOfRoads = Integer.parseInt(file.nextLine());
		for (int j = 0; j < numOfRoads; j++){
			name = file.nextLine();
			length = Float.parseFloat(file.nextLine());
			classification = file.nextLine();
			map.addRoad(name, file.nextLine(), file.nextLine(), Classification.valueOf(classification), length);
		}
		file.close();
	}
	
	/**
	 * Lets user save the map object to defined file
	 * @param map
	 */
	private void userSave(Map map){
		String name;
		System.out.print("\nEnter filename: ");
		name = sc.next();
		try { 
			save(name, map);
			lastFileName = name;
			saveFileName();
		} catch(IOException exc) {
			System.out.println("Error while saving.");
		};
	}
	
	/**
	 * Saves given Map object data to a given group of files
	 * @param fileName name of the file group
	 * @param map Map object to save
	 * @throws IOException file errors
	 */
	private void save(String fileName, Map map) throws IOException{
		System.out.println("Saving file...");
		saveSettlements(fileName + "_settlements.txt", map);
		saveRoads(fileName + "_roads.txt", map);
		System.out.println("File saved successfully.");

	}
	
	/**
	 * Saves settlements to a file
	 * @param fileName name of the file
	 * @param map Map object to save from
	 * @throws IOException
	 */
	private void saveSettlements(String fileName, Map map) throws IOException{
		Settlement s;
		ArrayList<Settlement> settlements = map.getSettlements();
		PrintWriter file = new PrintWriter(new FileWriter(fileName));
		file.println(settlements.size());
		for (int j = 0; j < settlements.size(); j++){
			s = settlements.get(j);
			file.println(s.getName());
			file.println(s.getPopulation());
			file.println(s.getKind());
		}
		file.close();
	}


	 private void askForSettlementType(){ 
	          boolean checked;
	          int pop;
	            do{ 
	                try { 
	                	checked = true;
	                	pop = Integer.parseInt(sc.next());
	                } catch (Exception e){
	                	System.out.println("Error");
	                	checked = false;
	                }
	             }while(!checked); 

	  } 
	/**
	 * Saves roads to a file
	 * @param fileName name of the file
	 * @param map Map object to save from
	 * @throws IOException
	 */
	private void saveRoads(String fileName, Map map) throws IOException{
		Road r;
		ArrayList<Road> roads = map.getRoads();
		PrintWriter file = new PrintWriter(new FileWriter(fileName));
		file.println(roads.size());
		for (int j = 0; j < roads.size(); j++){
			r = roads.get(j);
			file.println(r.getName());
			file.println(r.getLength());
			file.println(r.getClassification());
			file.println(r.getSourceSettlement().getName());
			file.println(r.getDestinationSettlement().getName());
		}
		file.close();
	}
	
	/**
	 * Saves last used file's filename (to load on startup)
	 * @throws IOException
	 */
	private void saveFileName() throws IOException {
		PrintWriter file = new PrintWriter(new FileWriter("mapFileName"));
		file.println(lastFileName);
		file.close();
	}
	
	/**
	 * Gets a proper settlement type from user
	 * @return SettlementType
	 */
	private SettlementType getSettlementType(){
		boolean valid = false;
		SettlementType type = SettlementType.CITY;
		System.out.print("Type of the settlement(TOWN/VILLAGE/CITY/HAMLET): ");
		do{
			try {
				type = SettlementType.valueOf(sc.next().toUpperCase());
				valid = true;
			} catch (IllegalArgumentException iae){
				System.out.print("Invalid settlement type (TOWN/VILLAGE/CITY/HAMLET expected). \nTry again: ");
			}
		} while (!valid);
		return type;
	}
	
	/**
	 * Gets a proper road type from user
	 * @return Classification
	 */
	private Classification getRoadType(){
		boolean valid = false;
		Classification type = Classification.M;
		System.out.print("Type of the road(M/A/B/U): ");
		do{
			try {
				type = Classification.valueOf(sc.next().toUpperCase());
				valid = true;
			} catch (IllegalArgumentException iae){
				System.out.print("Invalid road type (M/A/B/U expected). \nTry again: ");
			}
		} while (!valid);
		return type;
	}
	
	/**
	 * Prints out menu and gets user input
	 * @return
	 */
	private char printMenu() {
		System.out
				.println("\n================\nMenu: \n 1 - Display the map \n 2 - Create settlement \n 3 - Remove settlement "
						+ "\n 4 - Create road \n 5 - Remove road \n 6 - save to custom file \n 7 - load from custom file \nq = quit\n================");
		return sc.next().charAt(0);
	}
	


}
