/**
 * Abstract class of Appliance representing a cooker
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Cooker extends Appliance {

	/**
	 * Constructor for Cooker class
	 */
	public Cooker(int electricityUse, int gasUse, int waterUse, int timeOn) {
		super(electricityUse, gasUse, waterUse, timeOn);
	}

	/**
	* Method to start the cooker
	*/    
	public void cook() {
		use();
	}   

}