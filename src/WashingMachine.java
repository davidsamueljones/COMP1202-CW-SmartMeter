/**
 * Class of Appliance representing a washing machine
 * Assumed to be a duty cycle Appliance that uses electricity and water only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class WashingMachine extends Appliance {
	
	/**
	 * Constructor for WashingMachine class
	 * Assign defaults for WashingMachine
	 */
	public WashingMachine() {
		this(2, 1);
	}

	/**
	 * Constructor for WashingMachine class
	 * Set electricity and water by parameter, assign defaults for rest
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public WashingMachine(int electricityUse, int waterUse) {
		this(electricityUse, 0, waterUse);
	}
	
	/**
	 * Constructor for WashingMachine class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public WashingMachine(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, 8);

		// Check if arguments are sensible for Appliance type
		verifyUsage(false, true, false);
		
		// Define Appliance tasks on object instantisation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("DoWashing", getMethod("doWashing"), false, false));
	}

	/**
	 * Method to start the washing machine
	 */    
	public void doWashing() {
		use();
		System.out.println(String.format("'%s' is doing washing ...", getType()));
	}   
	
	/**
	 * @inheritDoc
	 */
	@Override
	public String getType() {
		return "Washing machine";
	}

}