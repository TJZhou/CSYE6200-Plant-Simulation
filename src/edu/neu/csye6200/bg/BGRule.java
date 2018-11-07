package edu.neu.csye6200.bg;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class BGRule {

//	private BGStem baseStem;
	private BGStem left;
	private BGStem stem;
	private BGStem right;
	private double radians, X, Y, length;
	
	private int depth = 0;
	
	public BGRule() {
		
	}
	
/*	public int growthPlant(BGStem baseStem) {
		radians = baseStem.getRadians();
		X = baseStem.getLocationX();
		Y = baseStem.getLocationY();
		length = baseStem.getLength();
		
		while(depth++<1) {
			left = new BGStem(X + length * Math.cos(radians), Y + length * Math.sin(radians), length/2, Math.PI/2+radians);
			growthPlant(left);
			mid = new BGStem(X + length * Math.cos(radians), Y + length * Math.sin(radians), length/1.5, Math.PI/2);
			growthPlant(mid);
			right = new BGStem(X + length * Math.cos(radians), Y + length * Math.sin(radians), length/2, Math.PI/2-radians);	
			growthPlant(right);
		}
		return depth--;
	}*/

	// method to growth 
		public void growthPlant(int age, double baseLength, double radian, BGStem baseStem) {

			// in order to calculate radians, LocationX/Y, length later
			double radians, X, Y, length;
			int ID = baseStem.getStemID();

			
		/*	 * Create 16 stems, and the first two stems are based on the baseStem
			 * Parameters here are able to change and adapt 
			 * It's able to increase the value of age to add more stems
*/			 
			stem = new BGStem(0, baseLength, baseLength - 5, Math.PI/2 + radian);
			stem = new BGStem(0, baseLength, baseLength - 5, Math.PI/2 - radian);
			if (age >= 2) {
				// Let every stem has two child stems
				for (int i = ID + 3; i < ID + Math.pow(2, age); i++) {

					// stems grow to left side
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
								(baseLength /2 ),
								// radians
								radians + radian);
					}

					// stems grow to right side
					else if (i % 2 == 0) {
						radians = BGStem.getFromHashMap((i - ID - 2) / 2 + ID).getRadians();
						X = BGStem.getFromHashMap((i - ID - 2) / 2 + ID).getLocationX();
						Y = BGStem.getFromHashMap((i - ID - 2) / 2 + ID).getLocationY();
						length = BGStem.getFromHashMap((i - ID - 2) / 2 + ID).getLength();
						stem = new BGStem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
								(baseLength/2 ), radians - radian);
					}
					//- ((int) (Math.log(i - ID + 1) / Math.log(2))) * 10
					//- ((int) (Math.log(i - ID + 1) / Math.log(2))) * 10
				}
			}
		}
}
