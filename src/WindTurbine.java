/**
 * Class of Appliance representing a wind turbine.
 * Assumed to be an always on Appliance that can generate electricity only.
 * Electricity generation fluctuates every unit time and is calculated using a wind modifier (constrained by a maximum).
 * The wind modifier fluctuates every unit time with a random value of sway (constrained by a maximum).
 * This modification has been implemented by overriding the timePasses method.
 * Duty cycle always default.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class WindTurbine extends Appliance {
	// WindTurbine Defaults
	private final static int DEFAULT_MAX_ELECTRIC_USAGE = -6; // Negative value implies generation
	private final static int DEFAULT_GAS_USAGE = 0;
	private final static int DEFAULT_WATER_USAGE = 0;
	private final static int DEFAULT_TIME_ON = -1;
	private final static UtilityType[] DEFAULT_ALLOWED_GENERATION = {UtilityType.ELECTRIC};
	
	private final static Double DEFAULT_WIND_MODIFIER = null;
	private final static double MIN_SWAY = 0;
	private final static double MAX_SWAY = 0.1;
	private final static double MIN_WIND_MODIFIER = 0;
	private final static double MAX_WIND_MODIFIER = 1;
	
	// Appliance properties
	private int maxElectricUsage;
	private double windModifier;
	
	/**
	 * Constructor for Refrigerator class.
	 * Assign defaults for Refrigerator.
	 */
	public WindTurbine() {
		this(DEFAULT_MAX_ELECTRIC_USAGE);
	}

	/**
	 * Constructor for WindTubine class.
	 * Set electricity by parameter, assign defaults for rest.
	 * @param  maxElectricUsage Maximum electric use per unit time [<= 0]
	 */
	public WindTurbine(int maxElectricUsage) {
		this(maxElectricUsage, DEFAULT_GAS_USAGE, DEFAULT_WATER_USAGE, DEFAULT_WIND_MODIFIER);
	}
	
	/**
	 * Constructor for WindTubine class.
	 * Set windModifier by parameter, assign defaults for rest.
	 * @param  windModifier Starting value of wind modifier [0 <= n <= 1]
	 */
	public WindTurbine(Double windModifier) {
		this(DEFAULT_MAX_ELECTRIC_USAGE, DEFAULT_GAS_USAGE, DEFAULT_WATER_USAGE, windModifier);
	}
	
	/**
	 * Constructor for WindTubine class.
	 * Set electricity and wind modifier by parameter, assign defaults for rest.
	 * @param  maxElectricUsage Maximum electric use per unit time [<= 0]
	 * @param  windModifier Starting value of wind modifier [0 <= n <= 1]
	 */
	public WindTurbine(int maxElectricUsage, Double windModifier) {
		this(maxElectricUsage, DEFAULT_GAS_USAGE, DEFAULT_WATER_USAGE, windModifier);
	}
	
	/**
	 * Constructor for WindTubine class.
	 * Set usage by parameters, only allowing appropriate usage types.
	 * @param  maxElectricUsage Maximum electric use per unit time [<= 0]
	 * @param  gasUsage Gas use per unit time [== 0]
	 * @param  waterUsage Water use per unit time [== 0]
	 * @param  windModifier Starting value of wind modifier [0 <= n <= 1]
	 */
	public WindTurbine(int maxElectricUsage, int gasUsage, int waterUsage, Double windModifier) {
		// Still pas maxElectricUsage to constructor for verification
		super(maxElectricUsage, gasUsage, waterUsage, DEFAULT_TIME_ON);
		
		// If windModifier was not set
		if (windModifier == null) {
			// Set as random value [0 <= n <= 1]
			windModifier = MIN_WIND_MODIFIER + (Math.random() * (MAX_WIND_MODIFIER - MIN_WIND_MODIFIER));
		}
		else if (windModifier < MIN_WIND_MODIFIER || windModifier > MAX_WIND_MODIFIER) {
			Logger.error(String.format("Wind modifier must be between '%.2f' and '%.2f'", MIN_WIND_MODIFIER, MAX_WIND_MODIFIER));
		}
		
		// Assign subclass properties
		this.maxElectricUsage = maxElectricUsage;
		this.windModifier = windModifier;
		
		// Appliance is always on
		turnOn();
		
		// No extra tasks
	}
	
	/**
	 * Calculate a new wind modifier and electric usage based off the current wind modifier.
	 * {@inheritDoc}
	 */
	@Override
	public void timePasses() {
		// Get random value between MIN_SWAY and MAX_SWAY
		double sway = MIN_SWAY + (Math.random() * (MAX_SWAY - MIN_SWAY));
		// Get minimum value of windModifier, maximise to MIN_WIND_MODIFIER
		double min = Math.max(windModifier - sway, MIN_WIND_MODIFIER);
		// Get maximum value of windModifier, minimise to MAX_WIND_MODIFIER
		double max = Math.min(windModifier + sway, MAX_WIND_MODIFIER);
		
		// Get random value between min and max for new windModifier
		windModifier = min + (Math.random() * (max - min));
		// Update electric usage, cast will always round down
		super.electricUsage = (int) (windModifier * maxElectricUsage);	
		
		// Call timePasses as normal
		super.timePasses();
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