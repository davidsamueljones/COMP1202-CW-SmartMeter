/**
 * Class of Appliance representing a night light
 * Assumed to be an always on Appliance that uses electricity only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class NightLight extends Appliance {

	/**
	 * Constructor for NightLight class
	 * Assign defaults for NightLight
	 */
	public NightLight() {
		this(1);
	}

	/**
	 * Constructor for NightLight class
	 * Set electricity by parameter, assign defaults for rest
	 * @param  electricityUse Electric use per unit time [>= 0]
	 */
	public NightLight(int electricityUse) {
		this(electricityUse, 0, 0);
	}
	
	/**
	 * Constructor for NightLight class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public NightLight(int electricityUse, int gasUse, int waterUse) {
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
	 * Method to start the NightLight
	 */    
	public void turnOn() {
		use();
		System.out.println(String.format("'%s' turned on...", getType()));
	} 

	/**
	 * Method to stop the NightLight
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
		return "Night light";
	}

}