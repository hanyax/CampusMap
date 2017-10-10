package hw8.test;

import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import hw4.test.SpecificationTests;
import hw5.DirectedLabeledEdge;
import hw7.MinimumCostPath;
import hw8.CampusMap;
import hw8.CampusPath;
import hw8.PathNode;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the CampusPath class.
 * <p>
 */
public class CampusPathTest {
	/**
	* checks that Java asserts are enabled, and exits if not
	*/
	@Before
	public void testAssertsEnabled() {
		 SpecificationTests.checkAssertsEnabled();
	}
	
	private CampusMap testMap = new CampusMap("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
	private CampusPath testPath1 = testMap.findPath("CSE", "PAB");
	private CampusPath testPath2 = testMap.findPath("CSE", "CSE");
	
///////////////////////////////////////////////////////////////////////////////////////
////	Constructor
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructorException() {
		try {
			new CampusPath(null);
			fail("Does not throw when path in a null list");
		} catch(IllegalArgumentException e){
		}
	}

///////////////////////////////////////////////////////////////////////////////////////
////	Test getMethods
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testGetTotalTurns() {
		assert(testPath1.getTotalTurns() == 24);
		assert(testPath2.getTotalTurns() == 0);
	}
	
	@Test 
	public void testGetDistance() {
		assert((int) Math.round(testPath1.getDistance(0)) == 18);
		assert(testPath2.getDistance(0) == 0); 
	}
	
	@Test
	public void testGetDistanceExcpetion() {
		try {
			testPath1.getDistance(-1);
			fail("Does not throw when try to find distance after -1 turns");
		} catch(IllegalArgumentException e){
		}
		
		try {
			testPath1.getDistance(100);
			fail("Does not throw when try to find distance after turns that is larger than total turns");
		} catch(IllegalArgumentException e){
		}
		
		try {
			testPath2.getDistance(1);
			fail("Does not throw when try to find distance that is larger than total turns");
		} catch(IllegalArgumentException e){
		}
	}
	
	@Test
	public void testGetLocaition() {
		assert((int) Math.round(testPath1.getStartLocationX(1)) == 2261);
		assert((int) Math.round(testPath1.getStartLocationY(1)) == 1707);
		
		assert((int) Math.round(testPath1.getStartLocationX(testPath1.getTotalTurns())) == 1561);
		assert((int) Math.round(testPath1.getStartLocationY(testPath1.getTotalTurns())) == 1682);
		
		assert((int) Math.round(testPath2.getStartLocationX(0)) == 2260);
		assert((int) Math.round(testPath2.getStartLocationY(0)) == 1716);
		
		assert((int) Math.round(testPath1.getDestLocationX(0)) == 2261);
		assert((int) Math.round(testPath1.getDestLocationY(0)) == 1707);
		
		assert((int) Math.round(testPath1.getDestLocationX(testPath1.getTotalTurns())) == 1561);
		assert((int) Math.round(testPath1.getDestLocationY(testPath1.getTotalTurns())) == 1698);
		
		assert((int) Math.round(testPath2.getDestLocationX(0)) == 2260);
		assert((int) Math.round(testPath2.getDestLocationY(0)) == 1716);
	}
	
	@Test
	public void testGetLocationException() {
		try {
			testPath1.getStartLocationX(-1);
			fail("Does not throw when try to find distance after -1 turns");
		} catch(IllegalArgumentException e){
		}
		
		try {
			testPath1.getStartLocationY(-1);
			fail("Does not throw when try to find distance after -1 turns");
		} catch(IllegalArgumentException e){
		}
		
		try {
			testPath1.getDestLocationX(-1);
			fail("Does not throw when try to find distance after -1 turns");
		} catch(IllegalArgumentException e){
		}
		
		try {
			testPath1.getDestLocationY(-1);
			fail("Does not throw when try to find distance after -1 turns");
		} catch(IllegalArgumentException e){
		}
		
		try {
			testPath1.getStartLocationX(100);
			fail("Does not throw when try to find distance after turn which is more than the total turn");
		} catch(IllegalArgumentException e){
		}
		
		try {
			testPath1.getStartLocationY(100);
			fail("Does not throw when try to find distance after turn which is more than the total turn");
		} catch(IllegalArgumentException e){
		}
		
		try {
			testPath1.getDestLocationX(100);
			fail("Does not throw when try to find distance after turn which is more than the total turn");
		} catch(IllegalArgumentException e){
		}
		
		try {
			testPath1.getDestLocationY(100);
			fail("Does not throw when try to find distance after turn which is more than the total turn");
		} catch(IllegalArgumentException e){
		}
	}


}
