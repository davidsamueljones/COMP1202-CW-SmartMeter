import java.lang.reflect.Method;

/**
 * Class that represents an Appliance task.
 * A task consists of a name that is used to identify a task. 
 * Properties of the task and a method to invoke the task also exist.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ApplianceTask {
	// ApplianceTask properties
	private String name;
	private Method method;
	private boolean isAdultOnly;
	private Boolean requiredState; // null when no requiredState

	/**
	 * Constructor for ApplianceTask.
	 * Set requiredState to null, representing a task that can be run no matter the state.
	 * @param  name Name of task
	 * @param  method Method attached to task
	 * @param  isAdultOnly Whether task can only be done by adult
	 */
	public ApplianceTask(String name, Method method, boolean isAdultOnly) {
		this(name, method, isAdultOnly, null);
	}

	/**
	 * Constructor for ApplianceTask.
	 * @param  name Name of task
	 * @param  method Method attached to task
	 * @param  isAdultOnly Whether task can only be done by adult
	 * @param  requiredState Whether task requires a specific current state of Appliance
	 */
	public ApplianceTask(String name, Method method, boolean isAdultOnly, Boolean requiredState) {
		// Check if task has a valid method, else throw an exception 
		if (method == null) {
			Logger.error(String.format("Task name '%s' does not have a valid method", name));		
		}

		// Assign properties
		this.name = name;
		this.method = method;
		this.isAdultOnly = isAdultOnly;
		this.requiredState = requiredState;
	}

	/**
	 * @return  Value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return  Value of method
	 */
	public Method getMethod() {
		return method;
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