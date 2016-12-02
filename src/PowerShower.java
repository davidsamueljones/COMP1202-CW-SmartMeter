/**
 * Class of Shower representing a power shower.
 * Assumed to be an always on Appliance that uses gas and water only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class PowerShower extends Shower {
	// PowerShower Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = 0;
	private final static int DEFAULT_GAS_USAGE = 10;
	private final static int DEFAULT_WATER_USAGE = 5;
	private final static int DEFAULT_TIME_ON = 1;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.GAS, UtilityType.WATER};
	private final static UtilityType[] DEFAULT_ALLOWED_GENERATION = {};
	
	/**
	 * Constructor for PowerShower class.
	 * Assign defaults for PowerShower.
	 */
	public PowerShower() {
		this(DEFAULT_GAS_USAGE, DEFAULT_WATER_USAGE);
	}

	/**
	 * Constructor for PowerShower class.
	 * Set gas and water by parameter, assign defaults for rest.
	 * @param  gasUsage Gas use per unit time [>= 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public PowerShower(int gasUsage, int waterUsage) {
		this(DEFAULT_ELECTRIC_USAGE, gasUsage, waterUsage);
	}
	
	/**
	 * Constructor for PowerShower class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  gasUsage Gas use per unit time [>= 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public PowerShower(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);
		
		// Check if arguments are sensible for Appliance type
		checkUsageAllowed(DEFAULT_ALLOWED_CONSUMPTION, false);
		checkUsageAllowed(DEFAULT_ALLOWED_GENERATION, true);
		
		// No extra tasks
	}

	@Override
	public String getType() {
		return ApplianceType.POWER_SHOWER.asString();
	}

}