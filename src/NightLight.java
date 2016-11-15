/**
 * Class of Appliance representing a night light
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class NightLight extends Appliance {

	/**
	 * Constructor for NightLight class
	 */
	public NightLight() {
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