/**
 * Class of Appliance representing a kettle
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Kettle extends Appliance {
	
	/**
	 * Constructor for Kettle class
	 */
	public Kettle() {
		super(20, 0, 1, 1);
	}

	/**
	* Method to start the kettle
	*/    
	public void boil() {
		use();
	} 

}