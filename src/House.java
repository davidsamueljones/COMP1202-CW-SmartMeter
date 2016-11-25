import java.util.ArrayList;

/**
 * Class representing a house
 * This house only allows a single instance of each Meter type
 * This house allows multiple instances of the same Appliance
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class House {
	ArrayList<Meter> meters;
	ArrayList<Appliance> appliances;

	/**
	 * Constructor for House class
	 */
	protected House() {
		// Initialise meter and appliance ArrayLists
		meters = new ArrayList<Meter>();
		appliances = new ArrayList<Appliance>();
	}

	/**
	 * Adds a Meter to the House
	 * @param  meter Meter to add
	 */
	public void addMeter(Meter meter) {
		// Check meter is not null
		if (meter == null) {
			System.err.println("[WARNING] Meter not added to house - null meter");
		}
		else {
			// Check meter is not already attached, else add to array list
			String type = meter.getType();
			if (getMeterOfType(type) != null) {
				System.err.println("[WARNING] Meter not added to house - meter type already exists");
			}
			else {
				System.out.println(String.format("Meter of type '%s' added to house", type));
				meters.add(meter);	
			}
		}
	}

	/**
	 * Adds an Appliance to the House
	 * @param  appliance Appliance to add
	 */
	public void addAppliance(Appliance appliance) {
		// Add if appliance is not null
		if (appliance == null) {
			System.err.println("[WARNING] Appliance not added to house - null meter");      
		}
		else {
			// Only allow 25 appliances max
			if (numAppliances() >= 25) {
				System.err.println("[WARNING] Appliance not added to house - maximum of 25 appliances");
			}
			else {
				System.out.println(String.format("Appliance of type '%s' added to house", appliance.getType()));
				appliances.add(appliance);      
			}
		}
	}

	/**
	 * Removes an Appliance from House by object
	 * @param  appliance Appliance to remove
	 */
	public void removeAppliance(Appliance appliance) {
		if (appliance != null) {
			appliances.remove(appliance);            
		}
	}

	/**
	 * Returns the number of Appliance objects in appliances
	 * @return  Number of appliances in the house
	 */
	public int numAppliances() {
		return appliances.size();
	}

	/**
	 * Simulate a unit time passing in the house
	 */
	public void timePasses() {
		// Run each Appliance timePasses method
		for (Appliance appliance : appliances) {
			appliance.timePasses();
		}
	}

	/**
	 * Returns the Meter that matches a type
	 * @param   meterType Type of meter to search for
	 * @return  matched Meter or null if not found
	 */
	private Meter getMeterOfType(String meterType) {
		
		// Search for meter type
		for (Meter meter : meters) {
			if (meter.getType().equals(meterType)) {
				return meter;
			}
		}
		
		return null; // meter not found
	}

}
