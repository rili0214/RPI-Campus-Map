package hw7;

public class Arch implements Comparable<Arch> {
	
	private String label;
	private String number;
	private Double x;
	private Double y;

	/**
	 * Construct a new Arch object.
	 *
	 * @param String label the name of the construction
	 * @param String number the building #
	 * @param Double x the horizontal coordinate of the construction
	 * @param Double y the vertical coordinate of the construction
	 * @requires none
	 * @modifies none
	 * @effects construct a new Arch object
	 * @throws none
	 * @return none
	 */
	public Arch(String label, String number, Double x, Double y) {
		this.label = new String(label);
		this.number = new String(number);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the name of the construction.
	 *
	 * @param none
	 * @requires none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return the name of the construction
	 */
	public String get_label() {
		return new String(this.label);
	}
	
	/**
	 * Get the number of the construction.
	 *
	 * @param none
	 * @requires none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return the number of the construction
	 */
	public String get_number() {
		return new String(this.number);
	}
	
	/**
	 * Get the horizontal coordinate of the construction.
	 *
	 * @param none
	 * @requires none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return the horizontal coordinate of the construction
	 */
	public Double get_x() {
		return this.x;
	}
	
	/**
	 * Get the vertical coordinate of the construction.
	 *
	 * @param none
	 * @requires none
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return the vertical coordinate of the construction
	 */
	public Double get_y() {
		return this.y;
	}
	
	/**
	 * Compare the 2 Arch objects
	 *
	 * @param Arch other the object will be compared
	 * @requires others != null
	 * @modifies none
	 * @effects none
	 * @throws none
	 * @return 0 if this == other; < 0 if this < other; > 0 if this > other
	 */
	@Override
	public int compareTo(Arch other) { 
		if (this.label.equals(other.label)) {
			return number.compareTo(other.number);
		} else {
			return label.compareTo(other.label);
		}
	}
}
