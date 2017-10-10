package hw8;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import hw6.MarvelParser.MalformedDataException;
/**
 * Parser utility to load the UW Campus Building data.
 */
public class PathParser {
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
    * Reads the campus path dataset
    * A block of input contains one line of start point coordinate
    * and the rest of lines that start with tabs the shows the end point
    * coordinate and the distance from start point to the end point.
    * 
    * @requires filename is a valid file path
    * @param 	filename the file that will be read
    * @param 	paths map in which all paths coordinate and distance will be stored;
    *          	typically empty when the routine is called
    * @modifies paths
    * @effects	fills paths with a map of start points represented by PathNode with corresponding 
    * 			end point represented by PathNode and distance between them 
    * @throws	MalformedDataException if the file is not well-formed:
    * 			each line contains exactly two tokens separated by a tab,
    * 			or else starting with a # symbol to indicate a comment line.
    */
	public static void parseData(String filename, Map<PathNode, Map<PathNode, Double>> paths,
								Set<PathNode> nodes) throws MalformedDataException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String inputLine;
			// Parse the data and add data to paths collections
			PathNode currentStartNode = null;
			while ((inputLine = reader.readLine()) != null) {
				if (!inputLine.startsWith("\t")) {
					String[] tokens = inputLine.split(",");
					Double x = Double.parseDouble(tokens[0]);
					Double y = Double.parseDouble(tokens[1]);
					PathNode startNode = new PathNode(x,y);
					paths.put(startNode, new HashMap<PathNode, Double>());
					nodes.add(startNode);
					currentStartNode = startNode;
				} else  {
					inputLine = inputLine.trim();
			   		String[] tokens = inputLine.split(": ");
			   		String[] endNodeTokens = tokens[0].split(",");
			   		Double endNodeX = Double.parseDouble(endNodeTokens[0]);
			   		Double endNodeY = Double.parseDouble(endNodeTokens[1]);
			   		Double distance = Double.parseDouble(tokens[1]);
			   		PathNode endNode = new PathNode(endNodeX, endNodeY);
			   		nodes.add(endNode);
			   		paths.get(currentStartNode).put(endNode, distance);
		   		}
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
