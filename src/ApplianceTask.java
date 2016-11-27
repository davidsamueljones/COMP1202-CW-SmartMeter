import java.lang.reflect.Method;

/**
 * Class that represents an appliances task
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ApplianceTask {
	private String taskName;
	private Method taskMethod;
	private boolean isAdultOnly;
	private Boolean requiredState; // null when no requiredState

	/**
	 * Constructor for ApplianceTask
	 * Set requiredState to null, representing a task that can be run no matter the state
	 * @param  taskName Name of task
	 * @param  isAdultOnly Whether task can only be done by adult
	 */
	public ApplianceTask(String taskName, Method taskMethod, boolean isAdultOnly) {
		this(taskName, taskMethod, isAdultOnly, null);
	}

	/**
	 * Constructor for ApplianceTask
	 * @param  taskName Name of task
	 * @param  isAdultOnly Whether task can only be done by adult
	 * @param  requiredState Whether task requires a specific current state of Appliance
	 */
	public ApplianceTask(String taskName, Method taskMethod, boolean isAdultOnly, Boolean requiredState) {
		// Check if task has a valid method, else throw an exception 
		if (taskMethod == null) {
			Logger.error(String.format("Task name '%s' does not have a valid method", taskName));
			throw new IllegalArgumentException();		
		}

		this.taskName = taskName;
		this.taskMethod = taskMethod;
		this.isAdultOnly = isAdultOnly;
		this.requiredState = requiredState;
	}

	/**
	 * @return  Value of taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @return  Value of taskName
	 */
	public Method getTaskMethod() {
		return taskMethod;
	}
	
	/**
	 * @return  Value of isAdultOnly
	 */
	public boolean getIsAdultOnly() {
		return isAdultOnly;
	}
	
	/**
	 * @return  Value of requiredState
	 */
	public Boolean getRequiredState() {
		return requiredState;
	}
	
}