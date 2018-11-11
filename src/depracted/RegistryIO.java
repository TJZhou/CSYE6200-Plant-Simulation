package depracted;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Tianju Zhou NUID 001420546
 */
public class RegistryIO {

	private static Logger log = Logger.getLogger(RegistryIO.class.getName());
	
	Plant plant;
	
	RegistryIO() {
		log.info("Constructing a RegistryIO instance");
	}

	/**
	 * save all Plant
	 * 
	 * @param plantMap
	 * @param fileName
	 */
	public void saveAll(HashMap<Integer, Plant> plantMap, String fileName) {
		
		log.info("Save method is called");
		
		for (Plant pt : plantMap.values()) {
			save(pt, fileName);
		}
	}

	/**
	 * save single Plant
	 * 
	 * @param pt
	 * @param fileName
	 */
	public void save(Plant pt, String fileName) {
		
		// open source and destination files
		// using try-with-resources, using FileWriter to modify BufferedWriter
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))){
		
			saveBasicInfo(bw, pt);

			saveStemInfo(bw, pt);
			
			bw.write("---------------------------------------------------------------------------------------------------------\n");
			
		} catch (FileNotFoundException e) { // File cannot be found
			log.warning("FileNotFoundException occurs at save method");
			e.printStackTrace();
		} catch (IOException e) { // All other IO problem
			log.warning("(IOException occurs at save method");
			e.printStackTrace();
		}
	}


	/**
	 * write basic Plant info
	 * 
	 * @param bw
	 * @param pt
	 * @throws IOException
	 */
	private void saveBasicInfo(BufferedWriter bw, Plant pt) throws IOException {
		bw.write(String.format("%1$-16s %2$-16s %3$-16s %4$-16s %5$-16s %6$-16s", 
		"specimenID", "plantName", "Age(Generation)", "totalHeight (cm)", "totalWidth (cm)", "stemNumbers"));
		bw.write("\n");
		bw.write(pt.toString());
		bw.write("\n\n");
	}

	/**
	 * write the stems info of the Plant
	 * 
	 * @param bw
	 * @param pt
	 * @throws IOException
	 */
	private void saveStemInfo(BufferedWriter bw, Plant pt) throws IOException {
		bw.write(pt.printChildStem());
	}
}
