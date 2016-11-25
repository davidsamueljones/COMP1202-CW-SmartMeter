import java.util.ArrayList;

/**
 * Abstract class that represents an Appliance
 * An assumption has been made that only a single meter of
 * each type can be connected. 
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Appliance {
	// Appliance properties
	private int electricityUse;
	private int gasUse;
	private int waterUse;
	private int timeOn; // time / 15 minutes

	// Ongoing properties
	private boolean currentState; // ON = true, OFF = false
	private int currentTimeOn; // time / 15 minutes

	// Connected meters
	private ArrayList<Meter> connMeters;

	/**
	 * Constructor for Appliance class
	 * @param  electricityUse Electric use per unit time [>= 0]
	 * @param  gasUse Gas use per unit time [>= 0]
	 * @param  waterUse Water use per unit time [>= 0]
	 * @param  timeOn  Duty cycle of appliance [-1 || > 0]
	 */
	protected Appliance(int electricityUse, int gasUse, int waterUse, int timeOn) {
		
		// Throw an exception when constructing if arguments are not sensible
		if (electricityUse < 0) {
			throw new IllegalArgumentException("[ERROR] Electricity usage must never be negative");
		}
		if (gasUse < 0) {
			throw new IllegalArgumentException("[ERROR] Gas use must never be negative");
		}
		if (waterUse < 0) {
			throw new IllegalArgumentException("[ERROR] Water use must never be negative");
		}
		if (timeOn != -1 && timeOn <= 0) {
			throw new IllegalArgumentException("[ERROR] Time on must be -1 or positive");
		}

		// Assign variables
		this.electricityUse = electricityUse;
		this.gasUse = gasUse;
		this.waterUse = waterUse;
		this.timeOn = timeOn;
		
		// Initialise ongoing variables
		this.currentState = false;
		this.currentTimeOn = 0;

		// Initialise meter ArrayList
		connMeters = new ArrayList<Meter>(); 
	}

	/**
	 * Start the Appliances duty cycle [dependent upon timeOn]
	 */    
	protected void use() {
		// If not already in use, reset and start
		if (!currentState) {
			stop(); // reset
			currentState = true;
		}
	}

	/**
	 * Simulates an appliance being turned off
	 */
	protected void stop() {
		currentState = false;
		currentTimeOn = 0;
	}

	/**
	 * Connects a Meter to the Appliance
	 * @param  meter Meter to connect
	 */
	public void connectMeter(Meter meter) {
		// Check meter is not null
		if (meter == null) {
			System.err.println("[WARNING] Meter not connected to appliance - null meter");
		}
		else {
			// Check meter is not already attached, else add to array list
			String type = meter.getType();
			if (getMeterOfType(type) != null) {
				System.err.println("[WARNING] Meter not connected to appliance - meter type is already connected");
			}
			else {
				System.out.println(String.format("Meter of type '%s' connected to appliance", type));
				connMeters.add(meter);	
			}
		}
	}

	/**
	 * Simulate a unit time passing
	 */
	public void timePasses() {
		
		// If appliance is still on
		if (currentState) {
			incMeters();
			currentTimeOn++;   
		}

		// Check if appliance should be turned off
		if (currentTimeOn == timeOn) {
			stop();
			System.out.println(String.format("'%s' duty cycle has completed and has turned off automatically...", getType()));
		}

	}

	/**
	 * Increment all connected meters by their
	 * respective values per unit time
	 */
	private void incMeters() {
		incMeterType("Electric", electricityUse);
		incMeterType("Gas", gasUse);
		incMeterType("Water", waterUse);
	}
	
	/**
	 * Increment meter of specified type
	 * @param  meterType Type of meter to search for
	 * @param  amount How much to increment meters by
	 */ 
	private void incMeterType(String meterType, int amount) {
		
		// Check if meter requires incrementing
		if (amount == 0) {
			return; 
		}

		// Get the meter from meters
		Meter meterOfType = getMeterOfType(meterType);
	
		// If null was returned, meter not found
		if (meterOfType == null) {
			System.err.println(String.format("[WARNING] Attempted meter increment but meter of type '%s' not connected", meterType));
		}
		else {
			// For meter of type, incrementConsumed
			meterOfType.incrementConsumed(amount);			
		}

	}
	
	/**
	 * Returns the Meter that matches a type
	 * @param   meterType Type of meter to search for
	 * @return  matched Meter or null if not found
	 */
	private Meter getMeterOfType(String meterType) {
		
		// Search for meter type
		for (Meter meter : connMeters) {
			if (meter.getType().equals(meterType)) {
				return meter;
			}
		}
		
		return null; // meter not found
	}

	/**
	 * Return the type of Appliance as a string
	 * @return  Appliance type as string
	 */
	public abstract String getType();

}