/**
 * Class of Meter that represents a gas meter.
 * Assumed that meter can only consume.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class GasMeter extends Meter {

	/**
	 * Constructor for GasMeter class.
	 * Assign defaults.
	 */
	public GasMeter() {
		this(0);
	}

	/**
	 * Constructor for GasMeter class.
	 * Set consumed by parameter, default others.
	 * @param  consumed Initial count of consumed gas 
	 */
	public GasMeter(int consumed) {
		super(consumed, false, 0);
	}

	@Override
	public String getType() {
		return UtilityType.GAS.asString();
	}

}