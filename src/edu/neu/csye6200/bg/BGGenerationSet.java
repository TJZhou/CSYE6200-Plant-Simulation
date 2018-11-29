package edu.neu.csye6200.bg;

import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * generationSet: hold different BGGeneration
 * 
 * @author Tianju Zhou NUID 001420546
 */
@SuppressWarnings("deprecation")
public class BGGenerationSet extends Observable{
	
	// BGGeneration and the container to collect it
	private BGGeneration bg;
	private static BGGenerationSet generationSet = null;
	private ArrayList<BGGeneration> bgSet = new ArrayList<BGGeneration>();
	
	private static Logger log = Logger.getLogger(BGGenerationSet.class.getName());	
	
	// private constructor - singleton pattern
	private BGGenerationSet() {
		
	}

	// only able to create one instance
	public static BGGenerationSet generationSet() {
		if(generationSet == null) {
			log.info("An instance of BGGenrationSet is created");
			generationSet = new BGGenerationSet();
		}			
		return generationSet;
	}
	
	public ArrayList<BGGeneration> getBgSet() {
		return bgSet;
	}

	/**
	 * -------------------------The Generation Set---------------------------
	 * 
	 * @param rule: has rule1, rule2 and rule3. growthRule depends on rules
	 */
	public void genrationSet(String rule) {
		bg = new BGGeneration();
		
		// different plant should have different growth patterns
		switch (rule) {
		
		case "rule1":
			bg.growthRule("rule1"); break;

		case "rule2":
			bg.growthRule("rule2"); break;
			
		case "rule3":
			bg.growthRule("rule3"); break;		
		}
		
		//add all bgGeneration to the index 0; do not need to hold multiply generation
		bgSet.add(0,bg);
		setChanged();	//Indicate that a change has happened
		notifyObservers(new String("A message"));
	}

}
