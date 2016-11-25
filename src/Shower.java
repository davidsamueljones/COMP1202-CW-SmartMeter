/**
 * Abstract class of Appliance representing a shower
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Shower extends Appliance {

	/**
	 * Constructor for Shower class
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 * @param  timeOn  Duty cycle of cooker [-1 || > 0]
	 */
	public Shower(int electricityUse, int gasUse, int waterUse, int timeOn) {
		super(electricityUse, gasUse, waterUse, timeOn);
	}
	
	/**
	 * Method to start the shower
	 */    
	public void shower() {
		use();
		System.out.println("Shower has been turned on...");
	}   

}