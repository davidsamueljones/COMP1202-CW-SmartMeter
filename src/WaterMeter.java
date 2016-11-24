/**
 * Class of Meter that represents a water meter
 * Assumed that meter can only consume
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class WaterMeter extends Meter {

	/**
	 * Constructor for WaterMeter class
	 * Assign defaults
	 */
	public WaterMeter() {
		this(0);
	}

	/**
	 * Constructor for WaterMeter class
	 * Set consumed by parameter, default others
	 * @param  consumed Initial count of consumed water 
	 */
	public WaterMeter(int consumed) {
		super(consumed, false, 0);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String getType() {
		return "water";
	}

}