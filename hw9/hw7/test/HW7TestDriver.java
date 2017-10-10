package hw7.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import hw5.DirectedLabeledEdge;
import hw5.DirectedLabeledGraph;
import hw6.MarvelPaths;
import hw6.test.HW6TestDriver;
import hw7.MarvelPaths2;


/**
 * This class implements a testing driver which reads test scripts
 * from files for your graph ADT and improved MarvelPaths application
 * using Dijkstra's algorithm.
 **/
public class HW7TestDriver {
    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW7TestDriver td;

            if (args.length == 0) {
                td = new HW7TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW7TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw5.test.HW5TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw5.test.HW5TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    //TODO for the student: Parameterize the next line correctly.
    private final Map<String, DirectedLabeledGraph<String, Double>> graphs = new HashMap<String, DirectedLabeledGraph<String, Double>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW5TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW7TestDriver(Reader r, Writer w) {
    	input = new BufferedReader(r);
        output = new PrintWriter(w);
    }
    
    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
            throws IOException
        {
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                    // echo blank and comment lines
                    output.println(inputLine);
                }
                else
                {
                    // separate the input line on white space
                    StringTokenizer st = new StringTokenizer(inputLine);
                    if (st.hasMoreTokens()) {
                        String command = st.nextToken();

                        List<String> arguments = new ArrayList<String>();
                        while (st.hasMoreTokens()) {
                            arguments.add(st.nextToken());
                        }

                        executeCommand(command, arguments);
                    }
                }
                output.flush();
            }
        }

        private void executeCommand(String command, List<String> arguments) {
            try {
            	if (command.equals("CreateGraph")) {
                    createGraph(arguments);
                } else if (command.equals("AddNode")) {
                    addNode(arguments);
                } else if (command.equals("AddEdge")) {
                    addEdge(arguments);
                } else if (command.equals("ListNodes")) {
                    listNodes(arguments);
                } else if (command.equals("ListChildren")) {
                    listChildren(arguments);
                } else if (command.equals("LoadGraph")) {
                    loadGraph(arguments);
                } else if (command.equals("FindPath")) {
                    findPath(arguments);
                } else {
                    output.println("Unrecognized command: " + command);
                }
            } catch (Exception e) {
                output.println("Exception: " + e.toString());
            }
        }

        private void createGraph(List<String> arguments) {
            if (arguments.size() != 1) {
                throw new CommandException("Bad arguments to CreateGraph: " + arguments);
            }
            String graphName = arguments.get(0);
            createGraph(graphName);
        }
        
        private void createGraph(String graphName) {
            // Insert your code here.
            // graphs.put(graphName, ___);
            // output.println(...);
        	graphs.put(graphName, new DirectedLabeledGraph<String, Double>());
        	output.println("created graph " + graphName);
        }
        
        private void addNode(List<String> arguments) {
            if (arguments.size() != 2) {
                throw new CommandException("Bad arguments to addNode: " + arguments);
            }
            String graphName = arguments.get(0);
            String nodeName = arguments.get(1);
            addNode(graphName, nodeName);
        }
        
        private void addNode(String graphName, String nodeName) {
            // Insert your code here.
            // ___ = graphs.get(graphName);
            // output.println(...);
        	
        	DirectedLabeledGraph<String, Double> myGraph = graphs.get(graphName);
            myGraph.addNode(nodeName);
            output.println("added node "+ nodeName + " to " + graphName);
        }
        
        private void addEdge(List<String> arguments) {
            if (arguments.size() != 4) {
                throw new CommandException("Bad arguments to addEdge: " + arguments);
            }
            String graphName = arguments.get(0);
            String parentName = arguments.get(1);
            String childName = arguments.get(2);
            Double edgeLabel = Double.parseDouble(arguments.get(3));
            addEdge(graphName, parentName, childName, edgeLabel);
        }
        
        private void addEdge(String graphName, String parentName, String childName,
                Double edgeLabel) {
            // Insert your code here.
            // ___ = graphs.get(graphName);
            // output.println(...);
        	
        	DirectedLabeledGraph<String, Double> myGraph = graphs.get(graphName);
        	myGraph.addEdge(parentName, childName, edgeLabel);
        	output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to " + 
        			childName + " in " + graphName);
        }
        
        private void listNodes(List<String> arguments) {
            if (arguments.size() != 1) {
                throw new CommandException("Bad arguments to listNodes: " + arguments);
            }
            String graphName = arguments.get(0);
            listNodes(graphName);
        }
        
        private void listNodes(String graphName) {
            // Insert your code here.
            // ___ = graphs.get(graphName);
            // output.println(...);
        	DirectedLabeledGraph<String, Double> myGraph = graphs.get(graphName);
        	ArrayList<String> list = new ArrayList<String>(myGraph.getAllParents());
        	Collections.sort(list);
        	output.print(graphName + " contains:");
        	for (String node : list) {
        		output.print(" " + node);
        	}
        	output.println();
        }
        
        private void listChildren(List<String> arguments) {
            if (arguments.size() != 2) {
                throw new CommandException("Bad arguments to listChildren: " + arguments);
            }
            String graphName = arguments.get(0);
            String parentName = arguments.get(1).replaceAll("_", " ");;
            listChildren(graphName, parentName);
        }
        
        // Updated listChildren to sorted
        private void listChildren(String graphName, String parentName) {
            // Insert your code here.
            // ___ = graphs.get(graphName);
            // output.println(...);
        	DirectedLabeledGraph<String, Double> myGraph = graphs.get(graphName);
        	output.print("the children of "+ parentName + " in " + graphName + " are:");
        	if (myGraph.getAllParents().contains(parentName)) {
	        	Map<String, HashSet<DirectedLabeledEdge<String, Double>>> children = myGraph.getAllEdges(parentName);
	        	ArrayList<String> sortedChildren = new ArrayList<String>(children.keySet());
	        	Collections.sort(sortedChildren);
	        	for (String child : sortedChildren) {
	        		ArrayList<DirectedLabeledEdge<String, Double>> sortedEdges= new ArrayList<DirectedLabeledEdge<String, Double>>(myGraph.getAllEdges(parentName).get(child));
	        		Collections.sort(sortedEdges);
	        		for (DirectedLabeledEdge<String, Double> edge : sortedEdges) {
	        			output.print(" " + edge.getChildrenNode() + "(" + String.format("%.3f", edge.getLabel()) + ")");
	        		}
	        	}
	        	output.println();
        	}
        }
        
        private void loadGraph(List<String> arguments) {
            if (arguments.size() != 2) {
                throw new CommandException("Bad arguments to CreateGraph: " + arguments);
            }

            String graphName = arguments.get(0);
            String fileName = arguments.get(1);
            loadGraph(graphName, fileName);
        }

        private void loadGraph(String graphName, String fileName) {
            // Insert your code here.

            // graphs.put(graphName, ___);
            // output.println(...);
        	graphs.put(graphName, MarvelPaths2.buildGraph("src/hw7/data/" + fileName));
        	output.println("loaded graph " + graphName);
        }
        
        
        private void findPath(List<String> arguments) {
            if (arguments.size() != 3) {
                throw new CommandException("Bad arguments to addNode: " + arguments);
            }

            String graphName = arguments.get(0);
            String starNode = arguments.get(1).replaceAll("_", " ");
            String endNode= arguments.get(2).replaceAll("_", " ");

            findPath(graphName, starNode, endNode);
        }
        
        private void findPath(String graphName, String startNode, String endNode) {
            // Insert your code here.
            // ___ = graphs.get(graphName);
            // output.println(...);
        	
        	DirectedLabeledGraph<String, Double> builtGraph = graphs.get(graphName);
            List<DirectedLabeledEdge<String, Double>> foundPath = MarvelPaths2.findPath(builtGraph, startNode, endNode);
            if (!builtGraph.getAllParents().contains(startNode) && !builtGraph.getAllParents().contains(endNode)) {
        		output.println("unknown character " + startNode + " " + endNode);
        	} else if (!builtGraph.getAllParents().contains(startNode)) {
        		output.println("unknown character " + startNode);
        	} else if (!builtGraph.getAllParents().contains(endNode)) {
        		output.println("unknown character " + endNode);
        	} else {    
	            output.println("path from "+ startNode + " to " + endNode + ":");
	            if (foundPath != null) {
	            	if (startNode.equals(endNode)) {
	                	output.println("total cost: 0.000");
	                } else {
		            	double totalCost = 0;
		    	        for (int i = 1; i < foundPath.size(); i++) {
		    	        	DirectedLabeledEdge<String, Double> edge = foundPath.get(i);
		    	        	output.println(edge.getParentNode() + " to " + edge.getChildrenNode() + String.format(" with weight %.3f", edge.getLabel()));
		    	        	totalCost += edge.getLabel();
		    	        }
		    	        output.println("total cost: " + String.format("%.3f", totalCost));
	                }
	            } else {
	            		output.println("no path found");
	            }
        	}
        }
        
        /**
         * This exception results when the input file cannot be parsed properly
         **/
        static class CommandException extends RuntimeException {

            public CommandException() {
                super();
            }
            public CommandException(String s) {
                super(s);
            }
            
            public static final long serialVersionUID = 3495;
        }
 }
