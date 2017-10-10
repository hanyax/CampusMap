package hw5;
/**
 * <b>DirectedLabeledEdge</b> represents a <b>immutable</b> edge expression. 
 * An directed labeled edge e consist of two nodes n1 and n2. (n1,n2) represents 
 * a directed vector connection indicating that n2 is directly reachable from n1. 
 * e also contain a label which is a piece of information that often describes the 
 * medium/value of the connection. Two edges with same nodes, same connection and same
 * label are considered duplicate which is allowed in this ADT.
 *
 * Specification fields:
 * @param <E>
 *  @specfield parent : String // tail node the connection start
 *  @specfield children : String // head node the connection pointing to 
 *  @specfield info : String // information about this connection
 *
 * Abstract Invariant:
 *  A edge can not point to or point from null, meaning parent and children could 
 *  not be null 
 */

public class DirectedLabeledEdge<N, L> implements Comparable<DirectedLabeledEdge<N, L>> {
	private final N parentNode;
	private final N childNode;
	private final L label;
	
	// Abstraction Function:
	//  AF(r) = DirectedLabeledEdge e such that 
	//			e.parent = r.parentNode
	// 			e.children = r.childrenNode
	//			e.info = r.label
	//
	// Representation Invariant:
	//  Edge != null && parentNode != null && childNode != null
	
	/**
	* Checks that the representation invariant holds (if any).
	*/
	private void checkRep() {
		assert(this != null):"Edge is not built correctly";
		assert(this.parentNode != null) : "DirectedLabeledEdge can not have a null parentNode."; 
		assert(this.childNode != null) : "DirectedLabeledEdge can not have a null childNode."; 
	}
	
	/**
	 * @param p the parentNode of new DirectedLabeledEdge
	 * @param c the childNode of new DirectedLabeledEdge
	 * @param l the label of new DirectedLabeledEdge
	 * @requires parenNode != null && childNode != null && info != null
	 * @effects Construct a new DirectedLabeledEdge with parentNode pointing to childNode with 
	 * 			label information stored.
	 * @throws IllegalArgumentException if specified String p is null or String c is null
	 * 			or String l is null
	 */
	public DirectedLabeledEdge(N parent, N child, L otherLabel) {
		if (parent == null) {
			throw new IllegalArgumentException("GraphNode p can not be null"); 
		} else if (child == null) {
			throw new IllegalArgumentException("GraphNode c can not be null"); 
		} else if (otherLabel == null) {
			throw new IllegalArgumentException("String l can not be null"); 
		} else { 
			this.parentNode = parent;
			this.childNode = child;
			this.label = otherLabel;
		}
		checkRep();
	}
	
	/** Get the label of this DirectedLabeledEdge
	 * 
	 * @return the String represent the label information 
	 */
	public L getLabel() {
		return this.label;
	}
	
	/** Get the childNode of this DirectedLabeledEdge
	 * 
	 * @return the String represent the childNode 
	 */
	public N getChildrenNode() {
		return this.childNode;
	}
	
	/** Get the parentNode of this DirectedLabeledEdge
	 * 
	 * @return the String represent the parentNode
	 */
	public N getParentNode() {
		return this.parentNode;
	}
	
	/** Standard equality operation.
	 *  @param obj The object to be compared for equality.
	 *  @return true if and only if 'obj' is an instance of a DirectedLabeledEdge and 'this'
	 *         and 'obj' represent the DirectedLabeledEdge with same parentNode, childrenNode
	 *         and label. Otherwise, return false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DirectedLabeledEdge) {
			DirectedLabeledEdge<?, ?> other = (DirectedLabeledEdge<?, ?>) obj;
			return ( this.parentNode.equals(other.parentNode) && 
					this.childNode.equals(other.childNode) && this.label.equals(other.label));
		} else {
			return false;
		}
	}
	
	/** Standard hashCode function.
	 * 	@return an int that all objects equal to this will also
	 *  return.
	 */
	@Override
	public int hashCode() {
		return parentNode.hashCode() + childNode.hashCode() + label.hashCode(); 
	}
	
	/** Standard toString function.
	 * 	@return a String representation of edge
	 *  <parentNode, label, childNode>
	 */
	@Override
	public String toString() {
		return "[" + this.parentNode.toString() + " " + label.toString() + " " + this.childNode.toString() + "]";
	}
	
	// When call by addEdge method of DLG, only need to tell the difference of the label
	// What if the Type L has no compareTo 
	@Override
	public int compareTo (DirectedLabeledEdge<N, L> other) {
		return this.label.toString().compareTo(other.label.toString());
	}
}