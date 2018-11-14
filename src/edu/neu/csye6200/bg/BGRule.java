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
	private double radians, X, Y, length;
	private double lengthChange;
	
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
	 * 		   * Create stems, and the last two stems are based on the first Stem
	 *         * parameters here are able to change and adapt in PlantSimUi
	 *         * It's able to increase the value of generation to add more stems
	 * 
	 * @param generation: how much generations to grow
	 * @param lengthChange: in each generation, how much does the length change
	 * @param rotateRadian: in each generation, how much does the radian change
	 * @param bgs: is an arrayList of BGStem
	 * @param i: is the index in the bgs
	 */
	public BGStem growthRule(int generation, double lengthGrow, double rotateRadian, ArrayList<BGStem> bgs, int i) {
		lengthChange = Math.pow(lengthGrow, ((int) (Math.log(i + 1) / Math.log(2))));
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
					(length / lengthChange),
					// radians
					radians + rotateRadian);
		}
		// stems grow to right side
		else if (i % 2 == 0) {
			presentStem(bgs, (i - 2) / 2);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / lengthChange), radians - rotateRadian);
		}
		return stem;
	}
	
	/** method to growth: rule2-grow three sides
	 * 
	 * @param generation: how much generations to grow
	 * @param midLengthChange: in each generation, how much does the mid length change
	 * @param sideLengthChange: in each generation, how much does the side (left and right) length change
	 * @param rotateRadian: in each generation, how much does the radian change
	 * @param bgs: is an arrayList of BGStem
	 * @param i: is the index in the bgs
	 */
	public BGStem growthRule(int generation, double sideLengthGrow, double midLengthGrow, double rotateRadian, ArrayList<BGStem> bgs, int i) {

		lengthChange = Math.pow(sideLengthGrow, (int) (Math.log(2 * i + 1) / Math.log(3)));
		// stems grow to left side
		if (i % 3 == 1) {
			presentStem(bgs, (i - 1) / 3);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / lengthChange),
					radians + rotateRadian);
		}

		// stems grow to the middle
		else if (i % 3 == 2) {
			// in orede to create new stem
			presentStem(bgs, (i - 2) / 3);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / lengthChange), radians);
		}

		// stems grow to right side
		else if (i % 3 == 0) {
			presentStem(bgs, (i - 3) / 3);

			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / lengthChange),
					radians - rotateRadian);
		}
		return stem;
	}
	
	/** method to growth: rule3-grow four sides
	 * 
	 * @param generation: how much generations to grow
	 * @param midLengthChange: in each generation, how much does the length of two mid stem change
	 * @param sideLengthChange: in each generation, how much does the side (left and right) length change
	 * @param midRotateRadian: in each generation, how much does the radian of two mid stem rotate
	 * @param sideRotateRadian: in each generation, how much does the radian of two side stem rotate
	 * @param bgs: is an arrayList of BGStem
	 * @param i: is the index in the bgs
	 */
	
	public BGStem growthRule(int generation, double sideLengthGrow, double midLengthGrow, double sideRotateRadian, double midRotateRadian, ArrayList<BGStem> bgs, int i) {
		double sideLengthChange = Math.pow(sideLengthGrow, (int) (Math.log(3 * i + 1) / Math.log(4)));
		double midLengthChange = Math.pow(midLengthGrow, (int) (Math.log(3 * i + 1) / Math.log(4)));
		
		// Let every stem has four child stems
		
		// stems grow to the most left side
		if (i % 4 == 1) {
			System.out.print("1");
			presentStem(bgs, (i - 1) / 4);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / sideLengthChange), radians + sideRotateRadian);
		}
		//stem grow to the mid-left side
		else if (i % 4 == 2) {
			System.out.print("2");
			presentStem(bgs, (i - 2) / 4);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / midLengthChange), radians + midRotateRadian);
		}
		//stem grow to the mid-right side
		else if (i % 4 == 3) {
			System.out.print("3");
			presentStem(bgs, (i - 3) / 4);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / midLengthChange), radians - midRotateRadian);
		}
		// stems grow to the most right side
		else if (i % 4 == 0) {
			System.out.print("4");
			presentStem(bgs, (i - 4) / 4);
			stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
					(length / sideLengthChange), radians - sideRotateRadian);
		}
		return stem;
	}
}
