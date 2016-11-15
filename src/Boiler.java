/**
 * Class of Appliance representing a boiler
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Boiler extends Appliance {

	/**
	 * Constructor for Boiler class
	 */
	public Boiler() {
		super(0, 1, 0, -1);
	}

	/**
	* Method to start the boiler
	*/    
	public void turnOn() {
		use();
	} 

	/**
	* Method to stop the boiler
	*/    
	public void turnOff() {
		stop();
	} 

}