package edu.neu.csye6200.bg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class BGGeneration {

	private BGRule bgr = new BGRule();

	private static Logger log = Logger.getLogger(PlantSimUI.class.getName());

	// bgStem and the container to collect it
	private BGStem bgStem;
	private ArrayList<BGStem> bgStemSet = new ArrayList<BGStem>();

	// in order to calculate radians, LocationX/Y, length later
	double radians, X, Y, length;

	public BGGeneration() {
		log.info("An instance of BGGenration is created");
	}

	public void growthRule(String rule) {

		// create baseStem
		bgStemSet.add(new BGStem(0, 0, 100, Math.PI / 2));

		// rule1 grow to two side
		if (rule.equals("rule1")) {
			// the stem numbers are depended on generation
			for (int i = 1; i <= Math.pow(2, PlantSimUI.generation + 1) - 1; i++) {
				bgStem = bgr.growthRule(PlantSimUI.generation, PlantSimUI.sideLengthGrow, PlantSimUI.rotateRadian,
						bgStemSet, i);
				bgStemSet.add(bgStem);
			}
		}

		// rule2 grow to three side
		else if (rule.equals("rule2")) {
			for (int i = 1; i < ((Math.pow(3, PlantSimUI.generation) - 1) * 3 / 2) + 1; i++) {
				bgStem = bgr.growthRule(PlantSimUI.generation, PlantSimUI.sideLengthGrow, PlantSimUI.midLengthGrow,
						PlantSimUI.rotateRadian, bgStemSet, i);
				bgStemSet.add(bgStem);
			}
		}

		saveStemInfo();

	}

	// save stem information in the file stemData.txt
	private void saveStemInfo() {
		// try-with-resources
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/edu/neu/csye6200/bg/stemData.txt"))) {
			bw.write(String.format("%1$-16s %2$-16s %3$-16s %4$-16s %5$-16s", "stemID", "locationX", "locationY",
					"length", "radians"));
			bw.write('\n');
			//for each stem in the bgStemSet array
			for (BGStem stem : bgStemSet) {
				bw.write(stem.toString());
				bw.write('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<BGStem> getBgs() {
		return bgStemSet;
	}
}
