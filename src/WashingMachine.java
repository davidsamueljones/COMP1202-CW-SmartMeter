/**
 * Class of Appliance representing a washing machine
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class WashingMachine extends Appliance {

	/**
	 * Constructor for WashingMachine class
	 */
	public WashingMachine() {
		super(2, 0, 1, 8);
	}

	/**
	* Method to start the washing machine
	*/    
	public void doWashing() {
		use();
	}   
	
}