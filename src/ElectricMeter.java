/**
 * Class of Meter that represents an electric meter.
 * Assumed that meter can generate and consume.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ElectricMeter extends Meter {


	/**
	 * Constructor for ElectricMeter class.
	 * Assign defaults.
	 */
	public ElectricMeter() {
		this(0);
	}

	/**
	 * Constructor for ElectricMeter class.
	 * Set consumed by parameter, default others.
	 * @param  consumed Initial count of consumed electric
	 */
	public ElectricMeter(int consumed) {
		this(consumed, false);
	}

	/**
	 * Constructor for ElectricMeter class.
	 * Set consumed  and canGenerate by parameter, default others.
	 * @param  consumed Initial count of consumed electric
	 * @param  canGenerate Whether meter can generate electricity
	 */
	public ElectricMeter(int consumed, boolean canGenerate) {
		this(consumed, canGenerate, 0);
	}

	/**
	 * Constructor for ElectricMeter class.
	 * Set all by parameter.
	 * @param  consumed Initial count of consumed electric
	 * @param  canGenerate Whether meter can generate electricity
	 * @param  generated Initial count of generated electric
	 */
	public ElectricMeter(int consumed, boolean canGenerate, int generated) {
		super(consumed, canGenerate, generated);
	}

	@Override
	public String getType() {
		return UtilityType.ELECTRIC.asString();
	}

}