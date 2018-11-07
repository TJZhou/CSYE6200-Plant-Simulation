package edu.neu.csye6200.bg;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Tianju Zhou NUID 001420546
 *
 */
public class PlantRoster {
	private static Logger log = Logger.getLogger(PlantRoster.class.getName());
	
	private static PlantRoster instance = null; // the single copy
	
	private HashMap<Integer, Plant> plantMap = new HashMap<Integer, Plant>();	//plant list
	
	private Plant[] plantArray;	//in order to sort all plants
	
	private RegistryIO IO = new RegistryIO();	//an instance of RegistryIO to do file operation
	
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
		System.out.println("This is the information about all plants");
		System.out.println(String.format("%1$-16s %2$-16s %3$-16s %4$-16s %5$-16s", "SpecimenID", "Plant Name", "Age(Year)",
				"Length(cm)", "Width(cm)"));		
		for (Plant pt : plantMap.values()) 
			System.out.println(pt.toString());
		System.out.println("---------------------------------------------------------------------------------");
	}
	
	public void growPlant(int age){	
		/*
		 * let the plant grow and output the information of the plant
		 * grow 4 years (the age parameter is in plantGrowth method) 
		 * 
		 */
		for (Plant pt : plantMap.values()) {
			pt.plantGrowth(age);
		}
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
	
	//save plants to "src/edu/neu/csye6200/sim/PlantData.txt"
	public void save(String saveBase){
		IO.saveAll(plantMap, saveBase);		
	}

	// load plants data from "src/edu/neu/csye6200/sim/PlantData.txt"
	public void load(String saveBase) {
		IO.load(plantMap, saveBase);
	}
	
	// get the plantArray
	public Plant[] getPlantArray() {
		return plantArray;
	}

	//use quickSort to sort plant by ID
	public void quickSort(){
		int i = 0;
		
		//initialize the plantArray, the length of plantArray equals the size of plantMap
		plantArray = new Plant[plantMap.size()];		
		for (Plant pt : plantMap.values()) {		
			plantArray[i++] = pt;
		}
		
		//call quick sort method
		qs(plantArray, 0, plantArray.length - 1);	
	}

	/**
	 * A recursive version of Quicksort.
	 * To sort plants by their specimen ID 
	 * @param plantArray
	 * @param left
	 * @param right
	 */
	private static void qs(Plant[] plantArray, int left, int right) {	
		int i, j;
		int x;    //middle number of specimenID
		Plant y;  //in order to change the location of different plant in the array
		i = left;
		j = right;
		x = plantArray[(left+right)/2].getSpecimenID();
		do {
			while ((plantArray[i].getSpecimenID() < x) && (i < right))
				i++;
			while ((x < plantArray[j].getSpecimenID()) && (j > left))
				j--;
			if (i <= j) {
				y = plantArray[i];
				plantArray[i] = plantArray[j];
				plantArray[j] = y;
				i++;
				j--;
			}
		} while (i <= j);
		if (left < j)
			qs(plantArray, left, j);
		if (i < right)
			qs(plantArray, i, right);
	}	
}
