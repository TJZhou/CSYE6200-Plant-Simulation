package edu.neu.csye6200.bg;

import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Logger;

import edu.neu.csye6200.ui.PlantSimUI;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class BGGenerationSet extends Observable{
	
	//BGGeneration and the container to collect it
	private BGGeneration bg;
	private static BGGenerationSet generationSet = null;
	private ArrayList<BGGeneration> bgSet = new ArrayList<BGGeneration>();
	
	private static Logger log = Logger.getLogger(PlantSimUI.class.getName());	
	
	//private constructor - singleton pattern
	private BGGenerationSet() {
		
	}

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

	/*
	 * ----------------The Generation Set------------------------
	 */
	public void genrationSet(String rule) {
		bg = new BGGeneration();
		
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
}
