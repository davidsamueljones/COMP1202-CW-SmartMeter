/**
 * Abstract class that represents a Meter
 * 
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
	public Meter(int consumed, boolean canGenerate, int generated) {

		// Throw an exception when constructing if arguments are not sensible
		if (consumed < 0) {
			throw new IllegalArgumentException("[ERROR] Consumed value must be zero or positive");
		}
		if (!canGenerate && generated != 0) {
			throw new IllegalArgumentException("[ERROR] Generated value must be zero if cannot generate");
		}
		if (generated < 0) {
			throw new IllegalArgumentException("[ERROR] Generated value must be zero or positive");
		}

		// Assign variables
		this.consumed = consumed;
		this.generated = generated;
		this.canGenerate = canGenerate;
	}

	/**
	 * Increment consumed value
	 * Use default increment of 1
	 */
	public void incrementConsumed() {
		incrementConsumed(1);
	}

	/**
	 * Increment consumed value
	 * Use parameter increment
	 * @param  inc Increment to use
	 */
	public void incrementConsumed(int inc) {
		consumed += inc;
	}

	/**
	 * Returns value of consumed
	 * @return  Value of consumed
	 */
	public int getConsumed() {
		return consumed;
	}

	/**
	 * Increment generated value
	 * Use default increment of 1
	 */
	public void incrementGenerated() {
		incrementGenerated(1);
	}
	
	/**
	 * Increment generated value
	 * Use parameter increment
	 * @param  inc Increment to use
	 */
	public void incrementGenerated(int inc) {
		// generated cannot change when !canGenerate
		if (canGenerate) {
			generated += inc;
		}
	}

	/**
	 * Returns value of generated
	 * @return  Value of generated
	 */
	public int getGenerated() {
		return generated;
	}


	/**
	 * Returns the state of canGenerate
	 * @return  State of canGenerate
	 */
	public boolean canGenerate() {
		return canGenerate;
	}
	
	/**
	 * Return the type of meter as a string
	 * @return  Meter type as string
	 */
	public abstract String getType();

}