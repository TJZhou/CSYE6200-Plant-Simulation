package edu.neu.csye6200.bg;

import java.util.ArrayList;

public class BGGeneration {

	private BGRule bgr = new BGRule();	
	private BGStem stem;
	private ArrayList<BGStem> bgs = new ArrayList<BGStem>();

	// in order to calculate radians, LocationX/Y, length later
	double radians, X, Y, length;

	public BGGeneration() {

	}



	public void growthRule(String rule) {

		//baseStem
		bgs.add(new BGStem(0, 0, 100, Math.PI / 2));

		if (rule.equals("rule1")) {
			for (int i = 1; i <= Math.pow(2, PlantSimUI.generation + 1) - 1; i++) {
				stem = bgr.growthRule(PlantSimUI.generation, PlantSimUI.sideLengthGrow, PlantSimUI.rotateRadian, bgs, i);
				bgs.add(stem);
			}
		}

		else if (rule.equals("rule2")) {
			for (int i = 1; i < ((Math.pow(3, PlantSimUI.generation) - 1) * 3 / 2) + 1; i++) {
				stem = bgr.growthRule(PlantSimUI.generation, PlantSimUI.sideLengthGrow, PlantSimUI.midLengthGrow,
						PlantSimUI.rotateRadian, bgs,i);
				bgs.add(stem);
			}
		}
	}
	public ArrayList<BGStem> getBgs() {
		return bgs;
	}
}
