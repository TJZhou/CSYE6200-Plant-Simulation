package edu.neu.csye6200.bg;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class PlantTest implements Runnable{
	
	private static Logger log = Logger.getLogger(PlantTest.class.getName());

	// create 3 different plant
	private Plant plant = new Plant("plant");

	//log file routine
	private String logBase = "src/edu/neu/csye6200/bg/server.log";
	
	// constructor
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

	//implement runnable
	public void run() {
		
		PlantRoster roster = PlantRoster.instance();
		
		//add plant to the singleton instance roster
		roster.addPlant(plant.getSpecimenID(), plant);

		//print basic plant infomation	
		roster.displayPlant();
			
		/*
		 * growth plant the parameter is the age of growth 
		 * I move the method save(Plantpt, String src) into growPlant method 
		 * every time it grow a plant, it does a save operate
		 */
		
		//can change the grow generation 7 in the GUI
		roster.growPlant(plant, PlantSimUI.rule);
			
		//print plant info after growth
		System.out.println("After growth");
		roster.displayPlant();

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Thread thread = new Thread(new PlantTest()); // create a thread
		//pt.run();
		
		thread.start();
	}
}
