/**
 * Abstract class of Appliance representing a shower
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Shower extends Appliance {

	/**
	* Constructor for Shower class
	*/
	public Shower(int electricityUse, int gasUse, int waterUse, int timeOn) {
		super(electricityUse, gasUse, waterUse, timeOn);
	}
	
	/**
	* Method to start the shower
	*/    
	public void shower() {
		use();
	}   
	
}