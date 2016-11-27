import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that represents a person's task
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class PersonTask {
	private int setTime;
	private House targetHouse;
	private String taskName;

	public PersonTask(int setTime, House targetHouse, String taskName) {
		
		// Check arguments are sensible
		if (targetHouse == null) {
			Logger.error("House cannot be null");
			throw new IllegalArgumentException();
		}
		if (setTime < targetHouse.getTime()) {
			Logger.error("Task cannot be set in the past in respect to target house");
			throw new IllegalArgumentException();
		}
		if (taskName == null) {
			Logger.error("task name cannot be null");
			throw new IllegalArgumentException();
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
	 * Attempt to run the task in the target appliance
	 * @return  True or false respective to if the task ran successfully
	 */
	public boolean doTask(Person person) {
		// Find appliances not in use with task name
		ArrayList<Appliance> appliances = searchAppliancesForTask(targetHouse.getAppliancesIterator());
		
		// Check if any appliances were found
		if (appliances.size() > 0) {
			// Do task on first available appliance
			Appliance appliance = appliances.get(0);
			// Get ApplianceTask of appliance
			ApplianceTask task = appliance.getTaskFromName(taskName);
			
			// Check if person is allowed to do task
			if (person.canDoTask(task)) {
				return appliance.doTask(task); // true if task ran sucessfully, else false
			}
			else {
				Logger.warning("Person attempting to do task is not an adult");
			}
		}
		else {
			Logger.warning("An available appliance to do task was not found");
		}
	
		
		return false; // task failed
	}
	
	/**
	 * 
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