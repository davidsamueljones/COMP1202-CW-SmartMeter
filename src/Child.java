/**
 * Class of Person representing a child
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Child extends Person {

	/**
	 * Constructor for Child class
	 * @param  name Name of child
	 * @param  age Age of child 0 <= n < 18 
	 */
	public Child(String name, int age) {
		super(name, age);
		
		// Check if arguments are sensible for Person type
		if (age >= 18) {
			Logger.error("Child's age cannot be 18+");
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public boolean isAdult() {
		return false;
	}

}