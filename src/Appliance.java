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
	 * @param  timeOn  Duty cycle of Appliance [-1 || > 0]
	 */
	protected Appliance(int electricityUse, int gasUse, int waterUse, int timeOn) {
		
		// Throw an exception when constructing if arguments are not sensible
		if (electricityUse < 0) {
			throw new IllegalArgumentException("[ERROR] Electricity use must be zero or positive");
		}
		if (gasUse < 0) {
			throw new IllegalArgumentException("[ERROR] Gas use must be zero or positive");
		}
		if (waterUse < 0) {
			throw new IllegalArgumentException("[ERROR] Water use must be zero or positive");
		}
		if (timeOn != -1 && timeOn <= 0) {
			throw new IllegalArgumentException("[ERROR] Time on must be -1 or positive");
		}

		// Assign variables
		this.electricityUse = electricityUse;
		this.gasUse = gasUse;
		this.waterUse = waterUse;
		this.timeOn = timeOn;
		
		// Initalise ongoing variables
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
			currentState = true;
			currentTimeOn = 0;
		}
	}

	/**
	* Simulates an appliance being turned off
	*/
	protected void stop() {
		currentState = false;
	}

	/**
	 * Method to connect a Meter to the Appliance
	 * @param  meter Meter to connect
	 */
	public void connectMeter(Meter meter) {
		// Add if meter is not null and not already attached
		if (meter != null && getMetersOfType(meter) != null) {
			connMeters.add(meter);            
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
			currentState = false;
		}

	}

	/**
	* Increment all connected meters by their
	* respective values per unit time
	*/
	private void incMeters() {
		incMeterType("electricity", electricityUse);
		incMeterType("gas", gasUse);
		incMeterType("water", waterUse);
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
		Meter meterOfType = getMetersOfType(meterType);
	
		// If null was returned, meter not found
		if (meterOfType == null) {
			System.err.println("[WARNING] Meter type not connected: " + meterType);
		}
		
		// For meters of type, incrementConsumed
		meterOfType.incrementConsumed(amount);

	}
	
	/**
	* Returns the Meter that matches a type
	* @param   meterType Type of meter to search for
	* @return  matched Meter or null if not found
	*/
	private Meter getMetersOfType(String meterType) {
		
		// Search for meter type
		for (Meter meter : connMeters) {
			if (meter.getType().equals(meterType)) {
				return meter;
			}
		}
		
		return null; // meter not found
	}

}