package hw5;
/**
 * <b>GraphNode</b> represents a <b>immutable</b> node 
 * which has a piece of information of any kind and 
 * could be linked with other nodes using other classes.
 *
 * Specification fields:
 *  @specfield value : String // The content of a node
 *
 * Abstract Invariant:
 *  A node's value != null && value is never empty
 */

public final class GraphNode {
	private final String nodeValue;
		
	// Abstraction Function:
	//  AF(r) = GraphNode, n, with n.value = r.nodeValue
	//
	// Representation Invariant:
	//  GraphNode != null && nodeValue != null && nodeValue.isEmpty() != true
	
	/**
	* Checks that the representation invariant holds (if any).
	*/
	private void checkRep() {
		assert(this != null):"Node is not built correctly";
		assert(this.nodeValue != null) : "GraphNode can not have null value."; 
		assert(this.nodeValue.isEmpty() != true) : "GraphNode can not have a value of empty String";
	}
	
	/** @param s The value of the new GraphNode
	 * 	@requires s != null && s.isEmpty() != true
	 *  @effects construct a new GraphNode n with n.value = s
	 *  @throws IllegalArgumentException if specified s is null or is empty String
	 */
	public GraphNode(String s) {
		if (s == null) {
			throw new IllegalArgumentException("Argment can not be null"); 
		} else if (s.isEmpty()) {
			throw new IllegalArgumentException("Empty String is not allowed"); 
		} else {
			nodeValue = s;
		}
		checkRep();
	}
	
	/** Return current GraphNode
	 *  @return this 
	 */
	public GraphNode getNode() {
		return this;
	}
	
	// Added NEW!!!
	public String getNodeValue() {
		return nodeValue;
	}
		
	/** Standard equality operation.
	 *  @param obj The object to be compared for equality.
	 *  @return true if and only if 'obj' is an instance of a GraphNode and 'this'
	 *         and 'obj' represent the GraphNode with same value(String in the case).
	 *         Otherwise, return false;
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GraphNode) {
			GraphNode other = (GraphNode) obj;
			return this.nodeValue.equals(other.nodeValue);
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
		return this.nodeValue.hashCode();
	}	
	
	/** Standard toString function.
	 * 	@return an String representation of the node
	 */
	@Override
	public String toString() {
		return this.nodeValue;
	}
}