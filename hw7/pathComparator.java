package hw7;

import java.util.*;

import hw5.DirectedLabeledEdge;

public class pathComparator<N> implements Comparator<List<DirectedLabeledEdge<N, Double>>> {

	/**
	 * Compare two List of DirectedLabeledEdge 
	 * 
	 * @requires l1 != null $$ l2 != null
	 * @param	l1 the first list of edge
	 * @param 	l2 the second list of edge
	 * @return 	1 if the sum of label value from l1 edges > the sum of  label value from l2 edges
	 * 		   	0 if the sum of label value from l1 edges = the sum of  label value from l2 edges
	 * 			-1 if the sum of label value from l1 edges < the sum of  label value from l2 edges
	 */
	@Override
	public int compare(List<DirectedLabeledEdge<N, Double>> l1, List<DirectedLabeledEdge<N, Double>> l2) {
		Double thisCost = 0.0;
		for (DirectedLabeledEdge<N, Double> edge : l1) {
			thisCost += edge.getLabel();
		}
		
		double otherCost = 0;
		for (DirectedLabeledEdge<N, Double> edge : l2) {
			otherCost += edge.getLabel();
		}
		
		if (thisCost > otherCost) {
			return 1;
		} else if (thisCost < otherCost) {
			return -1;
		} else {
			return 0;
		}
	}
}