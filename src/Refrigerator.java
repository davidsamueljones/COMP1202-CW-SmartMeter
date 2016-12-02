/**
 * Class of Appliance representing a refrigerator.
 * Assumed to be an always on Appliance that uses electricity only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Refrigerator extends Appliance {
	// Refrigerator Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = 1;
	private final static int DEFAULT_GAS_USAGE = 0;
	private final static int DEFAULT_WATER_USAGE = 0;
	private final static int DEFAULT_TIME_ON = -1;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.ELECTRIC};
	private final static UtilityType[] DEFAULT_ALLOWED_GENERATION = {};
	
	/**
	 * Constructor for Refrigerator class.
	 * Assign defaults for Refrigerator.
	 */
	public Refrigerator() {
		this(DEFAULT_ELECTRIC_USAGE);
	}

	/**
	 * Constructor for Refrigerator class.
	 * Set electricity by parameter, assign defaults for rest.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 */
	public Refrigerator(int electricUsage) {
		this(electricUsage, DEFAULT_GAS_USAGE, DEFAULT_WATER_USAGE);
	}
	
	/**
	 * Constructor for Refrigerator class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  gasUsage Gas use per unit time [>= 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public Refrigerator(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);
		
		// Check if arguments are sensible for Appliance type
		checkUsageAllowed(DEFAULT_ALLOWED_CONSUMPTION, false);
		checkUsageAllowed(DEFAULT_ALLOWED_GENERATION, true);
		
		// Appliance is always on
		turnOn();
		
		// No extra tasks
	}
	
	@Override
	public String getType() {
		return ApplianceType.REFRIGERATOR.asString();
	}

}