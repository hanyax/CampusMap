package hw8.test;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import hw4.test.SpecificationTests;
import hw8.PathNode;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the PathNode class.
 * <p>
 */
public class PathNodeTest {
	/**
	* checks that Java asserts are enabled, and exits if not
	*/
	@Before
	public void testAssertsEnabled() {
		 SpecificationTests.checkAssertsEnabled();
	}
	
	private void eq(PathNode testNode, String shortName, String longName, Double x, Double y) {
		if (shortName != null && longName != null) {
			assert(testNode.getShortName().equals(shortName));
			assert(testNode.getLongName().equals(longName));
		} else {
			assert(testNode.getShortName() == null);
			assert(testNode.getLongName() == null);
		}
		assert(testNode.getX().equals(x));
		assert(testNode.getY().equals(y));
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////	Constructor
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		eq(new PathNode("ODE","This is library",1.1, 2.2), "ODE","This is library", 1.1, 2.2);
		eq(new PathNode(null, null ,1.1, 2.2), null, null, 1.1, 2.2);
		eq(new PathNode(1.1, 2.2), null, null, 1.1, 2.2);
	}
	
	@Test
	public void testConstructorException() {
		try {
			new PathNode(null, null, null, null);
			fail("Does not throw when x and y coordinate is null");
		} catch(IllegalArgumentException e){
		}
		
		try {
			new PathNode(null, null);
			fail("Does not throw when x and y coordinate is null");
		} catch(IllegalArgumentException e){
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////	Test getShortName/getLongName/getX/getY
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testGetMethods() {
		PathNode testNode = new PathNode("ODE","This is library",1.1, 2.2);
		PathNode testNull = new PathNode(1.1, 2.2);
		
		assert(testNode.getShortName().equals("ODE"));
		assert(testNode.getLongName().equals("This is library"));
		assert(testNode.getX() == 1.1);
		assert(testNode.getY() == 2.2);
		
		assert(testNull.getShortName() == null);
		assert(testNull.getLongName() == null);
		assert(testNull.getX() == 1.1);
		assert(testNull.getY() == 2.2);
	}

///////////////////////////////////////////////////////////////////////////////////////
////	Test Equals
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testEquals() {
		PathNode testEqual1 = new PathNode("ODE","This is library",1.1, 2.2);
		PathNode testEqual2 = new PathNode(1.1, 2.2);
		PathNode testEqual3 = new PathNode("SUZ","This is another library",1.1, 2.2);
	
		assert(testEqual1.equals(testEqual1));
		assert(testEqual1.equals(testEqual2));
		assert(testEqual1.equals(testEqual3));
		assert(testEqual2.equals(testEqual3));
	}
}
