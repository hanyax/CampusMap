package hw5.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import hw4.test.SpecificationTests;
import hw5.DirectedLabeledGraph;
import hw5.DirectedLabeledEdge;

public class DirectedLabeledGraphTest {
	/**
	* checks that Java asserts are enabled, and exits if not
	*/
	@Before
	public void testAssertsEnabled() {
		 SpecificationTests.checkAssertsEnabled();
	}
	
	private String testNodeA = new String("Batman");
	private String testNodeB = new String("Superman");
	private String testNodeC = new String("Wonderwoman");
	private String testNodeD = new String("Flash");
	
///////////////////////////////////////////////////////////////////////////////////////
////	Test addNode
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddNode() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		graph.addNode(testNodeA);
		assertEquals(graph.getAllParents().size(), 1);
		graph.addNode(testNodeB);
		assertEquals(graph.getAllParents().size(), 2);
		graph.addNode(testNodeC);
		assertEquals(graph.getAllParents().size(), 3);
		graph.addNode(testNodeD);
		assertEquals(graph.getAllParents().size(), 4);
		
		assert(graph.getAllParents().contains(testNodeA));
		assert(graph.getAllParents().contains(testNodeB));
		assert(graph.getAllParents().contains(testNodeC));
		assert(graph.getAllParents().contains(testNodeD));
	}
	
	@Test
	public void testAddNodeException() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		try {
			graph.addNode(null);
			fail("Does not throw when add a null String");
		} catch(IllegalArgumentException e){
		}
		
		graph.addNode(testNodeA);
		try {
			graph.addNode(testNodeA);
			fail("Does not throw when add a existing String");
		} catch(IllegalArgumentException e) {
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////	Test getAllParents()
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testGetAllParents() {	
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		graph.addNode(testNodeA);
		graph.addNode(testNodeB);
		graph.addNode(testNodeC);
		graph.addNode(testNodeD);
		
		assert(graph.getAllParents().contains(testNodeA));
		assert(graph.getAllParents().contains(testNodeB));
		assert(graph.getAllParents().contains(testNodeC));
		assert(graph.getAllParents().contains(testNodeD));
		assertEquals(graph.getAllParents().size(), 4);
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test addEdge / getAllEdge
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddEdge() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		graph.addNode(testNodeA);
		DirectedLabeledEdge<String, String> edge = new DirectedLabeledEdge<String, String>(testNodeA, testNodeA, "BvS");
		graph.addEdge(testNodeA, testNodeA, "BvS");
		assert(graph.getAllEdges(testNodeA).containsKey(testNodeA) &&
			   graph.getAllEdges(testNodeA).get(testNodeA).contains(edge));
		
		DirectedLabeledEdge<String, String> edge2 = new DirectedLabeledEdge<String, String>(testNodeA, testNodeB, "BvS");
		graph.addEdge(testNodeA, testNodeB, "BvS");
		assert(graph.getAllEdges(testNodeA).containsKey(testNodeB) &&
				graph.getAllEdges(testNodeA).get(testNodeB).contains(edge2) );
		
		DirectedLabeledEdge<String, String> edge3 = new DirectedLabeledEdge<String, String> (testNodeA, testNodeB, "JL");
		graph.addEdge(testNodeA, testNodeB, "JL");
		assert(graph.getAllEdges(testNodeA).containsKey(testNodeB) &&
				graph.getAllEdges(testNodeA).get(testNodeB).contains(edge3));
		
	}
	
	@Test
	public void testGetAllEdge() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		graph.addNode(testNodeA);
		graph.addEdge(testNodeA, testNodeA, "BvS");
		DirectedLabeledEdge<String, String> edge = new DirectedLabeledEdge<String, String> (testNodeA, testNodeA, "BvS");
		
		graph.addEdge(testNodeA, testNodeB, "BvS");
		DirectedLabeledEdge<String, String> edge2 = new DirectedLabeledEdge<String, String>(testNodeA, testNodeB, "BvS");
		
		graph.addEdge(testNodeA, testNodeB, "JL");
		DirectedLabeledEdge<String, String> edge3 = new DirectedLabeledEdge<String, String>(testNodeA, testNodeB, "JL");
		
		graph.addEdge(testNodeA, testNodeC, "JL");
		DirectedLabeledEdge<String, String> edge4 = new DirectedLabeledEdge<String, String> (testNodeA, testNodeC, "JL");
		
		graph.addEdge(testNodeA, testNodeD, "JL");
		DirectedLabeledEdge<String, String> edge5 = new DirectedLabeledEdge<String, String> (testNodeA, testNodeD, "JL");
		
		assert(graph.getAllParents().contains(testNodeA));
		assert(graph.getAllParents().contains(testNodeB));
		assert(graph.getAllParents().contains(testNodeC));
		assert(graph.getAllParents().contains(testNodeD));
		assert(graph.getAllEdges(testNodeA).containsKey(testNodeA) &&
				graph.getAllEdges(testNodeA).get(testNodeA).contains(edge) );
		
		assert(graph.getAllEdges(testNodeA).containsKey(testNodeB) &&
				graph.getAllEdges(testNodeA).get(testNodeB).contains(edge2) );
		
		assert(graph.getAllEdges(testNodeA).containsKey(testNodeB) &&
				graph.getAllEdges(testNodeA).get(testNodeB).contains(edge3) );
		
		assert(graph.getAllEdges(testNodeA).containsKey(testNodeC) &&
				graph.getAllEdges(testNodeA).get(testNodeC).contains(edge4) );
		
		assert(graph.getAllEdges(testNodeA).containsKey(testNodeD) &&
				graph.getAllEdges(testNodeA).get(testNodeD).contains(edge5) );
		
		assertEquals(graph.getAllParents().size(), 4);
		assertEquals(graph.getAllEdges(testNodeA).size(), 4);
	} 
	
	@Test
	public void testAddEdgeException() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		try {
			graph.addEdge(null, testNodeA, "BvS");
			fail("Does not throw when add a null edge to graph.");
		} catch(IllegalArgumentException e){
		}
		
		try {
			graph.addEdge(testNodeA, null, "BvS");
			fail("Does not throw when add a null edge to graph.");
		} catch(IllegalArgumentException e){
		}

		try {
			graph.addEdge(testNodeA, testNodeA, null);
			fail("Does not throw when add a null edge to graph.");
		} catch(IllegalArgumentException e){
		}
	}
	
	@Test
	public void testGetAllEdgeException() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		graph.addNode(testNodeA);
		graph.addEdge(testNodeA, testNodeA, "BvS");
		graph.addEdge(testNodeA, testNodeB, "BvS");
		graph.addEdge(testNodeA, testNodeB, "JL");
		graph.addEdge(testNodeA, testNodeC, "JL");
		graph.addEdge(testNodeA, testNodeD, "JL");
		
		try {
			graph.getAllEdges(null);
			fail("Does not throw when try to get all edge for a null node to graph.");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			graph.getAllEdges(new String("Ironman"));
			fail("Does not throw when try to get all edge for node that is not in the graph.");
		} catch (IllegalArgumentException e) {
			
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test addEdge / getChildren
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testGetChildren() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		graph.addNode(testNodeA);
		graph.addEdge(testNodeA, testNodeA, "BvS");
		graph.addEdge(testNodeA, testNodeB, "BvS");
		graph.addEdge(testNodeA, testNodeB, "JL");
		graph.addEdge(testNodeA, testNodeC, "JL");
		graph.addEdge(testNodeA, testNodeD, "JL");
		
		assert(graph.getChildren(testNodeA).contains(testNodeA));
		assert(graph.getChildren(testNodeA).contains(testNodeB));
		assert(graph.getChildren(testNodeA).contains(testNodeC));
		assert(graph.getChildren(testNodeA).contains(testNodeD));
	}
	
	@Test
	public void testGetChildrenException() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		graph.addNode(testNodeA);
		graph.addEdge(testNodeA, testNodeA, "BvS");
		graph.addEdge(testNodeA, testNodeB, "BvS");
		graph.addEdge(testNodeA, testNodeB, "JL");
		graph.addEdge(testNodeA, testNodeC, "JL");
		graph.addEdge(testNodeA, testNodeD, "JL");
		
		try {
			graph.getChildren(null);
			fail("Does not through when checking for a null String");
		} catch(IllegalArgumentException e) {
		}
		
		try {
			graph.getChildren(new String("Ironman"));
			fail("Does not through when checking for a String that is not in the Graph");
		} catch(IllegalArgumentException e) {
		}
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test addEdge / isChildren
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testIsChildren() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		graph.addNode(testNodeA);
		graph.addEdge(testNodeA, testNodeA, "BvS");
		graph.addEdge(testNodeA, testNodeB, "BvS");
		graph.addEdge(testNodeA, testNodeB, "JL");
		graph.addEdge(testNodeA, testNodeC, "JL");
		graph.addEdge(testNodeA, testNodeD, "JL");
		
		assert(graph.isChildren(testNodeA, testNodeA) );
		assert(graph.isChildren(testNodeA, testNodeB));
		assert(graph.isChildren(testNodeA, testNodeC));
		assert(graph.isChildren(testNodeA, testNodeD));
	}
	
	public void testIsChildrenException() {
		DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
		graph.addNode(testNodeA);
		graph.addEdge(testNodeA, testNodeA, "BvS");
		graph.addEdge(testNodeA, testNodeB, "BvS");
		graph.addEdge(testNodeA, testNodeB, "JL");
		graph.addEdge(testNodeA, testNodeC, "JL");
		graph.addEdge(testNodeA, testNodeD, "JL");

		
		try {
			graph.isChildren(null, testNodeA);
			fail("Does not throw when ParentNode is null");
		} catch(IllegalArgumentException e) {
		}
		
		try {
			graph.isChildren(testNodeA, null);
			fail("Does not throw when childrenNode is null");
		} catch(IllegalArgumentException e) {
		}
	}
}