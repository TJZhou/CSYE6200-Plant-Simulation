package edu.neu.csye6200.bg;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class BGRule {

	//	private BGStem baseStem;
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
		int ID = baseStem.getStemID();

		/*
		 * * Create stems, and the first two stems are based on the baseStem Parameters
		 * here are able to change and adapt It's able to increase the value of age to
		 * add more stems
		 */

		stem = new BGStem(0, baseLength, baseLength / 1.3, Math.PI / 2 + rotateRadian);
		stem = new BGStem(0, baseLength, baseLength / 1.3, Math.PI / 2 - rotateRadian);
		if (generation >= 2) {
			// Let every stem has two child stems
			for (int i = ID + 3; i < ID + Math.pow(2, generation); i++) {
				// stems grow to left side
				if (i % 2 == 1) {				
					// get the last stem's radians, location x and y, length; in orede to create new stem
					presentStem((i - ID - 1) / 2 + ID);					
					stem = new BGStem(
							// locationX
							(X + length * Math.cos(radians)),
							// locationY
							(Y + length * Math.sin(radians)),
							// length
							(baseLength / Math.pow(1.5, ((int) (Math.log(i - ID + 1) / Math.log(2))))),
							// radians
							radians + rotateRadian);
				}
				// stems grow to right side
				else if (i % 2 == 0) {					
					presentStem((i - ID - 2) /2 +ID);
					stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
							(baseLength / Math.pow(1.5, ((int) (Math.log(i - ID + 1) / Math.log(2))))),
							radians - rotateRadian);
				}
			}
		}
	}
	
	public void growthRule2(int generation, double baseLength, double rotateRadian, BGStem baseStem) {
		// in order to calculate radians, LocationX/Y, length later
				double radians, X, Y, length;
				int ID = baseStem.getStemID();

				/*
				 * * Create stems, and the first two stems are based on the baseStem Parameters
				 * here are able to change and adapt It's able to increase the value of age to
				 * add more stems
				 */

				stem = new BGStem(0, baseLength, baseLength / 1.3, Math.PI / 2 + rotateRadian);
				stem = new BGStem(0, baseLength, baseLength / 1.3, Math.PI / 2);
				stem = new BGStem(0, baseLength, baseLength / 1.3, Math.PI / 2 - rotateRadian);
				if (generation >= 3) {
					// Let every stem has two child stems
					for (int i = ID + 3; i < ID + Math.pow(2, generation); i++) {

						// stems grow to left side
						if (i % 2 == 2) {
							// get the last stem's radians, location x and y, length; in
							// orede to create new stem
							radians = BGStem.getFromHashMap((i - ID - 1) / 2 + ID).getRadians();
							X = BGStem.getFromHashMap((i - ID - 1) / 2 + ID).getLocationX();
							Y = BGStem.getFromHashMap((i - ID - 1) / 2 + ID).getLocationY();
							length = BGStem.getFromHashMap((i - ID - 1) / 2 + ID).getLength();
							stem = new BGStem(
									// locationX
									(X + length * Math.cos(radians)),
									// locationY
									(Y + length * Math.sin(radians)),
									// length
									(baseLength / Math.pow(1.5, ((int) (Math.log(i - ID + 1) / Math.log(2))))),
									// radians
									radians + rotateRadian);
						}
						
						//stems grow to the middle
						if (i % 2 == 1) {
							// get the last stem's radians, location x and y, length; in
							// orede to create new stem
							radians = BGStem.getFromHashMap((i - ID - 1) / 2 + ID).getRadians();
							X = BGStem.getFromHashMap((i - ID - 1) / 2 + ID).getLocationX();
							Y = BGStem.getFromHashMap((i - ID - 1) / 2 + ID).getLocationY();
							length = BGStem.getFromHashMap((i - ID - 1) / 2 + ID).getLength();
							stem = new BGStem(
									// locationX
									(X + length * Math.cos(radians)),
									// locationY
									(Y + length * Math.sin(radians)),
									// length
									(baseLength / Math.pow(1.5, ((int) (Math.log(i - ID + 1) / Math.log(2))))),
									// radians
									radians + rotateRadian);
						}

						// stems grow to right side
						else if (i % 2 == 0) {
							radians = BGStem.getFromHashMap((i - ID - 2) / 2 + ID).getRadians();
							X = BGStem.getFromHashMap((i - ID - 2) / 2 + ID).getLocationX();
							Y = BGStem.getFromHashMap((i - ID - 2) / 2 + ID).getLocationY();
							length = BGStem.getFromHashMap((i - ID - 2) / 2 + ID).getLength();
							stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
									(baseLength / Math.pow(1.5, ((int) (Math.log(i - ID + 1) / Math.log(2))))),
									radians - rotateRadian);
						}
					}
				}
	}
}
