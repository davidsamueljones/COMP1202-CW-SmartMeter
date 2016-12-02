import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Class to handle logging.
 * Designed to use static methods so Logger does not
 * need to be instantiated to be run.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Logger {
	// Log file
	private static String filename = "log.txt";
	
	// Default Message settings
	private static boolean messageToLog = true;
	private static boolean messageToCmd = true;
	
	// Default Warning settings
	private static boolean warningLog = true;
	private static boolean warningToCmd = true;

	// Default Error settings
	private static boolean errorLog = true;

	/**
	 * Log a message.
	 * Use default logger properties.
	 * @param  msg Message to output
	 */
	public static void message(String msg) {
		message(msg, true);
	}

	/**
	 * Log a message.
	 * Use default file logging property, use parameter logging property for writing to commmand line.
	 * @param  msg Message to output
	 * @param  toCmd True or false respective to whether output should go to command line
	 */
	public static void message(String msg, boolean toCmd) {
		message(msg, toCmd, warningToCmd);
	}

	/**
	 * Log a message.
	 * Use parameter logging properties.
	 * @param  msg Message to output
	 * @param  toCmd True or false respective to whether output should go to command line
	 * @param  log True or false respective to whether output should go to log file
	 */
	public static void message(String msg, boolean toCmd, boolean log) {		
		// Write error to log file
		if (messageToLog) {
			writeLogMessage(msg);
		}
		// Write error to command line
		if (messageToCmd) {
			System.out.println(msg);
		}
	}
	
	/**
	 * Log a warning.
	 * Use default logger properties.
	 * @param  msg Message to output
	 */
	public static void warning(String msg) {
		warning(msg, true);
	}

	/**
	 * Log a warning.
	 * Use default file logging property, use parameter logging property for writing to command line.
	 * @param  msg Message to output
	 * @param  toCmd True or false respective to whether output should go to command line
	 */
	public static void warning(String msg, boolean toCmd) {
		warning(msg, toCmd, warningToCmd);
	}

	/**
	 * Log a warning.
	 * Use parameter logging properties.
	 * @param  msg Message to output
	 * @param  toCmd True or false respective to whether output should go to command line
	 * @param  log True or false respective to whether output should go to log file
	 */
	public static void warning(String msg, boolean toCmd, boolean log) {
		msg = "[WARNING] " + msg;
		
		// Write error to log file
		if (warningLog) {
			writeLogMessage(msg);
		}
		// Write error to command line
		if (warningToCmd) {
			System.err.println(msg);
		}
	}

	/**
	 * Log an error.
	 * Use default logger properties.
	 * @param  msg Message to output
	 */
	public static void error(String msg) {
		msg = "[ERROR] " + msg;
		
		// Write error to log file
		if (errorLog) {
			writeLogMessage(msg);
		}
		// Throw error to command line
		throw new RuntimeException(msg);
	}

	/**
	 * Append a message to a file.
	 * @param  msg Message to write
	 */
	private static void writeLogMessage(String msg) {
		// Open PrintStream
		try {
			// Open a writer to append
			PrintStream logWriter = new PrintStream(new FileOutputStream(filename, true)); 
			// Attempt message write
			logWriter.println(msg);
			// Close file writer
			logWriter.close();
		} catch (Exception e) {
			// Post warning to command line
			warning(String.format("Cannot log to file '%s'", filename), true, false);
		}
	}

	/**
	 * Delete current log file.
	 * @return  Whether delete was successful
	 */
	public static boolean deleteExistingLog() {
		return new File(filename).delete();
	}

}