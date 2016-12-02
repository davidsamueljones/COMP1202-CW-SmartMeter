import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for reading configuration formatted files.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ConfigurationReader {
	// Configuration Reader - Rules
	// Prefix to line that indicates ignore as is comment
	private static final String COMMENT_LABEL = "//";
	
	// Configuration commands that can be called from configuration file separated by command type
	// Other configuration entries will be assumed as Tasks
	// House Class
	private static final String CREATE_HOUSE_CMD = "House";
	// Meter Classes
	private static final String[] CREATE_METER_CMDS = UtilityType.asMeterStringArray();
	// Appliance Classes
	private static final String[] CREATE_APPLIANCE_CMDS = ApplianceType.asClassStringArray();
	// Person Classes
	private static final String CREATE_PERSON_CMD = "Person";
	private static final String CHILD_CLASS_NAME = "Child";
	private static final String GROWN_UP_CLASS_NAME = "GrownUp";
	// Task Class
	private static final String TASK_CLASS_NAME = "PersonTask";
	
	// Reader instance
	private BufferedReader fileReader;
	private String filename;
	
	/**
	 * Constructor for ConfigurationReader.
	 * No arguments, use default filename.
	 */
	public ConfigurationReader() {
		this("myHouse.txt");
	}
	
	/**
	 * Constructor for ConfigurationReader.
	 * @param  filename Name of file to load
	 */
	public ConfigurationReader(String filename) {
		try {
			this.filename = filename; // remember filename
			// Create BufferedReader using FileReader
			fileReader = new BufferedReader(new FileReader(filename));
		} 
		catch (FileNotFoundException e) {
			// Specific exception case 
			Logger.error(String.format("'%s' not found", filename));
		}		
		catch (Exception e) {
			// General exception case
			Logger.error(String.format("Unknown error reading '%s'", filename));
		}
	}
	
	/**
	 * @return  Current filename of file being read
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Read next line of file in fileReader.
	 * @return  Line of file
	 */
	private String getLine() {
		try {
			// Reads next line
			return fileReader.readLine();
		} 
		catch (Exception e) {
			// Note: Type of caught exception does not matter as we only
			// need to know when line read fails
			return null;
		}
	}
	
	/**
	 * Return ready state of fileReader.
	 * @return  Ready state
	 */
	private boolean fileReady() {
		try {
			return fileReader.ready();
		} 
		catch (Exception e) {
			// Note: Type of caught exception does not matter as we only 
			// need to know when file not ready
			return false;
		}
	}
	
	/**
	 * Create and set up a House defined by the configuration file.
	 * @return  ArrayList of House, returns empty ArrayList if configuration read error
	 */
	public ArrayList<House> getHouseFromFile() {
		ArrayList<House> houses = new ArrayList<House>();
		House lastHouse = null;
		Person lastPerson = null;

		// Loop through all lines of file
		while (fileReady()) {
			// Get next line
			String line = getLine();
			// Handle line read errors
			if (line == null) {
				Logger.error("Line read as null on house configuration");
				throw new RuntimeException();
			}
			
			// Ignore line if it is a comment or blank
			if (line.equals("") || line.substring(0, COMMENT_LABEL.length()).equals(COMMENT_LABEL)) {
				continue;
			}
			
			// Parse line
			String type = getType(line);
			Object[] args = createClassArguments(line);
			
			// Handle creation of new house
			if (type.equals(CREATE_HOUSE_CMD)) {
				lastHouse = getHouse(type, args);
				houses.add(lastHouse);
				lastPerson = null; // reset last person
			}
			else if (lastHouse != null) {
				// Handle addition of meters
				if (isStringInList(type, CREATE_METER_CMDS)) {
					lastHouse.addMeter(getMeter(type, args));
				}
				// Handle addition of appliances
				else if (isStringInList(type, CREATE_APPLIANCE_CMDS)) {
					lastHouse.addAppliance(getAppliance(type, args));
				}
				// Handle addition of people
				else if (type.equals(CREATE_PERSON_CMD)) {
					lastPerson = getPerson(args);
					lastHouse.addPerson(lastPerson);
				}
				// Handle addition of tasks (assume non object is a Task)
				else if (lastPerson != null) {
					// Create task targeting last house
					args = createTaskArguments(line, lastHouse);
					lastPerson.addTask(getTask(args));
				}
				else {
					Logger.error(String.format("Task '%s' added before person has been added", type));
					new RuntimeException();
				}
			}
		}

		return houses;
	}
	
	/**
	 * Splits line of text by seperator (:).
	 * @param  line Line to split
	 * @return  String array of split string
	 */
	private static String[] splitAsClass(String line) {
		// Split only by type and argument delimeter
		String[] splitLine = line.split(":");
		
		// Check if parse is correct
		if (splitLine.length == 0 || splitLine.length > 2){
			Logger.error("Line could not parsed on house configuration");
			throw new RuntimeException();
		}
		
		return splitLine;
	}
	
	/**
	 * Get the type part of a line split.
	 * @param  line Line to split
	 * @return  Type of 
	 */
	private static String getType(String line) {
		String[] splitLine = splitAsClass(line);
		if (splitLine.length >= 1) {
			return  splitLine[0];
		} 
		else {
			return null;
		}
	}
	
	/**
	 * Converts user string array to objects for class.
	 * @param  line String array of arguments 
	 * @return  Object array of arguments
	 */
	private static Object[] createClassArguments(String line) {
		String[] splitLine = splitAsClass(line);
		if (splitLine.length == 2) {
			// Split by argument seperator
			String[] strArgs = splitLine[1].split(",");
			// Convert string array of arguments to object array
			return convertArgumentsToObject(strArgs);
		} 
		else {
			return new Object[0];
		}
	}
	
	/**
	 * Converts user string array to objects for class, also appends targetHouse argument.
	 * @param  line String array of arguments 
	 * @param  targetHouse Extra parameter defined at runtime
	 * @return  Object array of arguments
	 */
	private static Object[] createTaskArguments(String line, House targetHouse) {
		// --- Handle user parameters
		// Split by all configuration delimeters (: or ,)
		String[] splitLine = line.split(":|,");

		// Check if parse is correct
		if (splitLine.length == 0){
			Logger.error("Line could not parsed on house configuration");
			throw new RuntimeException();
		}
		Object[] objArgs = convertArgumentsToObject(splitLine);
		
		// --- Handle runtime parameters
		// Convert Object array to ArrayList to dynamically add more arguments
		ArrayList<Object> args = new ArrayList<Object>(Arrays.asList(objArgs));
		args.add(targetHouse);
		
		// Convert back to Object array
		objArgs = args.toArray();

		return objArgs;
	}
	
	/**
	 * Convert a String array of arguments into an Object array of arguments
	 * (Casting where appropriate).
	 * @param  args String array of arguments
	 * @return  Object array of arguments
	 */
	private static Object[] convertArgumentsToObject(String[] args) {
		// Create Object array for arguments
		Object[] objArgs;
		if (args == null) {
			// No arguments
			return new Object[0];
		}
		else {
			objArgs = new Object[args.length];
		}
		
		// Loop for each string argument
		for (int i = 0; i < args.length; i++) {

			try {
				// Read as double
				if (args[i].contains(".")) {
					objArgs[i] = Double.parseDouble(args[i]);
				} 
				// Read as integer
				else {
					objArgs[i] = Integer.parseInt(args[i]);
				}
			}
			catch (Exception e) {
				// Read as boolean
				if (args[i].equals("true") || args[i].equals("false")) {
					objArgs[i] = Boolean.parseBoolean(args[i]);			
				}
				// Trim explicit string declarators
				else if (args[i].charAt(0) == ('"') && args[i].charAt(args[i].length() - 1) == ('"')) {
					// Trim speech marks
					objArgs[i] = args[i].substring(1, args[i].length() - 1);
				} 
				// Assign as string directly
				else {
					objArgs[i] = args[i];
				}
			}
		}
				
		return objArgs;
	}
	
	/**
	 * Searches a String array for a specified string.
	 * @param  searchTerm String to match
	 * @param  searchList Array to search 
	 * @return  True or false in respect to if the search term is found
	 */
	private static boolean isStringInList(String searchTerm, String[] searchList) {
		// Loop through searchList
		for (String listItem : searchList) {
			// If found, return true
			if (searchTerm.equals(listItem)){
				return true;
			}
		}
		return false; // not found
	}
	
	/**
	 * Cast a return value of getObject to House.
	 * @param  objArgs String array of arguments 
	 * @return  Instance of created House
	 */
	private static House getHouse(String houseName, Object[] objArgs)
	{
		return (House) getObject(houseName, objArgs);
	}
	
	/**
	 * Cast a return value of getObject to Meter.
	 * @param  meterName Name of meter to create; this is the name of the class
	 * @param  objArgs Arguments for constructor as Object array
	 * @return  Instance of created Meter
	 */
	private static Meter getMeter(String meterName, Object[] objArgs)
	{
		return (Meter) getObject(meterName, objArgs);
	}
	
	/**
	 * Cast a return value of getObject to Appliance.
	 * @param  applianceName Name of appliance to create; this is the name of the class
	 * @param  objArgs Arguments for constructor as Object array
	 * @return  Instance of created Appliance
	 */
	private static Appliance getAppliance(String applianceName, Object[] objArgs)
	{
		return (Appliance) getObject(applianceName, objArgs);
	}

	/**
	 * Cast a return value of getObject to Person.
	 * First attempt to create a Child, if this fails attempt to create an Adult.
	 * @param  objArgs Arguments for constructor as Object array
	 * @return  Instance of created Person
	 */
	private static Person getPerson(Object[] objArgs)
	{
		// Note: The method used to determine if a Person should be a 
		// Child or GrownUp is not an ideal ideal solution because Exceptions are dictating flow of the program.
		// However, as only class constructors know argument order (allowence), the correct class type
		// cannot be found using other methods.
		
		Person person;
		// First attempt to create a Child
		// getObject will throw an exception if arguments are invalid for Child
		// This exception is caught so that GrownUp creation can be attempted
		try {
			person = (Person) getObject(CHILD_CLASS_NAME, objArgs);		
		}
		catch (Exception e) {
			person = null;
		}
		// If Child was not created, attempt to create a GrownUp
		// getObject will throw an exception if arguments are invalid for GrownUp
		// Thrown exceptions are not caught as arguments do not assume a valid person
		// and no other people types exist
		if (person == null) {
			person = (Person) getObject(GROWN_UP_CLASS_NAME, objArgs);
		}
		
		// Return created person
		return person;
	}
	
	/**
	 * Cast a return value of getObject to PersonTask.
	 * @param  objArgs Arguments for constructor as Object array
	 * @return  Instance of created Person
	 */
	private static PersonTask getTask(Object[] objArgs)
	{
		return (PersonTask) getObject(TASK_CLASS_NAME, objArgs);
	}
	
	/**
	 * Create an instance of an Object by name with parameters dictated by a String array.
	 * @param  objectName Name of object to create; this is the name of the class
	 * @param  objArgs Arguments for constructor as Object array
	 * @return  Instance of created Object
	 */
	private static Object getObject(String objectName, Object[] objArgs)
	{
		// Get instantiation class 
		return getClassInstance(objectName, objArgs);
	}
	
	/**
	 * Get the appropriate constructor for a class and instantiate object.
	 * @param  className Name of class to create
	 * @param  objArgs Arguments for constructor as Object array
	 * @return  Instantiated object of class
	 */
	private static Object getClassInstance(String className, Object[] objArgs) {
		try {
			// Attempt to get class
			Class<?> clazz = Class.forName(className);
			// Get the constructors of the class
			Constructor<?>[] constructors = clazz.getConstructors();
			// Get the constructor that has matching arguments 
			Constructor<?> constructor = findConstructorWithArguments(constructors, objArgs);
			
			// Create an instance of the appliance
			if (constructor == null) {
				throw new NullPointerException();
			}
			return constructor.newInstance(objArgs);

		}
		catch (ClassNotFoundException e) {
			Logger.error(String.format("A class of '%s' does not exist", className));
		}
		catch (NullPointerException e) {
			Logger.error(String.format("A constructor for class '%s' using provided arguments was not found", className));
		}
		catch (IllegalArgumentException e) {
			Logger.error(String.format("Illegal arguments on instantiation of class '%s' ", className));
		}
		catch (InvocationTargetException e) {
			Logger.error(String.format("Class '%s' construction failed\nCaught exception: %s", className, e.getCause()));
		}
		catch (Exception e) {
			Logger.error(String.format("Class '%s' could not be instantiated for an unknown error", className));
		}
		return null;
	}
	
	/**
	 * Get the appropriate constructor from an array of constructors as dictated by the arguments.
	 * @param  constructors Array of constructors to search
	 * @param  objArgs Array of objects that are arguments to match
	 * @return  Constructor that matches
	 */
	private static Constructor<?> findConstructorWithArguments(Constructor<?>[] constructors, Object[] objArgs) {
		// Loop for each constructor
		for (Constructor<?> constructor : constructors) {
			// Get types of constructor parameters
			Type[] paramTypes = constructor.getParameterTypes();
			
			// Check number of arguments the same in constructor and object
			if (paramTypes.length != objArgs.length) {
				continue; // jump to next loop instance
			}
			
			// Loop for each constructor/object argument  
			boolean found = true;
			for (int i = 0; i < paramTypes.length; i++) {
				// Get lowercase version of both types to ignore casts - can cause 
				// conflicts with badly named case sensitive classes
				String constructorType = paramTypes[i].getTypeName().toLowerCase();
				String objectType = objArgs[i].getClass().getTypeName().toLowerCase();
				
				// If types are incompatable
				// Note: objectType will never be primitive so constructorType can never contain 
				// objectType unless objectType contains constructorType
				if (!objectType.contains(constructorType)) {
					found = false;
					break; // exit loop
				}	
			}
			// If all arguments match return the constructor
			if (found == true) {
				return constructor;
			}
		}

		return null; // no constructor found
	}
	
}