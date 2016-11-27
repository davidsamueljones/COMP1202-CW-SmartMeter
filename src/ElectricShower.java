/**
 * Class of Shower representing an electric shower
 * Assumed to be a duty cycle Appliance that uses electricity and water only
 * Duty cycle always default
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ElectricShower extends Shower {

	/**
	 * Constructor for ElectricShower class
	 * Assign defaults for ElectricShower
	 */
	public ElectricShower() {
		this(12, 4);
	}

	/**
	 * Constructor for ElectricShower class
	 * Set electricity and water by parameter, assign defaults for rest
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public ElectricShower(int electricityUse, int waterUse) {
		this(electricityUse, 0, waterUse);
	}
	
	/**
	 * Constructor for ElectricShower class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 */
	public ElectricShower(int electricityUse, int gasUse, int waterUse) {
		super(electricityUse, gasUse, waterUse, 1);

		// Check if arguments are sensible for Appliance type
		verifyUsage(false, true, false);
		
		// No extra tasks
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String getType() {
		return "Electric shower";
	}

}