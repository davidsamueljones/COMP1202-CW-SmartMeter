import java.util.ArrayList;

/**
 * Abstract class that represents a Person
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public abstract class Person {
	private String name;
	private int age;
	private ArrayList<PersonTask> tasks;

	/**
	 * Constructor for Person class
	 * @param  name Name of person
	 * @param  age Age of person >= 0
	 */
	protected Person(String name, int age) {

		// Throw an exception when constructing if arguments are not sensible	
		if (name == null) {
			Logger.error("Person cannot have null as a name");
			throw new IllegalArgumentException();
		}
		if (age < 0) {
			Logger.error("Person's age cannot be negative");
			throw new IllegalArgumentException();
		}

		// Assign variables
		this.name = name;
		this.age = age;

		// Initialise ongoing variables
		tasks = new ArrayList<PersonTask>();
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
	 * Adds a Task to Person's tasks
	 * @param  task Task to add
	 */
	public void addTask(PersonTask task) {
		// Check task is not null
		if (task == null) {
			Logger.warning("Task not added to person - null task");
		}
		// Check for task occurring at same time
		else if (getTaskAtTime(task.getSetTime()) != null) {
			Logger.warning("Task not added to person - task already scheduled for time"); 
		}
		// Task okay
		else {
			System.out.println(String.format("Task '%s' added to person", task.getTaskName()));
			tasks.add(task);
		}
	}
	
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
	 * Removes a Task from Person's tasks by object
	 * @param  task Task to remove
	 */
	public void removeTask(ApplianceTask task) {
		if (task == null) {
			Logger.warning("Task not removed from person - null task"); 
		}
		else {
			// Attempt to remove task, returns true if successful
			if (tasks.remove(task)) {
				System.out.println(String.format("Task '%s' removed from person", task.getTaskName()));
			}
			else {
				Logger.warning("Task not removed from person - not found"); 
			}
		}
	}

	/**
	 * Simulate a unit time passing for Person
	 * @param  currentTime Time to process tasks for
	 */
	public void timePasses(int currentTime) {
		// Process each task
		for (PersonTask task : tasks) {
			// If task should be done now
			if (currentTime == task.getSetTime()) {
				task.doTask(this); // attempt to do task
				tasks.remove(task); // remove task from tasks
				break; // break from loop, can only complete one task in unit time
			}
		}
	}
	
	/**
	 * Check if person can do appliance's task (adult check)
	 * @param task Task to complete
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
	 * Return whether or not the Person is an adult
	 * @return  True or False respectively
	 */
	public abstract boolean isAdult();

}