package edu.neu.csye6200.bg;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class PlantTest {
	private static Logger log = Logger.getLogger(PlantTest.class.getName());
	
	//data save base routine
	private String saveBase = "src/edu/neu/csye6200/bg/PlantData.txt" ;
	
	//log file routine
	private String logBase = "src/edu/neu/csye6200/bg/server.log";
	
	public PlantTest(){
		try {
			log.info("Constructing a PlantTest instance");
			/*Handler handler = new FileHandler(logBase);
			Logger.getLogger("").addHandler(handler);*/
			
		} catch (SecurityException e) {
			log.warning("SecurityException occurs in constructor PlantTest");
			e.printStackTrace();
		}	
	}

	// create 3 different plant
	private Plant plant1 = new Plant("Maple");
	private Plant plant2 = new Plant("PhoenixTree");
	private Plant plant3 = new Plant("CamphorTree");

		
	public void run() {
		
		PlantRoster roster = PlantRoster.instance();
		
		//add plant to the singleton instance roster
		roster.addPlant(plant1.getSpecimenID(), plant1);
		/*roster.addPlant(plant2.getSpecimenID(), plant2);
		roster.addPlant(plant3.getSpecimenID(), plant3);*/


		//print basic plant infomation	
		roster.displayPlant();
		
		//growth plant the parameter is the age of growth
		roster.growPlant(10);
		
		//print their child stems
		roster.printChild();
		
		//clear file before save and load
		clearFile();			
		
		//saveAll plant and flowerPlant to the file PlantData
		roster.save(saveBase);
		
		//load all PlantData from sabeBase file to loadBase file
		roster.load(saveBase);
		
		//IO.load(saveBase, plantMap);
		System.out.println("succeed to load plant data");
		
		//print plant info after load
		roster.displayPlant();
		
		//using quick sort to sort the HashMap plantMap
		roster.quickSort();
		
		//plant sorted by ID
		System.out.println("plant infomation after quick sort:");
		for(Plant pt : roster.getPlantArray()){
			System.out.println(pt.toString());
		}
	}

	private void clearFile(){
		// delete all the existing info and then do the rewrite
		try {
			FileOutputStream clearFile = new FileOutputStream(saveBase);
			clearFile.write(new String("").getBytes());
			clearFile.close();
		} catch (FileNotFoundException e) { // File cannot be found
			e.printStackTrace();
		} catch (IOException e) { // All other IO problem
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PlantTest pt = new PlantTest();
		pt.run();
	}
}
