package hw5.test;

import static org.junit.Assert.*;

import org.junit.Before;

import hw5.GraphNode;
import hw4.test.SpecificationTests;

import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the GraphNode class.
 * <p>
 */

public class GraphNodeTest {
	
	/**
	* checks that Java asserts are enabled, and exits if not
	*/
	@Before
	public void testAssertsEnabled() {
		 SpecificationTests.checkAssertsEnabled();
	}
	
	//Test GraphNodes
	private GraphNode testObjectA = new GraphNode("Batman");
	private GraphNode testCopyA = new GraphNode("Batman");
	private GraphNode testObjectB = new GraphNode(" ");
	private GraphNode testCopyB = new GraphNode(" ");
	private Object A = testObjectA;
	private Object B = testCopyA;
	
///////////////////////////////////////////////////////////////////////////////////////
////	Constructor
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		new GraphNode("a");
		new GraphNode(" ");
		new GraphNode("Iron Man");
	}
	
	@Test
	public void testConstructorExcepetion() {
		try {
			new GraphNode(null); // null case
			fail("Constructor should not accept null value");
		} catch(IllegalArgumentException e) {
		}
		
		try{
			new GraphNode(""); // empty string case
			fail( "Constructor should not accept empty String" );
		} catch(IllegalArgumentException e) {
		}
	}

///////////////////////////////////////////////////////////////////////////////////////
////	Test equal 
///////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This test check is equals is reflexive. In other words that a.equals(a)
	 * is always true.
	 */
	@Test
	public void testEqualsReflexive() {
		assertEquals(testObjectA, testObjectA);
		assertEquals(A, A);
	}
	
	/**
	 * This test check is equals is reflexive with different objects.
	 */
	@Test
	public void testEqualsReflexiveDifferentObjects() {
		assertEquals(testObjectA, testCopyA);
		assertEquals(A, B);
		assertEquals(testObjectA, A);
		assertEquals(testObjectA, B);
		assertEquals(testCopyA, A);
		assertEquals(testCopyA, B);
	}
	
	/**
	 * This test check is equals on transitivity.
	 */
	@Test
	public void testEqualsTransitivity() {
		GraphNode testObjectC = new GraphNode("Batman");
		
		assertEquals(testObjectA, testCopyA);
		assertEquals(testCopyA, testObjectC);
		assertEquals(testObjectA, testObjectC);
	}
///////////////////////////////////////////////////////////////////////////////////////
////	Test getNode 
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testGetNode() {
		assertEquals(testObjectA, testObjectA.getNode());
		assertEquals(testObjectA, testCopyA.getNode());
		assertEquals(testObjectB, testObjectB.getNode());
		assertEquals(testObjectB, testCopyB.getNode());
	}
		
}
