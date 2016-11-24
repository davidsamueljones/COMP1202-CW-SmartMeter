/**
 * Class of Appliance representing a refrigerator
 * Assumed to be an always on Appliance that uses electricity only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Refrigerator extends Appliance {

	/**
	 * Constructor for Refrigerator class
	 * Assign defaults for Refrigerator
	 */
	public Refrigerator() {
		this(1);
	}

	/**
	 * Constructor for Refrigerator class
	 * Set electricity by parameter, assign defaults for rest
	 * @param  electricityUse Electric use per unit time [>= 0]
	 */
	public Refrigerator(int electricityUse) {
		this(electricityUse, 0, 0);
	}
	
	/**
	 * Constructor for Refrigerator class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public Refrigerator(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, -1);
		
		// Check if arguments are sensible for Appliance type
		if (gasUse != 0) {
			throw new IllegalArgumentException("[ERROR] This appliance cannot use gas");
		}
		if (waterUse != 0) {
			throw new IllegalArgumentException("[ERROR] This appliance cannot use water");
		}
	}
	
}