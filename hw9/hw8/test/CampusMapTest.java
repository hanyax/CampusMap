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
 * implementation of the CampusMap class.
 * <p>
 */
public class CampusMapTest {
	/**
	* checks that Java asserts are enabled, and exits if not
	*/
	@Before
	public void testAssertsEnabled() {
		 SpecificationTests.checkAssertsEnabled();
	}
	
	private CampusMap testMap = new CampusMap("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
	
///////////////////////////////////////////////////////////////////////////////////////
////	Test FindPath
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testFindPath() {
		assert(testMap.findPath(null, null) == null);
		assert(testMap.findPath("afdsaf", "saedwqd") == null);
		assert(testMap.findPath("CSE", "PAB") != null);
		
		assert((int) Math.round(testMap.findPath("CSE", "PAB").getStartLocationX(1)) == 2261);
		assert((int) Math.round(testMap.findPath("CSE", "PAB").getStartLocationY(1)) == 1707);
		
		assert((int) Math.round(testMap.findPath("CSE", "PAB").getDestLocationX(testMap.findPath("CSE", "PAB").getTotalTurns())) == 1561);
		assert((int) Math.round(testMap.findPath("CSE", "PAB").getDestLocationY(testMap.findPath("CSE", "PAB").getTotalTurns())) == 1698);
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////	Test ListBuildings/getBuilding names
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testListBuildings() {
		assert(testMap.listBuildingsLongName().size() == 51);
		assert(testMap.listBuildingsLongName().size() == testMap.listBuildingsShortName().size());
	}
	
	@Test 
	public void testGetBuildingName() {
		for (String building : testMap.listBuildingsLongName()) {
			assert(testMap.getBuildingShortName(building) != null);
		}
		
		for (String building : testMap.listBuildingsShortName()) {
			assert(testMap.getBuildingLongName(building) != null);
		}
		
		assert(testMap.getBuildingLongName(null) == null);
		assert(testMap.getBuildingShortName(null) == null);
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////
////	Test exists
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testExists() {
		assert(testMap.exist("CSE"));
		assert(testMap.exist("Paul G. Allen Center for Computer Science & Engineering"));
		assert(!testMap.exist("CSE II"));
		assert(!testMap.exist(null));
		
	}


	
}