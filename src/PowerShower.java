/**
 * Class of Shower representing a power shower
 * Assumed to be an always on Appliance that uses gas and water only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class PowerShower extends Shower {

	/**
	 * Constructor for PowerShower class
	 * Assign defaults for PowerShower
	 */
	public PowerShower() {
		this(10, 5);
	}

	/**
	 * Constructor for PowerShower class
	 * Set gas and water by parameter, assign defaults for rest
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public PowerShower(int gasUse, int waterUse) {
		this(0, gasUse, waterUse);
	}
	
	/**
	 * Constructor for PowerShower class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public PowerShower(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, 1);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String getType() {
		return "Power shower";
	}

}