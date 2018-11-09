package edu.neu.csye6200.bg;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class BGRule {

	// private BGStem baseStem;
	private BGStem stem;
	// in order to calculate radians, LocationX/Y, length later
	double radians, X, Y, length;

	private void presentStem(int lastStemID) {
		radians = BGStem.getFromHashMap(lastStemID).getRadians();
		X = BGStem.getFromHashMap(lastStemID).getLocationX();
		Y = BGStem.getFromHashMap(lastStemID).getLocationY();
		length = BGStem.getFromHashMap(lastStemID).getLength();
	}

	// method to growth
	public void growthRule1(int generation, double baseLength, double rotateRadian, BGStem baseStem) {

		/*
		 * * Create stems, and the first two stems are based on the baseStem Parameters
		 * here are able to change and adapt It's able to increase the value of age to
		 * add more stems
		 */

		if (generation >= 2) {
			// Let every stem has two child stems
			for (int i = 1; i <  Math.pow(2, generation+1); i++) {
				// stems grow to left side
				if (i % 2 == 1) {
					// get the last stem's radians, location x and y, length; in orede to create new
					// stem
					presentStem((i - 1) / 2);
					stem = new BGStem(
							// locationX
							(X + length * Math.cos(radians)),
							// locationY
							(Y + length * Math.sin(radians)),
							// to calculate the length
							(baseLength / Math.pow(1.2, ((int) (Math.log(i + 1) / Math.log(2))))),
							// radians
							radians + rotateRadian);
				}
				// stems grow to right side
				else if (i % 2 == 0) {
					presentStem((i - 2) / 2);
					stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
							(baseLength / Math.pow(1.2, ((int) (Math.log(i + 1) / Math.log(2))))),
							radians - rotateRadian);
				}
			}
		}
	}

	public void growthRule2(int generation, double baseLength, double rotateRadian, BGStem baseStem) {
		// in order to calculate radians, LocationX/Y, length later

		if (generation >= 2) {
			// Let every stem has two child stems
			for (int i = 1; i <  ((Math.pow(3, generation) - 1) * 3 / 2) + 1; i++) {

				// stems grow to left side
				if (i % 3 == 1) {
					presentStem((i  - 1) / 3 );
					stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
							(baseLength / Math.pow(1.5, (int)(Math.log((i) * 2 / 3 + 1) / Math.log(3)))),
							radians + rotateRadian);
				}

				// stems grow to the middle
				else if (i % 3 == 2) {
					// get the last stem's radians, location x and y, length; in
					// orede to create new stem
					presentStem((i  - 2) / 3 );
					stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
							(baseLength / Math.pow(1.5, (int)(Math.log((i) * 2 / 3 + 1) / Math.log(3)))),
							radians);
				}

				// stems grow to right side
				else if (i % 3 == 0) {
					presentStem((i - 3) / 3 );
					
					// some calculation is wrong here, at the end of each stem
					if((Math.log((i) * 2 / 3 + 1) / Math.log(3))%1 == 0) {
						stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
								(baseLength / Math.pow(1.5,(int)(Math.log((i-1) * 2 / 3 + 1) / Math.log(3)))),
								radians - rotateRadian);
					}
					else
						stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
							(baseLength / Math.pow(1.5,(int)(Math.log((i) * 2 / 3 + 1) / Math.log(3)))),
							radians - rotateRadian);
				}
			}
		}
	}
}
