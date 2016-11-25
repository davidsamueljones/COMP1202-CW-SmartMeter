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
	int timeOfDay; // units of 15 minutes

	ArrayList<Meter> meters;
	ArrayList<Appliance> appliances;

	/**
	 * Constructor for House class
	 */
	protected House() {
		timeOfDay = 0;

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
	
	/**
	 * Adds an Appliance to the House
	 * @param  appliance Appliance to add
	 */
	public void addAppliance(Appliance appliance) {
		// Add if appliance is not null
		if (appliance == null) {
			System.err.println("[WARNING] Appliance not added to house - null appliance");      
		}
		else {
			// Check for exact appliance reference in appliances
			if (appliances.contains(appliance)) {
				System.err.println("[WARNING] Appliance not added to house - appliance instance exists in house"); 
			}
			// Check if number of appliances exceed max
			else if (numAppliances() >= 25) {
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
		if (appliance == null) {
			System.err.println("[WARNING] Appliance not removed from house - null appliance"); 
		}
		else {
			// Attempt to remove appliance, returns true if successful
			if (appliances.remove(appliance)) {
				System.out.println(String.format("Appliance '%s' removed from house", appliance.getType()));
			}
			else {
				System.err.println("[WARNING] Appliance not removed from house - not found"); 
			}
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
		// Call each Appliance timePasses method
		for (Appliance appliance : appliances) {
			appliance.timePasses();
		}

		// !!! Call each Person timePasses method
		
		// Increment time of day
		timeOfDay++;

		// Check if day has ended
		if (timeOfDay == 96) {
			outputMeterReport();
			timeOfDay = 0; // wrap to begining of next day
		}

	}

	/**
	 * Create a report using the houses connected meters
	 * Report lists their consumed and generated values
	 * Prints report to command line after generation
	 * NOTE: This code is not efficent but is flexible for future reporting changes
	 */
	private void outputMeterReport() {

		// Declare StringBuilder array of report line count
		StringBuilder[] reportLines = new StringBuilder[5];
		// Initialise StringBuilder array to hold the report lines
		for (int i = 0; i < reportLines.length; i++) {
			reportLines[i] = new StringBuilder(1000); // By default assign 1000 characters
		}

		// Define spacing of columns
		String columnFormat = "%12s ";

		// Create row headings
		// reportLines[0] is wrapper
		reportLines[1].append(String.format(columnFormat, "[DAY REPORT]"));
		reportLines[2].append(String.format(columnFormat, "{Consumed}"));
		reportLines[3].append(String.format(columnFormat, "{Generated}"));
		// reportLines[n-1] is wrapper
		
		// Append information to columns
		for (Meter meter : meters) {
			reportLines[1].append(String.format(columnFormat, meter.getType()));
			reportLines[2].append(String.format(columnFormat, meter.getConsumed()));
			reportLines[3].append(String.format(columnFormat, meter.getGenerated()));
		}
		
		// Determine width of report (length of header - 1)
		int width = reportLines[1].length() - 1;

		// Create a string for the report wrapper
		for(int i = 0; i < width; i++){
			reportLines[0].append('*');
		}   
		// Copy the report wrapper to the last line
		reportLines[reportLines.length - 1].append(reportLines[0].toString());

		// Print lines of StringBuilder array
		for(int i = 0; i < reportLines.length; i++) {
			System.out.println(reportLines[i].toString());
		}

	}

}
