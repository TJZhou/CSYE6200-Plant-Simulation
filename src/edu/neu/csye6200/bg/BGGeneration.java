package edu.neu.csye6200.bg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import edu.neu.csye6200.ui.*;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class BGGeneration {

	private BGRule bgr = new BGRule();

	private static Logger log = Logger.getLogger(BGGeneration.class.getName());

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
		bgStemSet.add(new BGStem(0, 0, 120, Math.PI / 2));

		// rule1 grow to two side
		if (rule.equals("rule1")) {
			// the stem numbers are depended on generation
			for (int i = 1; i < (Math.pow(2, BGApp.generation) * 2 - 1); i++) {
				bgStem = bgr.growthRule(BGApp.generation, BGApp.sideLengthGrow, BGApp.sideRotateRadian,
						bgStemSet, i);
				bgStemSet.add(bgStem);
			}
		}

		// rule2 grow to three side
		else if (rule.equals("rule2")) {
			for (int i = 1; i < ((Math.pow(3, BGApp.generation) * 3 / 2) - 1); i++) {
				bgStem = bgr.growthRule(BGApp.generation, BGApp.sideLengthGrow, BGApp.midLengthGrow,
						BGApp.sideRotateRadian, bgStemSet, i);
				bgStemSet.add(bgStem);
			}
		}

		// rule3 grow to four side
		else if (rule.equals("rule3")) {
			for (int i = 1; i < ((Math.pow(4, BGApp.generation) * 4 / 3) - 1); i++) {
				bgStem = bgr.growthRule(BGApp.generation, BGApp.sideLengthGrow, BGApp.midLengthGrow,
						BGApp.sideRotateRadian, BGApp.midRotateRadian, bgStemSet, i);
				bgStemSet.add(bgStem);
			}
		}

		saveStemInfo();

	}

	// save stem information in the file stemData.txt
	private void saveStemInfo() {
		// try-with-resources
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/stemData.txt"))) {
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
			log.warning("an error occurs when saving stem info - " + e);
		}

	}

	public ArrayList<BGStem> getBgs() {
		return bgStemSet;
	}
}
