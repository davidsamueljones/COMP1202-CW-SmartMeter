/**
 * Abstract class of Appliance representing a cooker.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Cooker extends Appliance {
	
	/**
	 * Constructor for Cooker class.
	 * @param  electricUsage Electric use per unit time
	 * @param  gasUsage Gas use per unit time
	 * @param  waterUsage Water use per unit time
	 * @param  timeOn  Duty cycle of cooker [-1 || > 0]
	 */
	public Cooker(int electricUsage, int gasUsage, int waterUsage, int timeOn) {
		super(electricUsage, gasUsage, waterUsage, timeOn);
		
		// Define Appliance tasks on object instantiation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("Cook", getMethod("turnOn"), true, false));
	}

}