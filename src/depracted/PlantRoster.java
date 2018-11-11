package depracted;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class PlantRoster {
	
	private static Logger log = Logger.getLogger(PlantRoster.class.getName());	
	private static PlantRoster instance = null; // the single copy	
	private HashMap<Integer, Plant> plantMap = new HashMap<Integer, Plant>();	//plant list	
	private RegistryIO IO = new RegistryIO();	//an instance of RegistryIO to do file operation
	private String saveBase = "src/edu/neu/csye6200/bg/PlantData.txt" ; //data save base routine
	
	private PlantRoster(){	// can't be built externally to class
		log.info("Constructing a PlantRoster instance");
	}
	
	public static PlantRoster instance(){
		if(instance == null)
			instance = new PlantRoster(); //build if needed
		return (instance);	//return the single copy
	}
	
	public void addPlant(int key, Plant pt){
		plantMap.put(key, pt);
	}
	
	//print the information of every plant
	public void displayPlant(){
		System.out.println("This is the information about the plants");
		System.out.println(String.format("%1$-16s %2$-16s %3$-16s %4$-16s %5$-16s %6$-16s", "SpecimenID", "Plant Name", "Age(Year)",
				"Length(cm)", "Width(cm)", "stemNumbers"));		
		for (Plant pt : plantMap.values()) 
			System.out.println(pt.toString());
		System.out.println("---------------------------------------------------------------------------------");
	}
	
	public void growPlant(Plant pt, String rule){	
		/*
		 * let the plant grow and output the information of the plant
		 * grow 4 years (the age parameter is in plantGrowth method) 
		 */
		
		// clear file before save and load
		clearFile();
		pt.plantGrowth(rule);
		save(pt, saveBase);
	}
	
	//print child stems and flowers of every plant	
	public void printChild() {
		for (Plant pt : plantMap.values()) {
			System.out.println("This is all stems of " + pt.getPlantName() + ";" + "\nThe total number of stems is:"
					+ pt.childStemNumbers());
			System.out.println("The total height of " + pt.getPlantName() + " is "
					+ String.format("%1$-5.2f", pt.plantHeight()) + "(cm)" + "\nThe total width of " + pt.getPlantName()
					+ " is " + String.format("%1$-5.2f", pt.plantWidth()) + "(cm)");
			System.out.println(pt.printChildStem());
			System.out.println("---------------------------------------------------------------------------------");		
		}
	}
	
	// delete all the existing info and then do the rewrite
	private void clearFile(){
	
		try (FileOutputStream clearFile = new FileOutputStream(saveBase);){			
			clearFile.write(new String("").getBytes());
		} catch (FileNotFoundException e) { // File cannot be found
			e.printStackTrace();
		} catch (IOException e) { // All other IO problem
			e.printStackTrace();
		}
	}
	
	//save plants to "src/edu/neu/csye6200/sim/PlantData.txt"
	public void save(Plant pt, String saveBase){
		IO.save(pt, saveBase);		
	}
}
