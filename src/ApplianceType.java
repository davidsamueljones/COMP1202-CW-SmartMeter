/**
 * Enumerated type that represents different appliance types.
 * Further functionality added so enumerated types can be got as a readable
 * word/phrase or as a class type.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public enum ApplianceType {
    // Declare enumeration types
	BOILER("Boiler"),
    DISHWASHER("Dishwasher"),
    ELECTRIC_COOKER("Electric cooker"),
	ELECTRIC_SHOWER("Electric shower"),
	GAS_COOKER("Gas cooker"),
	KETTLE("Kettle"),
	NIGHT_LIGHT("Night light"),
	POWER_SHOWER("Power shower"),
	REFRIGERATOR("Refrigerator"),
	TV("TV"),
	WASHING_MACHINE("Washing machine");
	
	// Enumeration properties
    private String stringType; // as a string type

    /**
     * Constructor for ApplianceType enumeration constructor.
     * @param  Appliance type as a string
     */
    ApplianceType(String stringType) {
        this.stringType = stringType;
    }

    /**
     * @return Returns the value of stringType
     */
    public String asString() {
        return stringType;
    }

    /**
     * @return Returns the value of string type with words capatalised and spaces removed
     */
    public String asClassString() {
        return toCamelCase(stringType);
    }
    
    /**
     * Converts a given string to camel case, e.g. Washing machine -> WashingMachine
     * @param normalCase  String to convert to camel case
     * @return Camel case version of provided string
     */
    private static String toCamelCase(String normalCase) {
    	StringBuilder camelCase = new StringBuilder(normalCase.length());
    	// Loop through each word
    	for (String word : normalCase.split(" ")) {
    		if (word.length() > 0) {
    			camelCase.append(word.substring(0, 1).toUpperCase());
    			camelCase.append(word.substring(1, word.length()));
    		}
    	}
    	return camelCase.toString();	
    }

    /**
     * @return Array of all ApplianceTypes as Class strings
     */
    public static String[] asClassStringArray() {
    	// Create array of strings of equal length to number of ApplianceTypes
    	ApplianceType[] types = ApplianceType.values();
    	String[] strTypes = new String[types.length];
        
        // Copy ApplianceType as class strings to string array
        for (int i = 0; i < types.length; i++) {
        	strTypes[i] = types[i].asClassString();
        }
        
        return strTypes;
    }

}