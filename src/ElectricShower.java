/**
 * Class of Shower representing an electric shower.
 * Assumed to be a duty cycle Appliance that uses electricity and water only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ElectricShower extends Shower {
	// Kettle Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = 12;
	private final static int DEFAULT_GAS_USAGE = 0;
	private final static int DEFAULT_WATER_USAGE = 4;
	private final static int DEFAULT_TIME_ON = 1;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.ELECTRIC, UtilityType.WATER};
	
	/**
	 * Constructor for ElectricShower class.
	 * Assign defaults for ElectricShower.
	 */
	public ElectricShower() {
		this(DEFAULT_ELECTRIC_USAGE, DEFAULT_WATER_USAGE);
	}

	/**
	 * Constructor for ElectricShower class.
	 * Set electricity and water by parameter, assign defaults for rest.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public ElectricShower(int electricUsage, int waterUsage) {
		this(electricUsage, DEFAULT_GAS_USAGE, waterUsage);
	}
	
	/**
	 * Constructor for ElectricShower class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  gasUsage Gas use per unit time [== 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public ElectricShower(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);

		// Check if arguments are sensible for Appliance type
		checkUsageAllowed(DEFAULT_ALLOWED_CONSUMPTION, false);
		
		// No extra tasks
	}

	@Override
	public String getType() {
		return ApplianceType.ELECTRIC_SHOWER.asString();
	}
	
	@Override
	protected UtilityType[] getAllowedConsumption() {
		return DEFAULT_ALLOWED_CONSUMPTION;
	}
}