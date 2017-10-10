package hw8;

/** PathNode represent a immutable location in a map. 
 * 	A PathNode can represent buildings or points where
 * 	a single path separate into multiple paths.
 * 
 * 	In PathNode, one location is described with:
 * 	a short name, a long name, x and y coordinate. 
 * 	A pathNode can only has x,y coordinate without names 
 * 	but is not allowed to have names but no coordinate
 * 
 * 	@author ShawnXu
 * 
 * 	Specification field:
 * 	@specfield short name : String // the shortName for location this pathNode represent
 *	@specfield long name : String // the longName for location this pathNode represent
 *	@specfield x coordinate : Double // the x coordinate for location this pathNode represent
 *	@specfield y coordinate : Double // the y coordinate for location this pathNode represent
 *
 *	Abstract Invariant:
 *	A pathNode can not have null x and y coordinate
 */
public class PathNode {
	// 	Abstraction Function:
	//	AF(r) = PathNode p such that 
	//			p.shot name = r.getShortName()
	//			p.long name = r.getLongName()
	//			p.x = r.getX();
	//			p.y = r.getY();
	
	// 	Representation Invariant:
	// 	PathNode != null && PathNode.x != null &&
	//	PathNode.y != null
	
	/** 
	 * Checks that the representation invariant holds (if any).
	 */
	private void checkRep() {
		assert(this != null) : "PathNode can not be null";
		assert(this.x != null) : "PathNode can not have null x coordinate";
		assert(this.y != null) : "PathNode can not have null y coordinate";
	}
	
	private final String shortName;
	private final String longName;
	private final Double x;
	private final Double y;
	
	/**
	 * @param shortName	String, the shortname for location the new PathNode object represent
	 * @param longName	String, the longname for location the new PathNode object represent
	 * @param x	Double, the x coordinate for location the new PathNode object represent
	 * @param y	Double, the	y coordinate for location the new PathNode object represent
	 * @requires x != null && y != null
	 * @effects	Construct a new PathNode with shortName, longName, x, y equals
	 * 			to passed in parameter 
	 * @modifies this
	 * @throws IllegalArgumentException if x == null || y == null
	 */
	public PathNode(String shortName, String longName, Double x, Double y) {
		if (x == null || y == null) {
			throw new IllegalArgumentException("PathNode can not have null x and y coordinate ");
		}
		this.shortName = shortName;
		this.longName = longName;
		this.x = x;
		this.y = y;
		checkRep();
	}
	
	/**
	 * @param x	Double, the x coordinate for location the new PathNode object represent
	 * @param y Double, the y coordinate for location the new PathNode object represent
	 * @requires x != null && y != null
	 * @effects	Construct a new PathNode with null shortName, null longName and x, y equals
	 * 			to passed in parameter 
	 * @requires x != null && y != null
	 * @modifies this
	 * @throws IllegalArgumentException if x == null || y == null
	 */
	public PathNode(Double x, Double y) {
		if (x == null || y == null) {
			throw new IllegalArgumentException("PathNode can not have null x and y coordinate ");
		}
		this.shortName = null;
		this.longName = null;
		this.x = x;
		this.y = y;
		checkRep();
	}
	
	/**
	 * @return the short name for location the this PathNode object represent
	 */
	public String getShortName() {
		return this.shortName;
	}
	
	/**
	 * @return the long name for location the this PathNode object represent
	 */
	public String getLongName() {
		return this.longName;
	}
	
	/**
	 * @return the x coordinate for location the new PathNode object represent
	 */
	public Double getX() {
		return this.x;
	}
	
	/**
	 * @return the y coordinate for location the new PathNode object represent
	 */
	public Double getY() {
		return this.y;
	}
	
	/** Standard hashCode function.
	 * 	@return an int that all objects equal to this will also
	 *  return.
	 */
	@Override 
	public int hashCode() {
		return 19 * this.x.hashCode() + 37 * this.y.hashCode();
	}
	
	/** Standard equality operation.
	 * 	@param obj The object to be compared for equality.
	 * 	@return true if and only if o is a instance of PathNode and o.x = this.x and o.y = this.y
	 * 			false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PathNode)) {
			return false;
		}
		PathNode other = (PathNode) o;
		return this.x.equals(other.x) && this.y.equals(other.y);
	}
	
	@Override
	public String toString() {
		return "[" + longName + " " + x + " " + y + "]";
	}
}
