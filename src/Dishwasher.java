/**
 * Class of Appliance representing a dishwasher
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Dishwasher extends Appliance {

	/**
	 * Constructor for Dishwasher class
	 */
	public Dishwasher() {
		super(2, 0, 1, 6);
	}

	/**
	* Method to start the dishwasher
	*/    
	public void washDishes() {
		use();
	} 

}