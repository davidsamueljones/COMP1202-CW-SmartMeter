/**
 * Class of Cooker representing an electric cooker.
 * Assumed to be a duty cycle Appliance that uses electricity only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ElectricCooker extends Cooker {
	// Kettle Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = 5;
	private final static int DEFAULT_GAS_USAGE = 0;
	private final static int DEFAULT_WATER_USAGE = 0;
	private final static int DEFAULT_TIME_ON = 4;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.ELECTRIC};
	
	/**
	 * Constructor for ElectricCooker class.
	 * Assign defaults for ElectricCooker.
	 */
	public ElectricCooker() {
		this(DEFAULT_ELECTRIC_USAGE);
	}

	/**
	 * Constructor for ElectricCooker class.
	 * Set electricity by parameter, assign defaults for rest.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 */
	public ElectricCooker(int electricUsage) {
		this(electricUsage, DEFAULT_GAS_USAGE, DEFAULT_WATER_USAGE);
	}
	
	/**
	 * Constructor for ElectricCooker class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  gasUsage Gas use per unit time [== 0]
	 * @param  waterUsage Water use per unit time [== 0]
	 */
	public ElectricCooker(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);

		// Check if arguments are sensible for Appliance type
		checkUsageAllowed(DEFAULT_ALLOWED_CONSUMPTION, false);
		
		// No extra tasks
	}

	@Override
	public String getType() {
		return ApplianceType.ELECTRIC_COOKER.asString();
	}
	
	@Override
	protected UtilityType[] getAllowedConsumption() {
		return DEFAULT_ALLOWED_CONSUMPTION;
	}
}