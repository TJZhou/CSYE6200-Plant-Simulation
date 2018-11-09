package edu.neu.csye6200.bg;

import java.util.logging.Logger;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class Plant {

	private static Logger log = Logger.getLogger(Plant.class.getName());

	private static int idCounter = 1001;

	private String plantName;
	private int specimenID; // create a unique specimenID
	private int age;
	private double length;
	private double width;
	private BGStem baseStem;

	// @SuppressWarnings("unused")
	// private BGStem stem;

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

	// get the specimenID
	public int getSpecimenID() {
		return specimenID;
	}

	// getter and setter of plantName and age
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

	// getter and setter of length and width
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
		if (width >= 0)
			this.width = width;
		else
			this.width = 0;
	}

	// get the baseStem
	public BGStem getBaseStem() {
		return baseStem;
	}

	/*
	 * ----------------The Generation Set------------------------
	 */
	public void genrationSet(int generation, BGStem baseStem) {

		BGRule bg = new BGRule();

		// different plant should have different growth patterns
		// here, assume rose has the same growth patterns as maple
		switch (this.plantName) {
		case "Maple":
			// locationX locationY length radians
			bg.growthRule1(generation, 100, Math.PI / 6, baseStem);
			break;

		case "PhoenixTree":
			/*
			 * baseStem = new BGStem(0, 0, 20, 90); bg.growthPlant(age, this.length, 20,
			 * baseStem);
			 */
			break;

		case "CamphorTree":
			/*
			 * baseStem = new BGStem(0, 0, 15, 90); bg.growthPlant(age, this.length, 15,
			 * baseStem);
			 */
			break;
		}

	}

	/*
	 * ----------------The growth method------------------------
	 */
	public void plantGrowth(int generation) {
		baseStem = new BGStem(0, 0, 100, Math.PI / 2);
		genrationSet(generation, baseStem);
		setAge(this.age + generation);
	}

	// method to print child stems
	public String printChildStem() {
		String str = String.format("%1$-16s %2$-16s %3$-16s %4$-16s %5$-16s", "stemID", "locationX", "locationY",
				"stemLength", "radians");
		return str + baseStem.traverseHashMap(baseStem);
	}

	// method to get numbers of child stem
	public int childStemNumbers() {
		return baseStem.childStemNumbers(baseStem);
	}

	// return the total height of the plant
	public double plantHeight() {
		setLength(baseStem.totalHeight(baseStem));
		return this.length;
	}

	// return the total width of the plant
	public double plantWidth() {
		setWidth(baseStem.totalWidth(baseStem));
		return this.width;
	}

	// the toString method
	public String toString() {
		String str = String.format("%1$-16d %2$-16s %3$-16d %4$-16.2f %5$-16.2f", this.specimenID, this.plantName,
				this.age, this.length, this.width);
		return str;
	}
}
