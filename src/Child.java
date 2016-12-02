/**
 * Class of Person representing a child.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Child extends Person {

	/**
	 * Constructor for Child class.
	 * @param  name Name of child
	 * @param  age Age of child n < 18 
	 * @param  gender Gender of child
	 */
	public Child(String name, int age, String gender) {
		super(name, age, gender);
		
		// Check if arguments are sensible for Person type
		if (isAdult()) {
			Logger.error("Child's age cannot be 18+");
		}
	}

}