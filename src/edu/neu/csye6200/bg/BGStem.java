package edu.neu.csye6200.bg;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class BGStem {
	private static int stemCounter = 0;
	
	private int stemID;			//create a unique stemID
	private double locationX;		
	private double locationY;	
	private double length;		
	private double radians;		
	
	// constructor
	public BGStem() {

	}

	/**
	 * Constructor
	 * 
	 * @param locationX: X-axis coordinate
	 * @param locationY: Y-axis coordinate
	 * @param length: length of each stem
	 * @param radians:	growth radians of each stem
	 */
	public BGStem(double locationX, double locationY, double length, double radians) {
		this.stemID = stemCounter++;
		setLocationX(locationX);
		setLocationY(locationY);
		setLength(length);
		setRadians(radians);
	}


	//getter ans setter of locationX,Y
	public double getLocationX() {
		return locationX;
	}
	public void setLocationX(double locationX) {
			this.locationX = locationX;
	}
	public double getLocationY() {
		return locationY;
	}
	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	//getter and setter of length and radians
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		
		//the legnth should not less than 0
		if (length <= 0)
			length = 0;
		this.length = length;
	}
	public double getRadians() {
		return radians;
	}
	public void setRadians(double radians) {
		this.radians = radians;
	}

	//get stemID
	public int getStemID() {
		return stemID;
	}

	//print the format of stem
	public String toString() {
		return String.format("%1$-16d %2$-16.2f %3$-16.2f %4$-16.2f %5$-16.2f", this.stemID, this.locationX,
				this.locationY, this.length, this.radians);
	}
}
