/**
 * Class of Appliance representing a TV
 * Assumed to be an always on Appliance that uses electricity only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class TV extends Appliance {

	/**
	 * Constructor for TV class
	 * Assign defaults for TV
	 */
	public TV() {
		this(1);
	}

	/**
	 * Constructor for TV class
	 * Set electricity by parameter, assign defaults for rest
	 * @param  electricityUse Electric use per unit time [>= 0]
	 */
	public TV(int electricityUse) {
		this(electricityUse, 0, 0);
	}
	
	/**
	 * Constructor for TV class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public TV(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, -1);

		// Check if arguments are sensible for Appliance type
		if (gasUse != 0) {
			throw new IllegalArgumentException("[ERROR] This appliance cannot use gas");
		}
		if (waterUse != 0) {
			throw new IllegalArgumentException("[ERROR] This appliance cannot use water");
		}
	}

	/**
	 * Method to start the TV
	 */    
	public void turnOn() {
		use();
		System.out.println(String.format("'%s' turned on...", getType()));
	} 

	/**
	 * Method to stop the TV
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
		return "TV";
	}

}