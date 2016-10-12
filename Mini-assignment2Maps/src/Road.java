/**
 * The road connecting two of the settlements (has a name, classification and two settlements)
 * @author kac12
 *
 */
public class Road {
	private String name;
	private Classification classification;
	private Settlement sourceSettlement;
	private Settlement destinationSettlement;
	private float length;
	
	
	/**
	 * Creates a new road
	 * @param n name of the new road
	 * @param src source settlement of the new road
	 * @param dst destination settlement of the new road
	 * @param cls classification of the new road (M/A/B/U)
	 */
	public Road(String n, Settlement src, Settlement dst, Classification cls, float len){
		name = n;
		src.addRoad(this);
		dst.addRoad(this);
		destinationSettlement = dst;
		sourceSettlement = src;
		classification = cls;
		length = len;
	}
	
	public String toString(){
		return ("\nRoad " + name + "| classification " + classification + " \n\tSource: " 
				+ sourceSettlement.getName() + "\n\tDestination: " + destinationSettlement.getName() +"");
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
	 * @return the classification
	 */
	public Classification getClassification() {
		return classification;
	}
	/**
	 * @param classification the classification to set
	 */
	public void setClassification(Classification classification) {
		this.classification = classification;
	}
	/**
	 * @return the sourceSettlement
	 */
	public Settlement getSourceSettlement() {
		return sourceSettlement;
	}
	/**
	 * @param sourceSettlement the sourceSettlement to set
	 */
	public void setSourceSettlement(Settlement sourceSettlement) {
		this.sourceSettlement = sourceSettlement;
	}
	/**
	 * @return the destinationSettlement
	 */
	public Settlement getDestinationSettlement() {
		return destinationSettlement;
	}
	/**
	 * @param destinationSettlement the destinationSettlement to set
	 */
	public void setDestinationSettlement(Settlement destinationSettlement) {
		this.destinationSettlement = destinationSettlement;
	}

	/**
	 * @return the length
	 */
	public float getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}
}
