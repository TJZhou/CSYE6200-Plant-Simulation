package edu.neu.csye6200.bg;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class BGGenerationSet {
	
	//constructor
	public BGGenerationSet() {
		
	}

	/*
	 * ----------------The Generation Set------------------------
	 */
	public void genrationSet(BGStem baseStem) {

		BGRule bg = new BGRule();
		
		// different plant should have different growth patterns
		// here, assume rose has the same growth patterns as maple
		switch (PlantSimUI.rule) {
		
		
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
			bg.growthRule1(PlantSimUI.generation, PlantSimUI.sideLengthGrow, PlantSimUI.rotateRadian , baseStem);
			break;

		case "rule2":
			bg.growthRule2(PlantSimUI.generation, PlantSimUI.sideLengthGrow, PlantSimUI.midLengthGrow, PlantSimUI.rotateRadian, baseStem);
			break;
			
		case "rule3":
			break;
		}
	}
}
