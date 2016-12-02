/**
 * Abstract class that represents a Meter.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Meter {
	// Meter properties
	private int consumed;
	private int generated;
	private boolean canGenerate;
	
	/**
	 * Constructor for Meter class.
	 * @param  consumed Amount already consumed in meter
	 * @param  canGenerate Whether the meter can generate 
	 * @param  generated Amount already generated in meter
	 */
	protected Meter(int consumed, boolean canGenerate, int generated) {

		// Throw an exception when constructing if arguments are not sensible
		if (consumed < 0) {
			Logger.error("Initial consumed value must be zero or positive");
		}
		if (!canGenerate && generated != 0) {
			Logger.error("Initial generated value must be zero if cannot generate");
		}
		if (generated < 0) {
			Logger.error("Initial generated value must be zero or positive");
		}

		// Assign variables
		this.consumed = consumed;
		this.generated = generated;
		this.canGenerate = canGenerate;
	}

	/**
	 * Increment consumed value.
	 * Use default increment of 1.
	 */
	public void incrementConsumed() {
		incrementConsumed(1);
	}

	/**
	 * Increment consumed value.
	 * Use parameter increment.
	 * @param  inc Increment to use
	 */
	public void incrementConsumed(int inc) {
		consumed += inc;
	}

	/**
	 * Returns value of consumed.
	 * @return  Value of consumed
	 */
	public int getConsumed() {
		return consumed;
	}

	/**
	 * Increment generated value.
	 * Use default increment of 1.
	 */
	public void incrementGenerated() {
		incrementGenerated(1);
	}
	
	/**
	 * Increment generated value.
	 * Use parameter increment.
	 * @param  inc Increment to use
	 */
	public void incrementGenerated(int inc) {
		// generated cannot change when !canGenerate
		if (canGenerate) {
			generated += inc;
		}
	}

	/**
	 * @return  Value of generated
	 */
	public int getGenerated() {
		return generated;
	}


	/**
	 * @return  State of canGenerate
	 */
	public boolean canGenerate() {
		return canGenerate;
	}
	
	/**
	 * @return  Meter type as string
	 */
	public abstract String getType();

}