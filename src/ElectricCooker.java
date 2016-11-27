/**
 * Class of Cooker representing an electric cooker
 * Assumed to be a duty cycle Appliance that uses electricity only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ElectricCooker extends Cooker {

	/**
	 * Constructor for ElectricCooker class
	 * Assign defaults for ElectricCooker
	 */
	public ElectricCooker() {
		this(5);
	}

	/**
	 * Constructor for ElectricCooker class
	 * Set electricity by parameter, assign defaults for rest
	 * @param  electricityUse Electric use per unit time [>= 0]
	 */
	public ElectricCooker(int electricityUse) {
		this(electricityUse, 0, 0);
	}
	
	/**
	 * Constructor for ElectricCooker class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public ElectricCooker(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, 4);

		// Check if arguments are sensible for Appliance type
		verifyUsage(false, true, true);
		
		// No extra tasks
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String getType() {
		return "Electric cooker";
	}

}