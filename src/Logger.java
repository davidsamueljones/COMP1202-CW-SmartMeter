import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Class to handle logging
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Logger {
	// Log file
	private static String filename = "log.txt";
	
	// Default Warning settings - Public to allow external changing
	public static boolean warningLog = true;
	public static boolean warningToCmd = true;

	// Default Error settings - Public to allow external changing
	public static boolean errorLog = true;
	public static boolean errorToCmd = true;
	
	/**
	 * Log a warning 
	 * Use default logger properties
	 * @param  message Message to output
	 */
	public static void warning(String message) {
		warning(message, true);
	}

	/**
	 * Log a warning 
	 * Use default file logging property, use parameter logging property for writing to commmand line
	 * @param  message Message to output
	 * @param  boolean True or false respective to whether output should go to command line
	 */
	public static void warning(String message, boolean toCmd) {
		warning(message, toCmd, warningToCmd);
	}

	/**
	 * Log a warning 
	 * Use parameter logging properties
	 * @param  message Message to output
	 * @param  boolean True or false respective to whether output should go to command line
	 * @param  boolean True or false respective to whether output should go to log file
	 */
	public static void warning(String message, boolean toCmd, boolean log) {
		message = "[WARNING] " + message;
		
		// Write error to log file
		if (warningLog) {
			writeLogMessage(message);
		}
		// Write error to command line
		if (warningToCmd) {
			System.err.println(message);
		}
	}

	/**
	 * Log an error 
	 * Use default logger properties
	 * @param  message Message to output
	 */
	public static void error(String message) {
		message = "[ERROR] " + message;
		
		// Write error to log file
		if (errorLog) {
			writeLogMessage(message);
		}
		// Write error to command line
		if (errorToCmd) {
			System.err.println(message);
		}
	}

	/**
	 * Append a message to a file
	 * @param  message Message to write
	 */
	private static void writeLogMessage(String message) {
		// Open PrintStream
		try {
			// Open a writer to append
			PrintStream logWriter = new PrintStream(new FileOutputStream(filename, true)); 
			// Attempt message write
			logWriter.println(message);
			// Close file writer
			logWriter.close();
		} catch (Exception e) {
			// Post warning to command line
			warning(String.format("Cannot log to file '%s'", filename), true, false);
		} finally {
			
		}
	}

	/**
	 * Delete current log file
	 * @return  Whether delete was successful
	 */
	public static boolean deleteExistingLog() {
		return new File(filename).delete();
	}

}