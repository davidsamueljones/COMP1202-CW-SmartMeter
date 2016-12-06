import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class representing a house.
 * This house only allows a single instance of each Meter type.
 * This house allows multiple instances of the same Appliance.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class House {
	/**
	 * Constant day length
	 */
	public final static int DAY_LENGTH = 96;
	
	// House properties
	private String name;
	private int time; // units of 15 minutes (non-wrapping)

	// ArrayLists holding House's connected objects
	ArrayList<Meter> meters = new ArrayList<Meter>();
	ArrayList<Appliance> appliances = new ArrayList<Appliance>();
	ArrayList<Person> people = new ArrayList<Person>();

	/**
	 * Constructor for House class.
	 * Default time and name.
	 */
	public House() {
		this("House", 0);
	}
	
	/**
	 * Constructor for House class.
	 * Default time.
	 * @param  name Name for house
	 */
	public House(String name) {
		this(name, 0);
	}
	
	/**
	 * Constructor for House class.
	 * Default name.
	 * @param  time The current time in the house
	 */
	public House(int time) {
		this("House", time);
	}
	
	/**
	 * Constructor for House class.
	 * @param  name Name for house
	 * @param  time The current time in the house
	 */
	public House(String name, int time) {
		// Check arguments are sensible
		if (name == null) {
			Logger.error("House's name cannot be null");
		}
		if (time < 0) {
			Logger.error("House's time cannot be negative");
		}
	
		// Assign properties
		this.name = name;
		this.time = time;
		
		Logger.message(String.format("House '%s' started at time '%d'", name, time));
	}

	/**
	 * @return  Returns the value of name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return  Returns the value of time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Adds a Meter to the House.
	 * @param  meter Meter to add
	 */
	public void addMeter(Meter meter) {
		// Check meter is not null
		if (meter == null) {
			Logger.warning(String.format("Meter not connected to '%s' - null meter", name));
		}
		else {
			// Check meter is not already attached, else add to array list
			String meterType = meter.getType();
			if (getMeterOfType(meterType) != null) {
				Logger.warning(String.format("Meter '%s' not connected to '%s' - type is already connected", meterType, name));
			}
			else {
				Logger.message(String.format("Meter '%s' connected to '%s'", meterType, name));
				meters.add(meter);
				updateAllApplianceMeters();
			}
		}
	}

	
	/**
	 * Removes a Meter from House by object.
	 * @param  meter Meter to remove
	 */
	public void removeMeter(Meter meter) {
		if (meter == null) {
			Logger.warning(String.format("Meter not removed from '%s' - null meter", name)); 
		}
		else {
			// Attempt to remove meter, true if successful
			if (meters.remove(meter)) {
				Logger.message(String.format("Meter '%s' removed from '%s", meter.getType(), name));
				updateAllApplianceMeters();
			}
			else {
				Logger.warning(String.format("Meter '%s' not removed from '%s' - not found", meter.getType(), name)); 
			}
		}
	}

	/**
	 * Returns the Meter that matches a type.
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
	 * Adds an Appliance to the House.
	 * @param  appliance Appliance to add
	 */
	public void addAppliance(Appliance appliance) {
		// Add if appliance is not null
		if (appliance == null) {
			Logger.warning(String.format("Appliance not added to '%s' - null appliance", name));      
		}
		// Check for exact appliance reference in appliances
		else if (appliances.contains(appliance)) {
			Logger.warning(String.format("Appliance '%s' not added to '%s' - appliance instance exists in house", name)); 
		}
		// Check if number of appliances exceed max
		else if (numAppliances() >= 25) {
			Logger.warning(String.format("Appliance '%s' not added to '%s' - maximum of 25 appliances", name));
		}
		// Appliance okay
		else {
			Logger.message(String.format("Appliance '%s' added to '%s'", appliance.getType(), name));
			appliances.add(appliance);
			updateApplianceMeters(appliance);
		}
	}

	/**
	 * Removes an Appliance from House by object.
	 * @param  appliance Appliance to remove
	 */
	public void removeAppliance(Appliance appliance) {
		if (appliance == null) {
			Logger.warning(String.format("Appliance not removed from '%s' - null appliance", name)); 
		}
		else {
			// Attempt to remove appliance, returns true if successful
			if (appliances.remove(appliance)) {
				Logger.message(String.format("Appliance '%s' removed from '%s", appliance.getType(), name));
			}
			else {
				Logger.warning(String.format("Appliance '%s' not removed from '%s' - not found", appliance.getType(), name)); 
			}
		}
	}
	
	/**
	 * Runs updateApplianceMeters() for all connected appliances 
	 */
	private void updateAllApplianceMeters() {
		for (Appliance appliance : appliances) {
			updateApplianceMeters(appliance);
		}
	}
	
	/**
	 * Adds all Meters connected to the House to the given Appliance,
	 * replacing if necessary.
	 * @param  appliance Appliance to add meters to
	 */
	private void updateApplianceMeters(Appliance appliance) {
		for (Meter meter : meters) {
			// If appliance uses meter type, add it
			if (appliance.getUsageFromType(meter.getType()) != 0) {
				// Force replace if required
				appliance.addMeter(meter, true); 
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
			Logger.message(String.format("Person '%s' added to '%s'", person.getName(), name));
			people.add(person);	
		}
	}
	
	/**
	 * @param  person Person to find
	 * @return  Returns true if instance of person is in house
	 */
	public boolean isPersonInHouse(Person person) {
		return people.contains(person);
	}
	
	/**
	 * Run simulation of time passing for current house
	 */
	public void go() {
		while (true) {	
			timePasses();
			// Sleep between unit time increments to slow down simulation 
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Logger.error("Thread sleep failed");
			}
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
			person.timePasses(this);
		}

		// Increment time of day
		time++;

		// Check if day has ended
		if (time % DAY_LENGTH == 0) {
			outputMeterReport();
		}

	}

	/**
	 * Create a report using the houses connected meters
	 * Report lists their consumed and generated values
	 * Prints report to command line after generation
	 * NOTE: This code is not memory efficient or fast but is flexible for future report changes
	 */
	private void outputMeterReport() {

		// Declare StringBuilder array of report line count
		StringBuilder[] reportLines = new StringBuilder[6];
		// Initialise StringBuilder array to hold the report lines
		for (int i = 0; i < reportLines.length; i++) {
			reportLines[i] = new StringBuilder(256); // By default assign 256 characters per line
		}

		// Define spacing of columns
		String columnFormat = "%12s ";

		// Create row headings
		// reportLines[0] is wrapper
		reportLines[1].append(String.format("%s - Day %d", name, (time % 96) + 1));
		reportLines[2].append(String.format(columnFormat, "---"));
		reportLines[3].append(String.format(columnFormat, "{Consumed}"));
		reportLines[4].append(String.format(columnFormat, "{Generated}"));
		// reportLines[n-1] is wrapper
		
		// Append information to columns
		for (Meter meter : meters) {
			reportLines[2].append(String.format(columnFormat, meter.getType()));
			reportLines[3].append(String.format(columnFormat, meter.getConsumed()));
			reportLines[4].append(String.format(columnFormat, meter.getGenerated()));
		}
		
		// Determine width of report (length of standard row - 1)
		int width = reportLines[2].length() - 1;

		// Create a string for the report wrapper
		for (int i = 0; i < width; i++){
			reportLines[0].append('*');
		}   
		// Copy the report wrapper to the last line
		reportLines[reportLines.length - 1].append(reportLines[0].toString());

		// Print lines of StringBuilder array
		for (int i = 0; i < reportLines.length; i++) {
			Logger.message(reportLines[i].toString());
		}

	}

}
