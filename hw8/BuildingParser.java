package hw8;
import java.io.*;
import java.math.*;
import java.util.*;

import hw6.MarvelParser.MalformedDataException;
/**
 * Parser utility to load the UW Campus Building data.
 */
public class BuildingParser {
	/**
	 * A checked exception class for bad data files
	 */
	@SuppressWarnings("serial")
	public static class MalformedDataException extends Exception {
	    public MalformedDataException() { }
	    public MalformedDataException(String message) {
	        super(message);
	    }
	    public MalformedDataException(Throwable cause) {
	        super(cause);
	    }
	    public MalformedDataException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}
	
   /**
    * Reads the Campus Building dataset
    * Each line of the input file contains a building's short name, longName, x and y coordinate
    * book the character appeared in, separated by a tab character
    * 
    * @requires filename is a valid file path
    * @param filename the file that will be read
    * @param building list in which all building names will be stored;
    *          typically empty when the routine is called
    * @modifies nodes
    * @effects fills nodes with a list of PathNode that represent campus buildings
    * @throws MalformedDataException if the file is not well-formed:
    *          each line contains exactly two tokens separated by a tab,
    *          or else starting with a # symbol to indicate a comment line.
    */
	public static void parseData(String filename, Set<PathNode> nodes) throws MalformedDataException {
		BufferedReader reader = null;
		try {
		   reader = new BufferedReader(new FileReader(filename));
		   String inputLine;
		   while ((inputLine = reader.readLine()) != null) {
			// Parse the data, stripping out quotation marks and throwing
			// an exception for malformed lines.
				String[] tokens = inputLine.split("\t");
				if (tokens.length != 4) {
				   throw new MalformedDataException("Line should contain exactly four tab: "
				                                 + inputLine);
				}
				String shortName = tokens[0];
				String longName = tokens[1];
				Double x = Double.parseDouble(tokens[2]);
				Double y = Double.parseDouble(tokens[3]);
				// Add the parsed data to the building collections.
				nodes.add(new PathNode(shortName, longName, x, y));
		   }
		} catch (IOException e) {
		    System.err.println(e.toString());
		    e.printStackTrace(System.err);
		} finally {
		    if (reader != null) {
		        try {
		            reader.close();
		        } catch (IOException e) {
		            System.err.println(e.toString());
		            e.printStackTrace(System.err);
		        }
		    }
		}
	}
}
