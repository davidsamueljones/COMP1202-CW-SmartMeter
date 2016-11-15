/**
 * Class of Meter that represents an electric meter
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ElectricMeter extends Meter {
	
	/**
	 * Constructor for ElectricMeter class
	 */
	public ElectricMeter(int consumed, int generated, boolean canGenerate) {
		super(consumed, generated, canGenerate);
	}

	/**
	* Method override to return electric type
	*/
	public String getType() {
		return "electric";
	}

}