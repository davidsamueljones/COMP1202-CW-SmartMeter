import java.util.ArrayList;

/**
 * Simulation class handling construction and simulation 
 * of houses from configuration files.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Simulation {
	private ArrayList<House> simHouses;
	private int simLength;
	
	/**
	 * --- Main
	 * @param  args : [0] = filename, [1] = simulation length
	 */
	public static void main(String[] args) {
		// Clear current log file
		Logger.deleteExistingLog();
		
		// --- Parse arguments
		// If first argument is provided, use as filename, else use ConfigurationReader default
		// If second argument is provided, use as simulation length, else use simulation length default
		String filename = null;
		int simLength = 96;
		
		if (args.length >= 3) {
			Logger.error("Too many arguments. Correct usage is 'java Simulate [filename] [sim_length]'");
		}
		if (args.length >= 2 ) {
			// Use first argument as filename
			try {
				simLength = Integer.parseInt(args[1]);
			}
			catch (Exception e) {
				Logger.error("Simulation length argument must be an integer'");
			}

		}
		if (args.length >= 1 ) {
			// Use first argument as filename
			filename = args[0];
		}
		
		// --- Run simulation
		new Simulation(filename, simLength);
	}
	
	/**
	 * Constructor for Simulation.
	 * Reads in configuration for simulation and runs simulation immediately.
	 * @param  filename Filename of configuration file to read in
	 * @param  simLength Length of unit time length to run the simulation
	 */
	public Simulation(String filename, int simLength) {

		// Read configuration into House object
		ConfigurationReader reader;
		if (filename == null) {
			// Use default filename
			reader = new ConfigurationReader();
		}
		else {
			// Use provided filename
			reader = new ConfigurationReader(filename);
		}

		// Parse the configuration file
		Logger.message(String.format("CONFIGURATION PARSING - '%s'", reader.getFilename()));
		Logger.message("---------------------------------");
		this.simHouses = reader.getHouseFromFile();
		if (simHouses.size() == 0) {
			Logger.error("No houses found in file");
		}
		
		// Assign simulation properties
		this.simLength = simLength;
		
		// Start a simulation
		Logger.message("\nSIMULATION");
		Logger.message("---------------------------------");
		simulate();
	}
	
	/**
	 * Run simulation on classes House ArrayList for classes simLength.
	 */
	public void simulate() {
		// Loop for simulation length
		for (int i = 0; i < simLength; i++) {
			// Simulate a unit time passing for each house
			for (House simHouse : simHouses) {
				simHouse.timePasses();
			}
			// Sleep between unit time increments to slow down simulation 
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				Logger.error("Thread sleep failed");
			}
		}	
	}
	
}
