package edu.neu.csye6200.bg;

import java.util.logging.Logger;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class Plant {

	private static Logger log = Logger.getLogger(Plant.class.getName());
	private BGGenerationSet bgs;
	private static int idCounter = 1001;
	private String plantName;
	private int specimenID; // create a unique specimenID
	private int age;
	private double length;
	private double width;
	private BGStem baseStem;

	// constructor
	Plant(String plantName, double length) {
		setPlantName(plantName);
		setAge(0);
		setLength(length);
		this.specimenID = idCounter++;
		log.info("Constructing a Plant instance");
	}

	Plant(String plantName) {
		setPlantName(plantName);
		setAge(0);
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
	 * ----------------The growth method------------------------
	 */
	public void plantGrowth(String rule) {
		BGStem.claerHashMap();
		baseStem = new BGStem(0, 0, 100, Math.PI / 2);
		bgs = new BGGenerationSet();
		
		//maybe can change the rule in the GUI
		bgs.genrationSet(baseStem);
		setLength(plantHeight());
		setWidth(plantWidth());
		setAge(this.age + MyAppUI.generation);
	}

	// method to print child stems
	public String printChildStem() {
		String str = String.format("%1$-16s %2$-16s %3$-16s %4$-16s %5$-16s", "stemID", "locationX", "locationY",
				"stemLength", "radians");
		return str + baseStem.traverseHashMap(baseStem);
	}

	// method to get numbers of child stem
	public int childStemNumbers() {
		try {
			return baseStem.childStemNumbers(baseStem);
		}catch(NullPointerException e) {
			return 0;
		}
			
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
		String str = String.format("%1$-16d %2$-16s %3$-16d %4$-16.2f %5$-16.2f %6$-16d", this.specimenID, this.plantName,
				this.age, this.length, this.width, childStemNumbers());
		return str;
	}
}
