/**
 * Class of Appliance representing a kettle.
 * Assumed to be a duty cycle Appliance that uses electricity and water only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Kettle extends Appliance {
	// Kettle Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = 20;
	private final static int DEFAULT_GAS_USAGE = 0;
	private final static int DEFAULT_WATER_USAGE = 1;
	private final static int DEFAULT_TIME_ON = 1;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.ELECTRIC, UtilityType.WATER};
	
	/**
	 * Constructor for Kettle class.
	 * Assign defaults for Kettle.
	 */
	public Kettle() {
		this(DEFAULT_ELECTRIC_USAGE, DEFAULT_WATER_USAGE);
	}

	/**
	 * Constructor for Kettle class.
	 * Set electricity and water by parameter, assign defaults for rest.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public Kettle(int electricUsage, int waterUsage) {
		this(electricUsage, DEFAULT_GAS_USAGE, waterUsage);
	}
	
	/**
	 * Constructor for Kettle class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [>= 0]
	 * @param  gasUsage Gas use per unit time [== 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public Kettle(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);
		
		// Define Appliance tasks on object instantiation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("Boil", getMethod("turnOn"), true, false));
	}
	
	@Override
	public String getType() {
		return ApplianceType.KETTLE.asString();
	}
	
	@Override
	protected UtilityType[] getAllowedConsumption() {
		return DEFAULT_ALLOWED_CONSUMPTION;
	}
}