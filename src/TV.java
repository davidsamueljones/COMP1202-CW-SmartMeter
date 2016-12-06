/**
 * Class of Appliance representing a TV.
 * Assumed to be an always on Appliance, which can be toggled, that uses electricity only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class TV extends Appliance {
	// TV Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = 1;
	private final static int DEFAULT_GAS_USAGE = 0;
	private final static int DEFAULT_WATER_USAGE = 0;
	private final static int DEFAULT_TIME_ON = -1;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.ELECTRIC};

	/**
	 * Constructor for TV class.
	 * Assign defaults for TV.
	 */
	public TV() {
		this(DEFAULT_ELECTRIC_USAGE);
	}

	/**
	 * Constructor for TV class.
	 * Set electricity by parameter, assign defaults for rest.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 */
	public TV(int electricUsage) {
		this(electricUsage, DEFAULT_GAS_USAGE, DEFAULT_WATER_USAGE);
	}
	
	/**
	 * Constructor for TV class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  gasUsage Gas use per unit time [== 0]
	 * @param  waterUsage Water use per unit time [== 0]
	 */
	public TV(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);

		// Define Appliance tasks on object instantiation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("TurnOnTV", getMethod("turnOn"), false, false));
		addTask(new ApplianceTask("TurnOffTV", getMethod("turnOff"), true, true));
	}

	@Override
	public String getType() {
		return ApplianceType.TV.asString();
	}
	
	@Override
	protected UtilityType[] getAllowedConsumption() {
		return DEFAULT_ALLOWED_CONSUMPTION;
	}
}