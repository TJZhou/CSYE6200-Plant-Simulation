package edu.neu.csye6200.bg;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class BGGenerationSet {
	
	//BGGeneration and the container to collect it
	private BGGeneration bg = new BGGeneration();
	private ArrayList<BGGeneration> bgSet = new ArrayList<BGGeneration>();
	
	private static Logger log = Logger.getLogger(PlantSimUI.class.getName());	
	
	//constructor
	public BGGenerationSet() {
		log.info("An instance of BGGenrationSet is created");
	}

	/*
	 * ----------------The Generation Set------------------------
	 */
	public void genrationSet(String rule) {
		
		
		// different plant should have different growth patterns
		// here, assume rose has the same growth patterns as maple
		switch (rule) {
		
		
		case "rule1":
			// locationX locationY length radians
			/*
			 * Able to grow by each generation
			 * bg.growthRule1(1, 100, Math.PI / 6, baseStem); 
			 * bg.growthRule1(2, 100, Math.PI / 6, baseStem); 
			 * bg.growthRule1(3, 100, Math.PI / 6, baseStem);
			 * bg.growthRule1(4, 100, Math.PI / 6, baseStem); 
			 * bg.growthRule1(5, 100, Math.PI / 6, baseStem); 
			 * bg.growthRule1(6, 100, Math.PI / 6, baseStem);
			 */		
			bg.growthRule("rule1");
			break;

		case "rule2":
			bg.growthRule("rule2");
			break;
			
		case "rule3":
			bg.growthRule("rule3");
			break;		
		}
		bgSet.add(bg);
	}

	public ArrayList<BGGeneration> getBgSet() {
		return bgSet;
	}
}
