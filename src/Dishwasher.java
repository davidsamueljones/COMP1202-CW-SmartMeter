/**
 * Class of Appliance representing a dishwasher
 * Assumed to be a duty cycle Appliance that uses electricity and water only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Dishwasher extends Appliance {

	/**
	 * Constructor for Dishwasher class
	 * Assign defaults for Dishwasher
	 */
	public Dishwasher() {
		this(2, 1);
	}

	/**
	 * Constructor for Dishwasher class
	 * Set electricity and water by parameter, assign defaults for rest
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public Dishwasher(int electricityUse, int waterUse) {
		this(electricityUse, 0, waterUse);
	}
	
	/**
	 * Constructor for Dishwasher class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public Dishwasher(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, 6);

		// Check if arguments are sensible for Appliance type
		verifyUsage(false, true, false);
		
		// Define Appliance tasks on object instantisation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("WashDishes", getMethod("washDishes"), false, false));
	}

	/**
	 * Method to start the dishwasher
	 */    
	public void washDishes() {
		use();
		System.out.println(String.format("'%s' is washing dishes...", getType()));
	} 

	/**
	 * @inheritDoc
	 */
	@Override
	public String getType() {
		return "Dishwasher";
	}

}