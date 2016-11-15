/**
 * Class of Meter that represents a gas meter
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class GasMeter extends Meter {

	/**
	 * Constructor for GasMeter class
	 */
	public GasMeter(int consumed) {
		super(consumed, 0, false);
	}

	/**
	* Method override to return gas type
	*/
	public String getType() {
		return "gas";
	}

}