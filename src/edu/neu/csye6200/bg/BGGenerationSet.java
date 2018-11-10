package edu.neu.csye6200.bg;

public class BGGenerationSet {

	private int gen;	//growth generation
	
	public BGGenerationSet(int generation) {
		setGen(generation);
	}

	public int getGen() {
		return gen;
	}

	public void setGen(int gen) {
		this.gen = gen;
	}	
	
	/*
	 * ----------------The Generation Set------------------------
	 */
	public void genrationSet(String plantName, BGStem baseStem) {

		BGRule bg = new BGRule();
		
		// different plant should have different growth patterns
		// here, assume rose has the same growth patterns as maple
		switch (plantName) {
		
		
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
			bg.growthRule1(getGen(), 1.05, Math.PI / 6, baseStem);
			break;

		case "rule2":
			bg.growthRule2(getGen(), 1.05,1.05, Math.PI / 10, baseStem);
			break;

/*		case "CamphorTree":
			bg.growthRule2(6, 1.05,1.05, Math.PI / 10, baseStem);
			break;*/
		}
	}
}
