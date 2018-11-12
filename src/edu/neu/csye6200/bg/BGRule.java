package edu.neu.csye6200.bg;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class BGRule {

	private static Logger log = Logger.getLogger(PlantSimUI.class.getName());	
	
	// private BGStem baseStem;
	private BGStem stem;
	
	// in order to calculate radians, LocationX/Y, length later
	double radians, X, Y, length;

	public BGRule() {
		log.info("An instance of BGRule is created");
	}
	
	// the present stem info based on the last stem info
	private void presentStem(ArrayList<BGStem> bgs, int lastStemID) {
		radians = bgs.get(lastStemID).getRadians();
		X = bgs.get(lastStemID).getLocationX();
		Y = bgs.get(lastStemID).getLocationY();
		length = bgs.get(lastStemID).getLength();
	}
 
	/**  method to growth: rule1-grow two sides
	 * 
	 * 		   * Create stems, and the last two stems are based on the first StemP
	 *         * parameters here are able to change and adapt in PlantSimUi
	 *         * It's able to increase the value of generation to add more stems
	 * 
	 * @param generation: how much generations to grow
	 * @param lengthChange: in each generation, how much does the length change
	 * @param rotateRadian: in each generation, how much does the radian change
	 */
	public BGStem growthRule(int generation, double lengthGrow, double rotateRadian, ArrayList<BGStem> bgs, int i) {
		// Let every stem has two child stems
		// stems grow to left side
		if (i % 2 == 1) {
			// get the last stem's radians, location x and y, length
			// in orede to create new stem
			presentStem(bgs, (i - 1) / 2);
			stem = new BGStem(
					// locationX
					(X + length * Math.cos(radians)),
					// locationY
					(Y + length * Math.sin(radians)),
					// to calculate the length
					(length / Math.pow(lengthGrow, ((int) (Math.log(i + 1) / Math.log(2))))),
					// radians
					radians + rotateRadian);
		}
		// stems grow to right side
		else if (i % 2 == 0) {
			presentStem(bgs, (i - 2) / 2);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / Math.pow(lengthGrow, ((int) (Math.log(i + 1) / Math.log(2))))), radians - rotateRadian);
		}
		return stem;
	}
	
	/** method to growth: rule2-grow three sides
	 * 
	 * @param generation: how much generations to grow
	 * @param midLengthChange: in each generation, how much does the mid length change
	 * @param sideLengthChange: in each generation, how much does the side (left and right) length change
	 * @param rotateRadian: in each generation, how much does the radian change
	 */
	public BGStem growthRule(int generation, double sideLengthGrow, double midLengthGrow, double rotateRadian, ArrayList<BGStem> bgs, int i) {

		// stems grow to left side
		if (i % 3 == 1) {
			presentStem(bgs, (i - 1) / 3);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / Math.pow(sideLengthGrow, (int) (Math.log(2 * i + 1) / Math.log(3) - 1))),
					radians + rotateRadian);
		}

		// stems grow to the middle
		else if (i % 3 == 2) {
			// in orede to create new stem
			presentStem(bgs, (i - 2) / 3);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / Math.pow(midLengthGrow, (int) (Math.log(2 * i + 1) / Math.log(3) - 1))), radians);
		}

		// stems grow to right side
		else if (i % 3 == 0) {
			presentStem(bgs, (i - 3) / 3);

			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / Math.pow(sideLengthGrow, (int) (Math.log(2 * i + 1) / Math.log(3) - 1))),
					radians - rotateRadian);
		}
		return stem;
	}
}
