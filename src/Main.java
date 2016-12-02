/**
 * Main test class
 * 
 * ECS Smart Meter - COMP1202 Coursework
 * @author dsj1n15
 */
public class Main {

	/**
	 * Test Main
	 * @param  args No arguments
	 */
	public static void main(String[] args) {
		Logger.deleteExistingLog();
		// Tasks completed in order of person added
		ElectricMeter em = new ElectricMeter(0,false);
		GasMeter gm = new GasMeter(0);
		WaterMeter wm = new WaterMeter(0);
		
		House myHouse = new House();

		Boiler myBoiler = new Boiler();
		Dishwasher myDishwasher = new Dishwasher();
		ElectricCooker myElectricCooker = new ElectricCooker();
		ElectricShower myElectricShower = new ElectricShower();
		GasCooker myGasCooker = new GasCooker();
		Kettle myKettle = new Kettle();
		NightLight myNightLight = new NightLight();
		PowerShower myPowerShower = new PowerShower();
		Refrigerator myRefrigerator = new Refrigerator();
		TV myTV = new TV();
		WashingMachine myWashingMachine = new WashingMachine();
		
		GrownUp myGrownUp = new GrownUp("Steven", 52, "M");		
		Child myChild = new Child("Richard", 12, "M");
		
		// Add people
		
		myHouse.addPerson(myGrownUp);
		myHouse.addPerson(myChild);

		// Connect appliances
		myHouse.addAppliance(myBoiler);
		myHouse.addAppliance(myGasCooker);
		myHouse.addAppliance(myTV);
		
		// Connect meters
		
		myHouse.addMeter(em);
		myHouse.addMeter(gm);
		myHouse.addMeter(wm);
		
		myBoiler.addMeter(em);
		myBoiler.addMeter(gm);
		myBoiler.addMeter(wm);

		myGasCooker.addMeter(em);
		myGasCooker.addMeter(gm);
		myGasCooker.addMeter(wm);

		myTV.addMeter(em);
		myTV.addMeter(gm);
		myTV.addMeter(wm);
		
		myGrownUp.addTask(new PersonTask("TurnOnBoiler", 5, myHouse));
		myGrownUp.addTask(new PersonTask("TurnOffBoiler", 100, myHouse));
		myGrownUp.addTask(new PersonTask("Cook", 16, myHouse));

		myChild.addTask(new PersonTask("Cook", 16, myHouse));
		
		while (true) {
			myHouse.timePasses();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}	

	}

}
