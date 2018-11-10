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
public class PlantTest implements Runnable{
	private static Logger log = Logger.getLogger(PlantTest.class.getName());
	
	//log file routine
	private String logBase = "src/edu/neu/csye6200/bg/server.log";
	
	public PlantTest(){
		try {
			log.info("Constructing a PlantTest instance");
			Handler handler = new FileHandler(logBase);
			Logger.getLogger("").addHandler(handler);
			
		} catch (SecurityException e) {
			log.warning("SecurityException occurs in constructor PlantTest");
			e.printStackTrace();
		} catch (IOException e) {
			log.warning("IOException occurs in constructor PlantTest");
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
		roster.addPlant(plant2.getSpecimenID(), plant2);
		roster.addPlant(plant3.getSpecimenID(), plant3);


		//print basic plant infomation	
		roster.displayPlant();
			
		/*
		 * growth plant the parameter is the age of growth 
		 * I move the method save(Plantpt, String src) into growPlant method 
		 * every time it grow a plant, it does a save operate
		 */
		roster.growPlant(6);
			
		//print plant info after load
		roster.displayPlant();

	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PlantTest pt = new PlantTest();
		pt.run();
	}
}
