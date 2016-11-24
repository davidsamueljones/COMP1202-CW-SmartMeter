/**
 * Class of Cooker representing a gas cooker
 * Assumed to be a duty cycle Appliance that uses gas only 
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class GasCooker extends Cooker {

	/**
	 * Constructor for GasCooker class
	 * Assign defaults for GasCooker
	 */
	public GasCooker() {
		this(4);
	}

	/**
	 * Constructor for GasCooker class
	 * Set gas by parameter, assign defaults for rest
	 * @param  gasUse Gas use per unit time [>= 0]
	 */
	public GasCooker(int gasUse) {
		this(0, gasUse, 0);
	}
	
	/**
	 * Constructor for GasCooker class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public GasCooker(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, 4);

		// Check if arguments are sensible for Appliance type
		if (waterUse != 0) {
			throw new IllegalArgumentException("[ERROR] This appliance cannot use water");
		}
	}

}	