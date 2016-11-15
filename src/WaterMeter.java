/**
 * Class of Meter that represents a water meter
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class WaterMeter extends Meter {

	/**
	 * Constructor for WaterMeter class
	 */
	public WaterMeter(int consumed) {
		super(consumed, 0, false);
	}

	/**
	* Method override to return water type
	*/
	public String getType() {
		return "water";
	}

}