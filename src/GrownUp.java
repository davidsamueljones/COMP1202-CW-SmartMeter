/**
 * Class of Person representing a grown up.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class GrownUp extends Person {

	/**
	 * Constructor for GrownUp class.
	 * @param  name Name of grown up
	 * @param  age Age of grown up n >= 18
	 * @param  gender Gender of grown up
	 */
	public GrownUp(String name, int age, String gender) {
		super(name, age, gender);
		
		// Check if arguments are sensible for Person type
		if (!isAdult()) {
			Logger.error("Grown ups age cannot be less than 18");
		}
	}
	
}