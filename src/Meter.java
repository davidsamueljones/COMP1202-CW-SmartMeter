/**
 * Abstract class that represents a Meter
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Meter {
	// Meter properties
	protected int consumed;
	protected int generated;
	protected boolean canGenerate;
	
	/**
	 * Constructor for Meter class
	 */
	public Meter(int consumed, int generated, boolean canGenerate) {
		// !!! Check values are sensible
		this.consumed = consumed;
		this.generated = generated;
		this.canGenerate = canGenerate;
	}

	/**
	 * Method to increment consumed value
	 * Overloaded to take different parameter options
	 */
	// Increment by 1
	public void incrementConsumed() {
		incrementConsumed(1);
	}

	// Increment by parameter
	public void incrementConsumed(int inc) {
		consumed += inc;
	}

	/**
	 * Method to increment generated value
	 * Overloaded to take different parameter options
	 */
	// Increment by 1
	public void incrementGenerated() {
		incrementGenerated(1);
	}
	
	// Increment by parameter
	public void incrementGenerated(int inc) {
		// generated cannot change when !canGenerate
		if (canGenerate) {
			generated += inc;
		}
	}

	/**
	 * Method to return canGenerate
	 */
	public boolean canGenerate() {
		return canGenerate;
	}
	
	/**
	* Abstract method definition for subclasses
	*/
	public abstract String getType();

}