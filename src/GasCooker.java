/**
 * Class of Cooker representing a gas cooker.
 * Assumed to be a duty cycle Appliance that uses gas only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class GasCooker extends Cooker {
	// Boiler Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = 0;
	private final static int DEFAULT_GAS_USAGE = 4;
	private final static int DEFAULT_WATER_USAGE = 0;
	private final static int DEFAULT_TIME_ON = 4;
	private final static UtilityType[] DEFAULT_ALLOWED_CONSUMPTION = {UtilityType.GAS};
	
	/**
	 * Constructor for GasCooker class
	 * Assign defaults for GasCooker
	 */
	public GasCooker() {
		this(DEFAULT_GAS_USAGE);
	}

	/**
	 * Constructor for GasCooker class
	 * Set gas by parameter, assign defaults for rest
	 * @param  gasUsage Gas use per unit time [>= 0]
	 */
	public GasCooker(int gasUsage) {
		this(DEFAULT_ELECTRIC_USAGE, gasUsage, DEFAULT_WATER_USAGE);
	}
	
	/**
	 * Constructor for GasCooker class
	 * Set usage by parameters, only allowing appropriate usage types
	 * @param  electricUsage Electric use per unit time [== 0]
	 * @param  gasUsage Gas use per unit time [>= 0]
	 * @param  waterUsage Water use per unit time [== 0]
	 */
	public GasCooker(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);

		// No extra tasks
	}

	@Override
	public String getType() {
		return ApplianceType.GAS_COOKER.asString();
	}
	
	@Override
	protected UtilityType[] getAllowedConsumption() {
		return DEFAULT_ALLOWED_CONSUMPTION;
	}
}	