/**
 * Class of Person representing a grown up
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class GrownUp extends Person {

	/**
	 * Constructor for GrownUp class
	 * @param  name Name of grown up
	 * @param  age Age of grown up n >= 18
	 */
	public GrownUp(String name, int age) {
		super(name, age);
		
		// Check if arguments are sensible for Person type
		if (age < 18) {
			Logger.error("Grown ups age cannot be less than 18");
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public boolean isAdult() {
		return true;
	}

}