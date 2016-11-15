import java.util.ArrayList;

/**
 * Abstract class that represents an Appliance
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
	 */
	protected Appliance(int electricityUse, int gasUse, int waterUse, int timeOn) {
		// !!! Catch when values are not sensible
		this.electricityUse = electricityUse;
		this.gasUse = gasUse;
		this.waterUse = waterUse;
		this.timeOn = timeOn;
		this.currentState = false;
	}

	/**
	* Method to start an Appliance
	*/    
	protected void use() {
		// If not already in use, reset and start
		if (!currentState) {
			currentState = true;
			currentTimeOn = 0;
		}
	}

	/**
	* Method to stop an Appliance
	*/
	protected void stop() {
		currentState = false;
		currentTimeOn = 0;
	}

	/**
	 * Method to connect a meter 
	 */
	public void connectMeter(Meter meter) {
		if (meter != null) {
			connMeters.add(meter);            
		}
	}

	/**
	* Method to simulate a unit time passing
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
	* Method to increment all connected meters by their
	* respective values per unit time
	*/
	private void incMeters() {
		incMeterType("electricity", electricityUse);
		incMeterType("gas", gasUse);
		incMeterType("water", waterUse);
	}
	
	/**
	* Method to increment each meter of specified type
	*/ 
	private void incMeterType(String meterType, int amount) {

		if (amount == 0) {
			return; // does not matter if meter is linked
		}
		// Find list of meters of type
		ArrayList<Meter> meters = getMetersOfType(meterType);
		
		try {
			// If ArrayList empty, meter not found
			if (meters.size() == 0) {
				throw new Exception("Meter type not connected: " + meterType);
			}
			// For meters of type, incrementConsumed
			for (Meter meter : meters) {
				meter.incrementConsumed(amount);
			}
		}
		catch (Exception e) {
			// Print exception to command line
			System.out.println(e.getMessage());
		}
	}
	
	/**
	* Method to return an ArrayList of matches for a type
	*/
	private ArrayList<Meter> getMetersOfType(String meterType) {
		// ArrayList to hold all matches
		ArrayList<Meter> meters = new ArrayList<Meter>();
		
		// Search for meter type
		for (Meter meter : connMeters) {
			if (meter.getType().equals(meterType)) {
				meters.add(meter);
			}
		}
		
		return meters; // list will be empty if not found
	}

}