/**
 * Class of Appliance representing a wind turbine.
 * Assumed to be an always on Appliance that can generate electricity only.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class WindTurbine extends Appliance {
	// WindTurbine Defaults
	private final static int DEFAULT_ELECTRIC_USAGE = -3; // Negative value implies generation
	private final static int DEFAULT_GAS_USAGE = 0;
	private final static int DEFAULT_WATER_USAGE = 0;
	private final static int DEFAULT_TIME_ON = -1;
	private final static UtilityType[] DEFAULT_ALLOWED_GENERATION = {UtilityType.ELECTRIC};
	
	/**
	 * Constructor for Refrigerator class.
	 * Assign defaults for Refrigerator.
	 */
	public WindTurbine() {
		this(DEFAULT_ELECTRIC_USAGE);
	}

	/**
	 * Constructor for WindTubine class.
	 * Set electricity by parameter, assign defaults for rest.
	 * @param  electricUsage Electric use per unit time [<= 0]
	 */
	public WindTurbine(int electricUsage) {
		this(electricUsage, DEFAULT_GAS_USAGE, DEFAULT_WATER_USAGE);
	}
	
	/**
	 * Constructor for WindTubine class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  electricUsage Electric use per unit time [<= 0]
	 * @param  gasUsage Gas use per unit time [== 0]
	 * @param  waterUsage Water use per unit time [== 0]
	 */
	public WindTurbine(int electricUsage, int gasUsage, int waterUsage) {
		super(electricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);
		
		// Appliance is always on
		turnOn();
		
		// No extra tasks
	}
	
	@Override
	public String getType() {
		return ApplianceType.WIND_TURBINE.asString();
	}

	@Override
	protected UtilityType[] getAllowedGeneration() {
		return DEFAULT_ALLOWED_GENERATION;
	}
	
}