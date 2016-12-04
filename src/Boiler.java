/**
 * Class of Appliance representing a boiler.
 * Assumed to be an always on Appliance, which can be toggled, that that uses gas only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Boiler extends Appliance {
	// Boiler Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = 0;
	private final static int DEFAULT_GAS_USAGE = 1;
	private final static int DEFAULT_WATER_USAGE = 0;
	private final static int DEFAULT_TIME_ON = -1;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.ELECTRIC, UtilityType.GAS, UtilityType.WATER};
	
	/**
	 * Constructor for Boiler class.
	 * Assign defaults for boiler.
	 */
	public Boiler() {
		this(DEFAULT_GAS_USAGE);
	}

	/**
	 * Constructor for Boiler class.
	 * Set gas by parameter, assign defaults for rest.
	 * @param  gasUsage Gas use per unit time [>= 0]
	 */
	public Boiler(int gasUsage) {
		this(DEFAULT_ELECTRIC_USAGE, gasUsage, DEFAULT_WATER_USAGE);
	}
	
	/**
	 * Constructor for Boiler class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  gasUsage Gas use per unit time [>= 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public Boiler(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);
		
		// Define Appliance tasks on object instantiation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("TurnOnBoiler", getMethod("turnOn"), true, false));
		addTask(new ApplianceTask("TurnOffBoiler", getMethod("turnOff"), true, true));
	}

	@Override
	public String getType() {
		return ApplianceType.BOILER.asString();
	}

	@Override
	protected UtilityType[] getAllowedConsumption() {
		return DEFAULT_ALLOWED_CONSUMPTION;
	}

}