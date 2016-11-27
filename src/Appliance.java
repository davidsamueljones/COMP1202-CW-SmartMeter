import java.lang.reflect.Method;
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
	// Tasks that can be run by Appliance
	private ArrayList<ApplianceTask> tasks;

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
			Logger.error("Electricity usage must never be negative");
			throw new IllegalArgumentException();
		}
		if (gasUse < 0) {
			Logger.error("Gas use must never be negative");
			throw new IllegalArgumentException();
		}
		if (waterUse < 0) {
			Logger.error("Water use must never be negative");
			throw new IllegalArgumentException();
		}
		if (timeOn != -1 && timeOn <= 0) {
			Logger.error("Time on must be -1 or positive");
			throw new IllegalArgumentException();
		}

		// Assign variables
		this.electricityUse = electricityUse;
		this.gasUse = gasUse;
		this.waterUse = waterUse;
		this.timeOn = timeOn;

		// Initialise ongoing variables
		this.currentState = false;
		this.currentTimeOn = 0;

		// Initialise ArrayLists
		connMeters = new ArrayList<Meter>(); 
		tasks = new ArrayList<ApplianceTask>();
	}
	
	/**
	 * Checks if appliance usages are valid against banned list
	 * Throw an exception if usage banned but usage is non zero
	 * @param  notElectricity True if electricty must be == 0
	 * @param  notGas True if gas must be == 0
	 * @param  notWater True if water must be == 0
	 */
	protected void verifyUsage(boolean notElectricity, boolean notGas, boolean notWater) {
		// Check if arguments are sensible for Appliance type
		if (notElectricity && electricityUse != 0) {
			Logger.error("This appliance cannot use water");
			throw new IllegalArgumentException();
		}
		if (notGas && gasUse != 0) {
			Logger.error("This appliance cannot use gas");
			throw new IllegalArgumentException();
		}
		if (notWater & waterUse != 0) {
			Logger.error("This appliance cannot use water");
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @return  Value of currentState
	 */
	public boolean getCurrentState() {
		return currentState;
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
			Logger.warning("Meter not connected to appliance - null meter");
		}
		else {
			// Check meter is not already attached, else add to array list
			String type = meter.getType();
			if (getMeterOfType(type) != null) {
				Logger.warning("Meter not connected to appliance - meter type is already connected");
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
			Logger.warning(String.format("Attempted meter increment but meter of type '%s' not connected", meterType));
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
	 * Adds a ApplianceTask to the Appliance task list
	 * Verifies that the task is valid before adding
	 * @param  task ApplianceTask to add
	 */
	protected void addTask(ApplianceTask task) {
		// Check if task name already exists in task list (also covers exact instance search)
		if (getTaskFromName(task.getTaskName()) != null) {
			Logger.error(String.format("Task name '%s' already exists", task.getTaskName()));
			throw new IllegalArgumentException();			
		}
		
		// ApplianceTask is unique so add to task list 
		tasks.add(task);
	}
	
	/**
	 * Gets a ApplianceTask from the Appliance task list using taskName
	 * @param  taskName Name of task to get
	 * @return ApplianceTask matching taskName, else null
	 */
	public ApplianceTask getTaskFromName(String taskName) {
		for (ApplianceTask task : tasks) {
			if (task.getTaskName().equals(taskName)) {
				return task;
			}
		}
		return null;
	}
	
	/**
	 * Does a ApplianceTask from the Appliance task list using an ApplianceTask
	 * The exact instance of the ApplianceTask must be used for security purposes
	 * @param  task ApplianceTask to run
	 * @return True or false respective to if the task ran successfully
	 */
	public boolean doTask(ApplianceTask task) {
		// Check if exact task object is member of appliance
		if (tasks.contains(task)) {
			// Get method as defined by task
			Method taskMethod = task.getTaskMethod();

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
	 * Get the Method object for a given name
	 * @param  methodName Name of method to get
	 * @return Method object with name of methodName, if not found return null
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
	 * Return the type of Appliance as a string
	 * @return  Appliance type as string
	 */
	public abstract String getType();

}