import java.util.ArrayList;

/**
 * Abstract class that represents a Person.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Person {
	private String name;
	private int age;
	private String gender;
	
	// Initialise ongoing variables
	private ArrayList<PersonTask> tasks  = new ArrayList<PersonTask>();

	/**
	 * Constructor for Person class.
	 * @param  name Name of person (non-empty)
	 * @param  age Age of person >= 0
	 * @param  gender Gender of child (M/F/O)
	 */
	protected Person(String name, int age, String gender) {
		// Throw an exception when constructing if arguments are not sensible	
		if (name == null || name.isEmpty()) {
			Logger.error("Person's name cannot be null or empty");
		}
		if (age < 0) {
			Logger.error("Person's age cannot be negative");
		}
		if (!(gender.equals("M") || gender.equals("F") || gender.equals("O"))) {
			Logger.error("Person's gender must be Male (M), Female (F) or Other (O)");
		}
		
		// Assign variables
		this.name = name;
		this.age = age;
		this.gender = gender;

	}

	/**
	 * @return  Value of name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return  Value of age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @return  Value of gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * Adds a Task to Person's tasks.
	 * @param  task Task to add
	 */
	public void addTask(PersonTask task) {
		// Check task is not null
		if (task == null) {
			Logger.warning(String.format("Task not added to '%s' - null task", name));
		}
		// Check for task occurring at same time
		else if (getTaskAtTime(task.getSetTime()) != null) {
			Logger.warning(String.format("Task '%s' not added to '%s' - task already scheduled for time '%d'", 
					task.getTaskName(), name, task.getSetTime())); 
		}
		// Task okay
		else {
			Logger.message(String.format("Task '%s' added to '%s' for time '%d'", 
					task.getTaskName(), name, task.getSetTime()));
			tasks.add(task);
		}
	}
	
	/**
	 * Removes a Task from Person's tasks by object.
	 * @param  task Task to remove
	 */
	public void removeTask(ApplianceTask task) {
		if (task == null) {
			Logger.warning(String.format("Task not removed from '%s' - null task", name)); 
		}
		else {
			// Attempt to remove task, returns true if successful
			if (tasks.remove(task)) {
				Logger.message(String.format("Task '%s' removed from '%s'", task.getName(), name));
			}
			else {
				Logger.warning(String.format("Task '%s' not removed from '%s' - task not found", task.getName(), name)); 
			}
		}
	}

	/**
	 * Finds the task a person is doing at a time
	 * @param  time Time 
	 * @return  Task at time, null if not found
	 */
	public PersonTask getTaskAtTime(int time) {
		// Search for task time
		for (PersonTask task : tasks) {
			if (task.getSetTime() == time) {
				return task;
			}
		}	
		return null; // task not found
	}
	
	/**
	 * Simulate a unit time passing for Person.
	 * @param  currentHouse House timePasses has been called from
	 */
	public void timePasses(House currentHouse) {
		// Process each task, allow multiple tasks to be called in one unit time
		for (PersonTask task : tasks) {
			// If task belongs to calling house and current time is now
			if (currentHouse == task.getTargetHouse() && currentHouse.getTime() == task.getSetTime()) {
				task.doTask(this); // attempt to do task
				tasks.remove(task);
				break;
			}
		}
	}
	
	/**
	 * Check if person can do appliance's task (adult check).
	 * @param  task Task to complete
	 * @return  True or False respectively
	 */
	public boolean canDoTask(ApplianceTask task) {
		if (!task.getIsAdultOnly() | isAdult()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Return whether or not the Person is an adult.
	 * @return  True or False respectively
	 */
	public boolean isAdult() {
		return isAdult(age);
	}
	
	/**
	 * Return whether or not an age defines a Person as an adult.
	 * Adult is defined as a Person that is 18+.
	 * @param  age Age to check
	 * @return  True or False respectively
	 */
	public static boolean isAdult(int age) {
		if (age >= 18) {
			return true;
		} 
		else {
			return false;
		}
	}

}