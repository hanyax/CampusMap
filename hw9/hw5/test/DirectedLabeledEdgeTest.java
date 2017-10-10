package hw5.test;

import static org.junit.Assert.*;

import org.junit.Before;

import hw5.GraphNode;
import hw5.DirectedLabeledEdge;
import hw4.test.SpecificationTests;

import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the DirectedLabelEdge class.
 * <p>
 */
public class DirectedLabeledEdgeTest {
	
	/**
	* checks that Java asserts are enabled, and exits if not
	*/
	@Before
	public void testAssertsEnabled() {
		 SpecificationTests.checkAssertsEnabled();
	}
	
	//Test String/ GraphNodes/ DirectedLabeledEdge:
	private String parentNode = "parent";
	private String childNode = "child";
	private DirectedLabeledEdge<String, String> testEdge = new DirectedLabeledEdge<String, String>(parentNode,childNode, "BvS");
	private DirectedLabeledEdge<String, String> testEdge2 = new DirectedLabeledEdge<String, String>("parent", "child", "BvS");
	private DirectedLabeledEdge<String, String> testEdge3 = new DirectedLabeledEdge<String, String>("parent", "child", "BvS");
	
	private void eq(DirectedLabeledEdge<String, String> testEdge, String label) {
		assert(testEdge.getParentNode().equals(parentNode));
		assert(testEdge.getChildrenNode().equals(childNode));
		assert(testEdge.getLabel().equals(label));
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////
//// Constructor / getLabel
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructorAndGetLabel() {
		eq(new DirectedLabeledEdge<String, String>(parentNode,childNode, "BvS"), "BvS");
		eq(new DirectedLabeledEdge<String, String>(parentNode,childNode, " "), " ");
		eq(new DirectedLabeledEdge<String, String>(parentNode,childNode, ""), ""); // label is empty string 
	}
	
	@Test
	public void testConstructorException() {
		try {
			new DirectedLabeledEdge<String, String>(null, childNode, "BvS");
			fail("Does not throw when parentNode is null");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			new DirectedLabeledEdge<String, String>(parentNode, null, "BvS");
			fail("Does not throw when parentNode is null");
		} catch(IllegalArgumentException e) {
		}
		
		try {
			new DirectedLabeledEdge<String, String>(parentNode, childNode, null);
			fail("Does not throw when parentNode is null");
		} catch(IllegalArgumentException e) {
		}
	}

///////////////////////////////////////////////////////////////////////////////////////
////	getParentNode/getChildrenNode
///////////////////////////////////////////////////////////////////////////////////////
	@Test 
	public void testGetParentAndGetChildren() {
		assert(testEdge.getParentNode().equals(parentNode));
		assert(testEdge.getChildrenNode().equals(childNode));
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////	test Equal
///////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This test check is equals is reflexive. In other words that a.equals(a)
	 * is always true.
	 */
	@Test
	public void testEqualsReflexive() {
		assertEquals(testEdge, testEdge);
	}
	
	/**
	 * This test check is equals is reflexive with different objects.
	 */
	@Test
	public void testEqualsReflexiveDifferentObjects() {
		assertEquals(testEdge, testEdge2);
		assertEquals(testEdge2, testEdge3);
		assertEquals(testEdge, testEdge3);
	}
}