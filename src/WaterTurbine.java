/**
 * Class of Appliance representing a water turbine.
 * Assumed to be an Appliance, which can be toggled, that generates electricity,
 * using up water as it flows through the system (imagine an energy recovery system in a sink).
 * Extension implemented to show how an Appliance can both generate and consume at the same time.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class WaterTurbine extends Appliance {
	// WaterTurbine Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = -4; // Negative value implies generation
	private final static int DEFAULT_GAS_USAGE = 0;
	private final static int DEFAULT_WATER_USAGE = 1;
	private final static int DEFAULT_TIME_ON = -1;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.WATER};
	private final static UtilityType[] DEFAULT_ALLOWED_GENERATION = {UtilityType.ELECTRIC};
	
	/**
	 * Constructor for WaterTurbine class.
	 * Assign defaults for WaterTurbine.
	 */
	public WaterTurbine() {
		this(DEFAULT_ELECTRIC_USAGE, DEFAULT_WATER_USAGE);
	}

	/**
	 * Constructor for WaterTurbine class.
	 * Set electricity by parameter, assign defaults for rest.
	 * @param  electricUsage Electric use per unit time [<= 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public WaterTurbine(int electricUsage, int waterUsage) {
		this(electricUsage, DEFAULT_GAS_USAGE, waterUsage);
	}
	
	/**
	 * Constructor for WaterTurbine class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [<= 0]
	 * @param  gasUsage Gas use per unit time [== 0]
	 * @param  waterUsage Water use per unit time [>= 0]
	 */
	public WaterTurbine(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);
		
		// Define Appliance tasks on object instantiation
		// An exception is thrown if the task method does not exist
		addTask(new ApplianceTask("TurnOnWaterTurbine", getMethod("turnOn"), true, false));
		addTask(new ApplianceTask("TurnOffWaterTurbine", getMethod("turnOff"), true, true));
	}
	
	@Override
	public String getType() {
		return ApplianceType.WATER_TURBINE.asString();
	}

	@Override
	protected UtilityType[] getAllowedConsumption() {
		return DEFAULT_ALLOWED_CONSUMPTION;
	}
	
	@Override
	protected UtilityType[] getAllowedGeneration() {
		return DEFAULT_ALLOWED_GENERATION;
	}
	
}