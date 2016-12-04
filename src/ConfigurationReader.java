import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for reading configuration formatted files.
 * A configuration formatted file is a dynamic input method of creating smart Houses using
 * Meters, Appliances, People and Tasks. The internal methods use the Java Reflection library heavily
 * so configuration complexity is moved from the reader to the classes themselves. For this reason, the
 * reader assumes very little and obeys simple formatting rules.
 * 
 * This reader expects the following rules to be followed in configuration formatted files:
 * 
 * General Rules:
 * - A COMMAND is used to instantiate a CLASS or assign a TASK
 * - If a COMMAND is not recognised as a CLASS it is assumed a TASK
 * - A COMMAND must be followed by a semicolon e.g. COMMAND:
 * - Comma separated ARGUMENTS may follow a COMMAND e.g. COMMAND:ARGUMENT1,ARGUMENT2
 * - An OBJECT REFERENCE may store a CLASS for later direct addressing e.g. [VAR_NAME]<-Test:
 * - An existing OBJECT REFERENCE may be used as a COMMAND input e.g. [VAR_NAME]->Test:
 * - Multiple HOUSES can be created, HOUSES do not share CLASSES unless explicitly defined with OBJECT REFERENCES
 * - Comments (//) and blank lines are ignored
 * 
 * Order Rules:
 * - A HOUSE must be the first declaration
 * - An APPLIANCE must be declared before a TASK uses it
 * - A METER will attach to any APPLIANCES already in the HOUSE
 * - A METER will attach to any APPLIANCE newly added to the HOUSE
 * - TASKS are implicitly assigned to the last declared PERSON in the HOUSE
 * - OBJECT REFERENCES must be assigned before they are used
 * 
 * Argument Rules:
 * - ARGUMENTS are passed to CLASS CONSTRUCTORS without prior checks but are implicitly converted
 * - ARGUMENTS are implicitly Strings if they are not numerics or boolean values e.g. Test
 * - ARGUMENTS can be explicitly Strings if they are surrounded by quote marks (trimmed) e.g. "1"
 * - Numeric ARGUMENTS with a decimal are implicitly assumed Double e.g. 1.0 or 52.2
 * - Numeric ARGUMENTS without a decimal are implicitly assumed Integer e.g. 14
 * - Boolean equivalent String values: true/false are converted to the Boolean type
 * 
 * Extended Functionality:
 * OBJECT REFERENCES - A method of temporarily storing objects when creating houses from configuration 
 * files. They must be assigned with a variable name, from which they can be retrieved for targeted
 * applications. 
 * Current objects which can be stored include: 
 * - Person
 * Functionality:
 * - Person can be retrieved for assignment directly to a task, if another person is declared before task declaration
 * - Person can be retrieved for assignment directly to a task in another house. Person reference is implicitly added
 *   to the house and task is carried out in the house.
 * 
 * ---> See example .txt files for example usage
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class ConfigurationReader {
	// --- Reader Rules	
	// Configuration commands that can be called from configuration file separated by command type
	// Other configuration entries will be assumed as Tasks
	private static final String CREATE_HOUSE_CMD = "House";
	private static final String[] CREATE_METER_CMDS = UtilityType.asMeterStringArray();
	private static final String[] CREATE_APPLIANCE_CMDS = ApplianceType.asClassStringArray();
	private static final String CREATE_PERSON_CMD = "Person";
	
	// Explicit Class names not defined as a command
	private static final String CHILD_CLASS_NAME = "Child";
	private static final String GROWN_UP_CLASS_NAME = "GrownUp";
	private static final String TASK_CLASS_NAME = "PersonTask";
	
	// Prefix to line that indicates ignore as is comment
	private static final String COMMENT_LABEL = "//";
	// Part of line to indicate assignment to temporary variable
	private static final String ASSIGN_LABEL = "<-";
	private static final String GET_LABEL = "->";
	// Part of line to indicate that data between two instances should be interpreted as a string
	private static final char STRING_LABEL = '\"'; // "
	// Separators
	private static final char CLASS_SEP_LABEL = ':';
	private static final char ARG_SEP_LABEL = ',';
	
	// Regular Expressions
	// Syntax to assign and store variables inside configuration files
	private static final String VAR_STORAGE_REGEX = String.format("^(\\[)(\\w+)(\\])(%s|%s)", ASSIGN_LABEL, GET_LABEL);
	// Syntax for a class and respective arguments
	private static final String CLASS_REGEX = String.format("(\\w+)(%s)(.+%s?)*", CLASS_SEP_LABEL, ARG_SEP_LABEL);
	// Full line validation for configuration file
	private static final String VALID_LINE_REGEX = String.format("(%s)?%s", VAR_STORAGE_REGEX, CLASS_REGEX);
	
	// --- Instance variables
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
		// Store filename
		this.filename = filename; // remember filename
		openReader();
	}
	
	/**
	 * @return  Current filename of file being read
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Open reader associated with class
	 */
	public void openReader() {
		try {
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
	 * Close reader associated with class
	 */
	public void closeReader() {
		try {
			fileReader.close();
		} catch (IOException e) {
			Logger.error(String.format("File '%s' could not be closed", filename));
		}
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
		// Array to return, holds all created houses
		ArrayList<House> houses = new ArrayList<House>();
		// Keep track of last of object type
		House lastHouse = null;
		Person lastPerson = null;

		// Stores a created object for later use, addressable via string reference
		HashMap<String, Object> objRefs = new HashMap<String, Object>();

		// Loop through all lines of file
		while (fileReady()) {
			// Get next line
			String line = getLine();
			// Handle line read errors
			if (line == null) {
				Logger.error("Line read as null on house configuration");
			}
			
			// Ignore line if it is a comment or blank
			if (line.isEmpty() || line.substring(0, COMMENT_LABEL.length()).equals(COMMENT_LABEL)) {
				continue;
			}
			
			// Validate line is in a valid format
			if (!isValidLine(line)) {
				Logger.error("Line read is not in a valid format");
			}
			
			// Parse line into parts
			String strRef = getReferenceString(line);
			String strClass = getClassString(line);
			
			// Convert classPart to par
			String type = getTypeString(strClass);
			Object[] args = createClassArguments(strClass);
			
			// Keep track of created storable objects
			Object objRef = null;
			
			// Handle creation of new house
			if (type.equals(CREATE_HOUSE_CMD)) {
				lastHouse = getHouse(type, args);
				houses.add(lastHouse);
				lastPerson = null; // reset last person
			}
			// Add to house
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
					lastPerson = (Person) getPerson(args);
					lastHouse.addPerson(lastPerson);
					// Keep track of created object for storage
					objRef = lastPerson; 
				}
				// Handle addition of tasks (assume non object is a Task)
				else if (lastPerson != null || strRef != null) {
					// Create task targeting last house
					args = createTaskArguments(strClass, lastHouse);
					
					// Determine person task should be added to
					Person taskPerson = null;	
					// Then check if object lookup returns a Person
					if (line.contains(GET_LABEL)) {
						if (strRef != null) {
							// Check if object lookup returns a person, casting as appropriate
							objRef = getReference(objRefs, strRef);	
							if (objRef instanceof Person) {
								taskPerson = (Person) objRef;
							}
							else {
								Logger.error(String.format("Object is not of type '%s'", type));
							}						
						}
						else {
							Logger.error(String.format("Cannot get object of type '%s'", type));
						}
						// If person is not a member of target house, add them so timePasses runs
						// tasks on the target house
						if (!lastHouse.isPersonInHouse(taskPerson)) {
							lastHouse.addPerson(taskPerson);
						}
					}
					else {
						taskPerson = lastPerson;
					}
					// Add task to found person
					taskPerson.addTask(getTask(args));
				}
				else {
					Logger.error(String.format("'%s' Invalid class type or task added before person", type));
				}
			}
			else {
				Logger.error("House should be declared before any other declaration type");
			}
			
			// If line indicates created object should be stored
			if (line.contains(ASSIGN_LABEL)) {
				// Check if storable object was created
				if (objRef != null) {
					assignReference(objRefs, strRef, objRef);
				}
				else {
					Logger.error(String.format("Cannot store object of type '%s'", type));
				}
			}
			// If line indicated object should be retrieved for use but has not been used
			else if (line.contains(GET_LABEL) && objRef == null) {
				Logger.error(String.format("No object retrieval methods for type '%s'", type));
			}
				
		}
		// Return all created houses
		return houses;
	}
	
	/**
	 * Validate configuration line is a valid command
	 * @param line Line to search
	 * @return Matching pattern if found, else null
	 */
	private static boolean isValidLine(String line) {
		Pattern p = Pattern.compile(VALID_LINE_REGEX);
		Matcher m = p.matcher(line);
		// True if string is a whole match
		return m.matches();
	}
	
	/**
	 * Search the given string for a pattern, to extract the class part.
	 * @param line Line to search
	 * @return Matching pattern if found, else null
	 */
	private static String getClassString(String line) {
		Pattern p = Pattern.compile(CLASS_REGEX);
		Matcher m = p.matcher(line);
		// Return match anywhere in a string
		if (m.find()) {
			// Full string
			return m.group(0);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Search the given string for a pattern, to extract the object reference part.
	 * @param line Line to search
	 * @return Matching pattern if found, else null
	 */
	private static String getReferenceString(String line) {
		Pattern p = Pattern.compile(VAR_STORAGE_REGEX);
		Matcher m = p.matcher(line);
		// Return match anywhere in a string
		if (m.find()) {
			// String between '[' and ']'
			return m.group(2);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Gets class name and arguments and splits text by separator (:) 
	 * into a string array.
	 * @param  strClass Line to split
	 * @return  String array of split string
	 */
	private static String[] splitAsClass(String strClass) {
		String[] splitLine = strClass.split(String.valueOf(CLASS_SEP_LABEL));
		// Verify split was correct
		if (splitLine.length == 0 || splitLine.length > 2) {
			Logger.error("Line was not parsed correctly on house configuration");
		}
		return splitLine;
	}
	
	/**
	 * Get the type part of a line split.
	 * @param  line String to split holding class type and arguments
	 * @return  LHS of ':', equivalent to class type 
	 */
	private static String getTypeString(String strClass) {
		String[] splitLine = splitAsClass(strClass);
		if (splitLine.length >= 1) {
			return  splitLine[0];
		} 
		else {
			return null;
		}
	}
	
	/**
	 * Get the arguments part of a line split as an object array of arguments.
	 * @param  strClass String to split holding class type and arguments
	 * @return  RHS of ':', split again by ',' as an object array of arguments
	 */
	private static Object[] createClassArguments(String strClass) {
		String[] splitLine = splitAsClass(strClass);
		if (splitLine.length == 2) {
			// Split by argument separator
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
	private static Object[] createTaskArguments(String strClass, House targetHouse) {
		// --- Handle user parameters
		// Split by all configuration delimiters (: or ,)
		String[] splitLine = strClass.split(String.format("%s|%s", CLASS_SEP_LABEL,  ARG_SEP_LABEL));	

		// Check if parse is correct
		if (splitLine.length == 0) {
			Logger.error("Line was not parsed correctly on house configuration");
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
				// Check for explicit string declarators
				else if (args[i].charAt(0) == (STRING_LABEL) && args[i].charAt(args[i].length() - 1) == (STRING_LABEL)) {
					// Trim explicit string declarators
					objArgs[i] = args[i].substring(1, args[i].length() - 1);
				} 
				// Else assign as string directly
				else {
					objArgs[i] = args[i];
				}
			}
		}			
		return objArgs;
	}
	
	/**
	 * Add a new object and its reference to the HashMap of objects.
	 * @param  objRefs HashMap of objects and their unique references
	 * @param  strRef Reference string, must not exist in HashMap
	 * @param  objRef Object to store in HashMap
	 */
	private static void assignReference(HashMap<String, Object> objRefs, String strRef, Object objRef) {
		if (objRefs.containsKey(strRef)) {
			Logger.error(String.format("Object reference '%s' already exists", strRef));
		}
		else {
			objRefs.put(strRef, objRef);
		}
	}
	
	/**
	 * Gets an object from the HashMap of objects using it's unique reference
	 * @param  objRefs HashMap of objects and their unique references
	 * @param  strRef Reference string to search for in HashMap
	 * @return  The object from HashMap with the respective key
	 */
	private static Object getReference(HashMap<String, Object> objectRefs, String strRef) {
		// Will be null if key does not exist
		Object objRef = objectRefs.get(strRef);
		if (objRef == null) {
			Logger.error(String.format("Object reference '%s' not found", strRef));
		}
		return objRef;
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
		// However, as only class constructors know argument order (allowance), the correct class type
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
				
				// If types are incompatible
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