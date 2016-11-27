/**
 * Class of Appliance representing a kettle
 * Assumed to be a duty cycle Appliance that uses electricity and water only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Kettle extends Appliance {

	/**
	 * Constructor for Kettle class
	 * Assign defaults for Kettle
	 */
	public Kettle() {
		this(20, 1);
	}

	/**
	 * Constructor for Kettle class
	 * Set electricity and water by parameter, assign defaults for rest
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public Kettle(int electricityUse, int waterUse) {
		this(electricityUse, 0, waterUse);
	}
	
	/**
	 * Constructor for Kettle class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public Kettle(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, 1);

		// Check if arguments are sensible for Appliance type
		verifyUsage(false, true, false);
		
		// Define Appliance tasks on object instantisation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("Boil", getMethod("boil"), true, false));
	}

	/**
	 * Method to start the kettle
	 */    
	public void boil() {
		use();
		System.out.println(String.format("'%s' is boiling water...", getType()));
	} 

	/**
	 * @inheritDoc
	 */
	@Override
	public String getType() {
		return "Kettle";
	}

}