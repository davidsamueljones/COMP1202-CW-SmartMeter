/**
 * Class of Appliance representing a dishwasher.
 * Assumed to be a duty cycle Appliance that uses electricity and water only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Dishwasher extends Appliance {
	// Dishwasher Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = 2;
	private final static int DEFAULT_GAS_USAGE = 0;
	private final static int DEFAULT_WATER_USAGE = 1;
	private final static int DEFAULT_TIME_ON = 6;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.ELECTRIC, UtilityType.WATER};
	
	/**
	 * Constructor for Dishwasher class.
	 * Assign defaults for Dishwasher.
	 */
	public Dishwasher() {
		this(DEFAULT_ELECTRIC_USAGE, DEFAULT_WATER_USAGE);
	}

	/**
	 * Constructor for Dishwasher class.
	 * Set electricity and water by parameter, assign defaults for rest.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public Dishwasher(int electricUsage, int waterUsage) {
		this(electricUsage, DEFAULT_GAS_USAGE, waterUsage);
	}
	
	/**
	 * Constructor for Dishwasher class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  gasUsage Gas use per unit time [== 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public Dishwasher(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);

		// Check if arguments are sensible for Appliance type
		checkUsageAllowed(DEFAULT_ALLOWED_CONSUMPTION, false);
		
		// Define Appliance tasks on object instantiation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("WashDishes", getMethod("turnOn"), false, false));
	}

	@Override
	public String getType() {
		return ApplianceType.DISHWASHER.asString();
	}
	
	@Override
	protected UtilityType[] getAllowedConsumption() {
		return DEFAULT_ALLOWED_CONSUMPTION;
	}
}