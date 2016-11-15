/**
 * Class of Appliance representing a TV
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class TV extends Appliance {

	/**
	 * Constructor for TV class
	 */
	public TV() {
		super(1, 0, 0, -1);
	}

	/**
	* Method to start the TV
	*/    
	public void turnOn() {
		use();
	} 

	/**
	* Method to stop the TV
	*/    
	public void turnOff() {
		stop();
	} 

}