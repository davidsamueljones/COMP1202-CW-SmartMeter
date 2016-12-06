/**
 * Enumerated type that represents different utility types.
 * Further functionality added so enumerated types can be got as a string
 * or as a meter type.
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public enum UtilityType {
    // Declare enumeration types
	ELECTRIC("Electric"),
    WATER("Water"),
    GAS("Gas");

	// Enumeration properties
    private String stringType; // as a string type
    private String meterType; // as a meter type

    /**
     * Constructor for UtilityType enumeration constructor.
     * @param  stringType Utility type as a string
     */
    UtilityType(String stringType) {
        this.stringType = stringType;
        this.meterType = stringType + "Meter";
    }

    /**
     * @return  Returns the value of stringType
     */
    public String asString() {
        return stringType;
    }

    /**
     * @return  Returns the value of meterType 
     */
    public String asMeterString() {
        return meterType;
    }

    /**
     * @return  Array of all UtilityTypes as Meter strings
     */
    public static String[] asMeterStringArray() {
    	// Create array of strings of equal length to number of UtilityTypes
    	UtilityType[] types = UtilityType.values();
    	String[] strTypes = new String[types.length];
        
        // Copy UtilityType as meter strings to string array
        for (int i = 0; i < types.length; i++) {
        	strTypes[i] = types[i].asMeterString();
        }
        
        return strTypes;
    }

}