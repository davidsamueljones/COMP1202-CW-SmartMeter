import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Abstract class that represents an Appliance.
 * An assumption has been made that only a single meter of
 * each type can be connected. 
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Appliance {
	// Appliance properties
	private int electricUsage;
	private int gasUsage;
	private int waterUsage;
	private int timeOn; // time / 15 minutes

	// Ongoing properties
	private boolean currentState = false; // ON = true, OFF = false
	private int currentTimeOn = 0; // time / 15 minutes

	// Connected meters
	private ArrayList<Meter> meters = new ArrayList<Meter>();
	// Tasks that can be run by Appliance
	private ArrayList<ApplianceTask> tasks = new ArrayList<ApplianceTask>();
	
	/**
	 * Constructor for Appliance class.
	 * @param  electricUsage Electric use per unit time
	 * @param  gasUsage Gas use per unit time
	 * @param  waterUsage Water use per unit time
	 * @param  timeOn  Duty cycle of appliance [-1 || > 0]
	 */
	protected Appliance(int electricUsage, int gasUsage, int waterUsage, int timeOn) {
		// Throw an exception when constructing if arguments are not sensible
		if (timeOn != -1 && timeOn <= 0) {
			Logger.error("Time on must be -1 or positive");
		}

		// Assign variables
		this.electricUsage = electricUsage;
		this.gasUsage = gasUsage;
		this.waterUsage = waterUsage;
		this.timeOn = timeOn;

		// Check if arguments are sensible for Appliance type
		checkUsageAllowed(getAllowedConsumption(), false);
		checkUsageAllowed(getAllowedGeneration(), true);
	}
	
	/**
	 * @return  Value of currentState
	 */
	public boolean getCurrentState() {
		return currentState;
	}
	
	/**
	 * Get appliance usage for a given type.
	 * @param  type Enumerated representation of type
	 * @return  The usage respective to the type
	 */
	public int getUsageFromType(String type) {
		try {
			return getUsageFromType(UtilityType.valueOf(type.toUpperCase()));
		}
		catch (Exception e){
			return 0;
		}
	}
	
	/**
	 * Get appliance usage for a given type.
	 * @param  type Enumerated representation of type
	 * @return  The usage respective to the type
	 */
	public int getUsageFromType(UtilityType type) {
		switch (type) {
			case ELECTRIC:
				return electricUsage;
			case GAS:
				return gasUsage;
			case WATER:
				return waterUsage;
			default:
				return 0;
		}	
	}
	
	/**
	 * Checks if appliance usage values are valid against a UtilityType allowed list 
	 * and all types of UtilityType.
	 * Throw an exception if consumption usage type banned and is greater than zero.
	 * Throw an exception if generation usage type banned and is less than negative.
	 * @param  allowedList Array of utility types to indicate allowed usage
	 * @param  generation Boolean to indicate if checking generation (otherwise generation)
	 */
	protected void checkUsageAllowed(UtilityType[] allowedList, boolean generation) {
		// Loop for all values in UtilityTypes
		for (UtilityType type : UtilityType.values()) {		
			boolean allowed = false;
			
			// Check if type is in allowed list
			for (UtilityType allowedType : allowedList) {
				if (type.equals(allowedType)) {
					allowed = true;
				}
			}
			
			// If type is not in allowed list
			if (!allowed) {
				// If checking consumption values may not be greater than 0 when banned
				if (!generation && getUsageFromType(type) > 0) {
					Logger.error(String.format("Appliance '%s' cannot use '%s' for consumption", getType(), type.asString()));			
				}
				// If checking generation values may not be lower than 0 when banned
				else if (generation && getUsageFromType(type) < 0) {
					Logger.error(String.format("Appliance '%s' cannot use '%s' for generation", getType(), type.asString()));			
				}
			}
		}
	}
	
	/**
	 * Adds a Meter to the Appliance.
	 * Defaulting replaceIfExists to false.
	 * @param  meter Meter to connect
	 */
	public void addMeter(Meter meter) {
		addMeter(meter, false);
	}
	
	/**
	 * Adds a Meter to the Appliance.
	 * @param  meter Meter to connect
	 * @param  replaceIfExists If existing meter of type, remove it and add provided
	 */
	public void addMeter(Meter meter, boolean replaceIfExists) {
		String applianceType = getType();
		// Check meter is not null
		if (meter == null) {
			Logger.warning(String.format("Meter not connected to appliance '%s' - null meter", applianceType));
		}
		else {
			String meterType = meter.getType();
			Meter existingMeter = getMeterOfType(meterType);
			
			// Check meter is not already attached, else add to array list
			if (meters.contains(meter)) {
				return;
			}
			// Meter exists and is not being replaced
			else if (existingMeter != null && !replaceIfExists) {
				Logger.warning(String.format("Meter '%s'  not connected to appliance '%s' - type is already connected", meterType, applianceType));
			}
			// Meter does not exist or is being replaced
			else {
				// Remove existing meter if it exists
				if (existingMeter != null) {
					removeMeter(existingMeter);
				}
				Logger.message(String.format("Meter '%s' connected to appliance '%s'", meterType, applianceType));
				meters.add(meter);	
			}
		}
	}

	/**
	 * Removes a Meter from House by object.
	 * @param  meter Meter to remove
	 */
	public void removeMeter(Meter meter) {
		String applianceType = getType();
		if (meter == null) {
			Logger.warning(String.format("Meter not removed from appliance '%s' - null meter", applianceType)); 
		}
		else {
			// Attempt to remove meter, true if successful
			if (meters.remove(meter)) {
				Logger.message(String.format("Meter '%s' removed from appliance '%s", meter.getType(), applianceType));
			}
			else {
				Logger.warning(String.format("Meter '%s' not removed from appliance '%s' - not found", meter.getType(), applianceType)); 
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
	 * Simulate a unit time passing.
	 */
	public void timePasses() {
		
		// If appliance is still on
		if (currentState) {
			incMeters();
			currentTimeOn++;   
		}

		// Check if appliance should be turned off
		if (currentTimeOn == timeOn) {
			turnOff();
			Logger.message(String.format("'%s' duty cycle has completed and has turned off automatically", getType()));
		}

	}

	/**
	 * Attempt to increment all utility meter typs by their
	 * respective usage values per unit time.
	 */
	private void incMeters() {
		// Loop through all possible utility types
		for (UtilityType type : UtilityType.values()) {
			incMeterType(type);
		}
	}
	
	/**
	 * Increment meter of specified type.
	 * @param  meterType Type of meter to search for
	 * @param  amount How much to increment meters by
	 */ 
	private void incMeterType(UtilityType meterType) {
		// Determine amount to increment by
		int amount = getUsageFromType(meterType);
				
		// Check if meter requires incrementing
		if (amount == 0) {
			return; 
		}

		// Get the meter from meters
		Meter meterOfType = getMeterOfType(meterType.asString());
	
		// If null was returned, meter not found
		if (meterOfType == null) {
			Logger.warning(String.format("Attempted meter increment but meter of type '%s' not connected", meterType.asString()));
		}
		else {
			// Check if amount should increment consumed or generated
			// [Negative values imply generated]
			if (amount > 0) {
				// For meter of type, incrementConsumed
				meterOfType.incrementConsumed(amount);
			}
			else {
				// Check if meter has the generation capability
				if (meterOfType.canGenerate()) {
					// For meter of type, incrementGenerated
					meterOfType.incrementGenerated(-amount);
				}
				else {
					Logger.warning(String.format("Attempted meter generation increment but meter of type '%s' does not support generation", meterType.asString()));
				}
			}
		}

	}

	/**
	 * Start the Appliances duty cycle [dependent upon timeOn].
	 * Manual calling allowed but should be targeted using a task.
	 */    
	public void turnOn() {
		// If not already in use, reset and start
		if (!currentState) {
			turnOff(); // reset
			currentState = true;
		}
	}

	/**
	 * Simulates an appliance being turned off.
	 * Manual calling allowed but should be targeted using a task.
	 */
	public void turnOff() {
		currentState = false;
		currentTimeOn = 0;
	}
	
	/**
	 * Adds an ApplianceTask to the Appliance task list.
	 * Verifies that the task is valid before adding.
	 * @param  task ApplianceTask to add
	 */
	protected void addTask(ApplianceTask task) {
		// Check if task name already exists in task list (also covers exact instance search)
		if (getTaskFromName(task.getName()) != null) {
			Logger.error(String.format("Task name '%s' already exists", task.getName()));			
		}
		
		// ApplianceTask is unique so add to task list 
		tasks.add(task);
	}
	
	/**
	 * Gets an ApplianceTask from the Appliance task list using taskName.
	 * @param  taskName Name of task to get
	 * @return  ApplianceTask matching taskName, else null
	 */
	public ApplianceTask getTaskFromName(String taskName) {
		for (ApplianceTask task : tasks) {
			if (task.getName().equals(taskName)) {
				return task;
			}
		}
		return null;
	}
	
	/**
	 * Does an ApplianceTask from the Appliance task list using an ApplianceTask.
	 * The exact instance of the ApplianceTask must be used for security purposes.
	 * @param  task ApplianceTask to run
	 * @return  True or false respective to if the task ran successfully
	 */
	public boolean doTask(ApplianceTask task) {
		// Check if exact task object is member of appliance
		if (tasks.contains(task)) {
			// Get method as defined by task
			Method taskMethod = task.getMethod();

			if (taskMethod != null) {
				try {
					// Attempt to run method
					taskMethod.invoke(this);
					return true; // task ran successfully
				} 
				catch (Exception e) {
					Logger.warning("Task failed when invoked on Appliance");
				}
			}
		}
		else {
			Logger.warning("Task not a member of Appliances task list");
		}
		return false; // task did not run successfully
	}
	
	/**
	 * Get the Method object for a given name.
	 * @param  methodName Name of method to get
	 * @return  Method object with name of methodName, if not found return null
	 */
	protected Method getMethod(String methodName) {
		try {
			// Get the class of the current instance and get the method of methodName in the class
			return this.getClass().getMethod(methodName);
		} catch (NoSuchMethodException e) {
			// Method not found
			return null;
		}
	}
	
	/**	
	 * Return the type of Appliance as a string.
	 * @return  Appliance type as string
	 */
	public abstract String getType();

	/**	
	 * Get the list of consumption types allowed by type of Appliance.
	 * If this is not overridden, no generation is allowed.
	 * @return  All allowed UtilityTypes in an array
	 */
	protected UtilityType[] getAllowedConsumption() {
		return new UtilityType[] {};
	}
	
	/**	
	 * Get the list of generation types allowed by type of Appliance.
	 * If this is not overridden, no generation is allowed.
	 * @return  All allowed UtilityTypes in an array
	 */
	protected UtilityType[] getAllowedGeneration() {
		return new UtilityType[] {};
	}

}