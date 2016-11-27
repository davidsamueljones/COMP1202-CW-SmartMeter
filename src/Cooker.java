/**
 * Abstract class of Appliance representing a cooker
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Cooker extends Appliance {

	/**
	 * Constructor for Cooker class
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 * @param  timeOn  Duty cycle of cooker [-1 || > 0]
	 */
	public Cooker(int electricityUse, int gasUse, int waterUse, int timeOn) {
		super(electricityUse, gasUse, waterUse, timeOn);
		
		// Define Appliance tasks on object instantisation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("Cook", getMethod("cook"), true, false));
	}

	/**
	 * Method to start the cooker
	 */    
	public void cook() {
		use();
		System.out.println(String.format("'%s' is cooking...", getType()));
	}   

}