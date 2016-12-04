import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that represents a person's task.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class PersonTask {
	private int setTime;
	private House targetHouse;
	private String taskName;

	/**
	 * Constructor for PersonTask.
	 * @param  taskName Name of task to run, as defined in Appliance task lists
	 * @param  setTime Time for task to run, this time must be in the future of House
	 * @param  targetHouse House for task to be run in, used to find Appliance list
	 */
	public PersonTask(String taskName, int setTime, House targetHouse) {
		
		// Check arguments are sensible
		if (targetHouse == null) {
			Logger.error("House cannot be null");
		}
		if (setTime < targetHouse.getTime()) {
			Logger.error("Task cannot be set in the past in respect to target house");
		}
		if (taskName == null) {
			Logger.error("task name cannot be null");
		}

		// Assign properties
		this.setTime = setTime;
		this.targetHouse = targetHouse;
		this.taskName = taskName;
	}
	
	/**
	 * @return  Value of taskTime
	 */
	public int getSetTime() {
		return setTime;
	}
	
	/**
	 * @return  Value of taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	
	/**
	 * @return  Value of targetHouse
	 */
	public House getTargetHouse() {
		return targetHouse;
	}

	/**
	 * Attempt to run the task in the target appliance.
	 * @param  person Person doing task
	 * @return  True or false respective to if the task ran successfully
	 */
	public boolean doTask(Person person) {
		// Find appliances not in use with task name
		ArrayList<Appliance> appliances = searchAppliancesForTask(targetHouse.getAppliancesIterator());
		
		// Check if any appliances were found
		if (appliances.size() > 0) {
			
			// Target first available appliance
			Appliance targetAppliance = null;
			ApplianceTask task = null;
			// Search if person can do task for available appliances
			for (Appliance searchAppliance : appliances) {
				task = searchAppliance.getTaskFromName(taskName);
				// If task can be done assign it as the target
				if (person.canDoTask(task)) {
					targetAppliance = searchAppliance;
					break; // target found, exit loop
				}
			}
			
			// Check if appliance was found
			if (targetAppliance != null) {
				// Attempt task, true if ran successfully, else false
				boolean taskCompleted = targetAppliance.doTask(task);
				// Output if task completed
				if (taskCompleted) {
					Logger.message(String.format("'%s' did task '%s' on appliance '%s' in '%s'",
					 person.getName(), task.getName(), targetAppliance.getType(), targetHouse.getName()));
				}
				return taskCompleted;
			}
			else {
				Logger.warning(String.format("'%s' could not do task '%s' in '%s' because task requires an adult",
					 person.getName(), taskName, targetHouse.getName()));
			}
		}
		else {
			Logger.warning(String.format("'%s' could not do task '%s' in '%s' because an available appliance could not be found", 
					person.getName(), taskName, targetHouse.getName()));
		}
	
		
		return false; // task failed
	}

	/**
	 * Search through an appliance list for appliances that are available for a task. 
	 * @param  itrAppliances Iterator of type Appliance to search through 
	 * @return  Appliances found in itrAppliances that match search criteria
	 */
	public ArrayList<Appliance> searchAppliancesForTask(Iterator<Appliance> itrAppliances) {
		ArrayList<Appliance> matchedAppliances = new ArrayList<Appliance>();
		
		// Loop through all appliances
		while (itrAppliances.hasNext()) {
			Appliance appliance = itrAppliances.next();
			
			// If appliance does not contain task, check next appliance
			ApplianceTask task = appliance.getTaskFromName(taskName);
			if (task == null) {
				continue; // task not found, loop to next
			}
			
			// If requiredState is not set or states match 
			if (task.getRequiredState() == null || appliance.getCurrentState() == task.getRequiredState()) {
				// Add to matched appliances
				matchedAppliances.add(appliance);
			}			
		}
		
		return matchedAppliances;
	}

}