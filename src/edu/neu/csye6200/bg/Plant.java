package edu.neu.csye6200.bg;

import java.util.logging.Logger;
/**
 * @author Tianju Zhou NUID 001420546
 */
public class Plant {

	private static Logger log = Logger.getLogger(Plant.class.getName());
	
	private static int idCounter = 1001;
	
	private String plantName;
	private int specimenID; 	// create a unique specimenID
	private int age; 		
	private double length; 	
	private double width;
	private BGStem baseStem;
	
	//@SuppressWarnings("unused")
	//private BGStem stem;	
	
	// constructor
	Plant(String plantName, double length) {
		setPlantName(plantName);
		setAge(1);
		setLength(length);
		this.specimenID = idCounter++;
		log.info("Constructing a Plant instance");
	}

	Plant(String plantName) {
		setPlantName(plantName);
		setAge(1);
		setLength(60);
		this.specimenID = idCounter++;
		log.info("Constructing a Plant instance");
	}

	//get the specimenID
	public int getSpecimenID() {
		return specimenID;
	}

	//getter and setter of plantName and age
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		// age must greater or equal than 0
		if (age >= 0)
			this.age = age;
		else
			this.age = 0;
	}
	
	//getter and setter of length and width
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		// length must greater or equal than 1
		if (length >= 0)
			this.length = length;
		else
			this.length = 0;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		if(width >= 0)
			this.width = width;
		else
			this.width = 0;
	}
	
	//get the baseStem
	public BGStem getBaseStem() {
		return baseStem;
	}

	/*
	 * ----------------The growth method------------------------
	 */
	public void plantGrowth(int age) {

		BGRule bg = new BGRule();
		
		// different plant should have different growth patterns
		// here, assume rose has the same growth patterns as maple
		switch (this.plantName) {
		case "Maple":
			baseStem = new BGStem(0, 0, 100, Math.PI/2);
			bg.growthPlant(age, 100, Math.PI/3, baseStem);
			break;
		case "PhoenixTree":
			baseStem = new BGStem(0, 0, 20, 90);
			bg.growthPlant(age, this.length, 20, baseStem);
			break;
		case "CamphorTree":
			baseStem = new BGStem(0, 0, 15, 90);
			bg.growthPlant(age, this.length, 15, baseStem);
			break;
		}
		setAge(this.age + age);
	}

	
/*	// method to growth 
	public void growthPlant1(int age, double baseLength, int rotateRadian) {

		baseStem = new Stem(0, 0, baseLength, 90);

		// in order to calculate radians, LocationX/Y, length later
		double radians, X, Y, length;
		int ID = baseStem.getStemID();

		
		 * Create 16 stems, and the first two stems are based on the baseStem
		 * Parameters here are able to change and adapt 
		 * It's able to increase the value of age to add more stems
		 
		stem = new Stem(0, baseLength, baseLength - 5, 90 + rotateRadian);
		stem = new Stem(0, baseLength, baseLength - 5, 90 - rotateRadian);
		if (age >= 2) {
			// Let every stem has two child stems
			for (int i = ID + 3; i < ID + Math.pow(2, age); i++) {

				// stems grow to left side
				if (i % 2 == 1) {
					// get the last stem's radians, location x and y, length; in
					// orede to create new stem
					radians = Stem.getFromHashMap((i - ID - 1) / 2 + ID).getRadians();
					X = Stem.getFromHashMap((i - ID - 1) / 2 + ID).getLocationX();
					Y = Stem.getFromHashMap((i - ID - 1) / 2 + ID).getLocationY();
					length = Stem.getFromHashMap((i - ID - 1) / 2 + ID).getLength();
					stem = new Stem(
							// locationX
							(X + length * Math.cos(radians)),
							// locationY
							(Y + length * Math.sin(radians)),
							// length
							(baseLength - ((int) (Math.log(i - ID + 1) / Math.log(2))) * 5),
							// radians
							radians + rotateRadian);
				}

				// stems grow to right side
				else if (i % 2 == 0) {
					radians = Stem.getFromHashMap((i - ID - 2) / 2 + ID).getRadians();
					X = Stem.getFromHashMap((i - ID - 2) / 2 + ID).getLocationX();
					Y = Stem.getFromHashMap((i - ID - 2) / 2 + ID).getLocationY();
					length = Stem.getFromHashMap((i - ID - 2) / 2 + ID).getLength();
					stem = new Stem((X + length * Math.cos(radians)), (Y + length * Math.sin(radians)),
							(baseLength - ((int) (Math.log(i - ID + 1) / Math.log(2))) * 5), radians - rotateRadian);
				}
			}
		}
	}
*/
	
	//method to print child stems
	public String printChildStem() {
		String str = String.format("%1$-16s %2$-16s %3$-16s %4$-16s %5$-16s", "stemID", "locationX", "locationY",
				"stemLength", "radians");
		return str + baseStem.traverseHashMap(baseStem);
	}

	//method to get numbers of child stem
	public int childStemNumbers(){
		return baseStem.childStemNumbers(baseStem);
	}

	//return the total height of the plant
	public double plantHeight(){
		setLength(baseStem.totalHeight(baseStem));
		return this.length;
	}
	
	//return the total width of the plant
	public double plantWidth(){
		setWidth(baseStem.totalWidth(baseStem));
		return this.width;
	}
	
	// the toString method
	public String toString() {
		String str = String.format("%1$-16d %2$-16s %3$-16d %4$-16.2f %5$-16.2f", this.specimenID, this.plantName, this.age,
				this.length, this.width);
		return str;
	}
}
