import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class representing a house
 * This house only allows a single instance of each Meter type
 * This house allows multiple instances of the same Appliance
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class House {
	int time; // units of 15 minutes (non-wrapping)

	// Array lists
	ArrayList<Meter> meters;
	ArrayList<Appliance> appliances;
	ArrayList<Person> people;

	/**
	 * Constructor for House class
	 * Default time to 0
	 */
	public House() {
		this(0);
	}

	/**
	 * Constructor for House class
	 * @param  time The current time in the house
	 */
	public House(int time) {

		if (time < 0) {
			Logger.error("House's time cannot be negative");
			throw new IllegalArgumentException();
		}
	
		this.time = time;
		
		// Initialise ArrayLists
		meters = new ArrayList<Meter>();
		appliances = new ArrayList<Appliance>();
		people = new ArrayList<Person>();
	}

	/**
	 * @return  Returns the value of time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Adds a Meter to the House
	 * @param  meter Meter to add
	 */
	public void addMeter(Meter meter) {
		// Check meter is not null
		if (meter == null) {
			Logger.warning("Meter not added to house - null meter");
		}
		else {
			// Check meter is not already attached, else add to array list
			String type = meter.getType();
			if (getMeterOfType(type) != null) {
				Logger.warning("Meter not added to house - meter type already exists");
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
			Logger.warning("Appliance not added to house - null appliance");      
		}
		// Check for exact appliance reference in appliances
		else if (appliances.contains(appliance)) {
			Logger.warning("Appliance not added to house - appliance instance exists in house"); 
		}
		// Check if number of appliances exceed max
		else if (numAppliances() >= 25) {
			Logger.warning("Appliance not added to house - maximum of 25 appliances");
		}
		// Appliance okay
		else {
			System.out.println(String.format("Appliance of type '%s' added to house", appliance.getType()));
			appliances.add(appliance);      
		}
	}

	/**
	 * Removes an Appliance from House by object
	 * @param  appliance Appliance to remove
	 */
	public void removeAppliance(Appliance appliance) {
		if (appliance == null) {
			Logger.warning("Appliance not removed from house - null appliance"); 
		}
		else {
			// Attempt to remove appliance, returns true if successful
			if (appliances.remove(appliance)) {
				System.out.println(String.format("Appliance '%s' removed from house", appliance.getType()));
			}
			else {
				Logger.warning("Appliance not removed from house - not found"); 
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
	 * @return  Returns the iterator for appliances
	 */
	public Iterator<Appliance> getAppliancesIterator() {
		return appliances.iterator();
	}
	
	/**
	 * Adds a Person to the House
	 * @param  person Person to add
	 */
	public void addPerson(Person person) {
		// Check Person is not null
		if (person == null) {
			Logger.warning("Person not added to house - null person");
		}
		// Check for exact person reference in people
		else if (people.contains(person)) {
			Logger.warning("Person not added to house - person instance exists in house");
		}
		else {
			// Add to array list
			System.out.println(String.format("Meter of type '%s' added to house", person.getName()));
			people.add(person);	
		}
	}
	
	/**
	 * Simulate a unit time passing in the house
	 */
	public void timePasses() {
		// Call each Appliance timePasses method
		for (Appliance appliance : appliances) {
			appliance.timePasses();
		}

		// Call each Person timePasses method
		for (Person person : people) {
			person.timePasses(time);
		}

		// Increment time of day
		time++;

		// Check if day has ended
		if (time % 96 == 0) {
			outputMeterReport();
		}

	}

	/**
	 * Create a report using the houses connected meters
	 * Report lists their consumed and generated values
	 * Prints report to command line after generation
	 * NOTE: This code is not memory efficent or fast but is flexible for future report changes
	 */
	private void outputMeterReport() {

		// Declare StringBuilder array of report line count
		StringBuilder[] reportLines = new StringBuilder[5];
		// Initialise StringBuilder array to hold the report lines
		for (int i = 0; i < reportLines.length; i++) {
			reportLines[i] = new StringBuilder(256); // By default assign 256 characters per line
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
