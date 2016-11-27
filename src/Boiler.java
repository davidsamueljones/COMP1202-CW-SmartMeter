/**
 * Class of Appliance representing a boiler
 * Assumed to be an always on Appliance that uses gas only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Boiler extends Appliance {

	/**
	 * Constructor for Boiler class
	 * Assign defaults for boiler
	 */
	public Boiler() {
		this(1);
	}

	/**
	 * Constructor for Boiler class
	 * Set gas by parameter, assign defaults for rest
	 * @param  gasUse Gas use per unit time [>= 0]
	 */
	public Boiler(int gasUse) {
		this(0, gasUse, 0);
	}
	
	/**
	 * Constructor for Boiler class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public Boiler(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, -1);
		
		// Check if arguments are sensible for Appliance type
		verifyUsage(false, false, false);

		// Define Appliance tasks on object instantisation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("TurnOnBoiler", getMethod("turnOn"), true, false));
		addTask(new ApplianceTask("TurnOffBoiler", getMethod("turnOff"), true, true));
	}

	/**
	 * Method to start the boiler
	 */    
	public void turnOn() {
		use();
		System.out.println(String.format("'%s' turned on...", getType()));
	} 

	/**
	 * Method to stop the boiler
	 */    
	public void turnOff() {
		stop();
		System.out.println(String.format("'%s' turned off...", getType()));
	} 

	/**
	 * @inheritDoc
	 */
	@Override
	public String getType() {
		return "Boiler";
	}

}