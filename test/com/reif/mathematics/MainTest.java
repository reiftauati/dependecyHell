package com.reif.mathematics;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class MainTest {

	static Lineage l;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		l = new Lineage("4", "3");
		Main.masterList.add(l);
	}

	@Test
	public void testIsInputaRoot_shouldReturnTrueWithInputDependent_EqualsRootOfLineage() {
		assertTrue(Main.isInputaRoot("3", "2"));
	}
	
	@Test
	public void testIsInputaRoot_shouldReturnFalseWithInputDependent_NotEqualRootOfLineage() {
		assertFalse(Main.isInputaRoot("5", "4"));
	}

	@Test
	public void testIsInputaDependent_shouldReturnTrueWithInputRoot_EqualsDependentOfLineage() {
		assertTrue(Main.isInputaDependent("5", "4"));
	}
	
	@Test
	public void testIsInputaDependent_shouldReturnTrueWithInputRoot_NotEqualDependentOfLineage() {
		assertFalse(Main.isInputaDependent("4", "5"));
	}

	@Test
	public void testReturnRelevantIndex_shouldReturnIndexOfLineageBasedOn_RootInput() {
		assertEquals(0, Main.returnRelevantIndex("3", "root"));
	}
	
	@Test
	public void testReturnRelevantIndex_shouldReturnIndexOfLineageBasedOn_DependentInput() {
		assertEquals(0, Main.returnRelevantIndex("4", "dependent"));
	}

	@Test
	public void testUpdateTemporaryDummyList_shouldEqual4WhenUpdated() {
		Lineage l = new Lineage("2","1");
		Main.masterList.add(l);
		Main.updateTemporaryDummyList();
		int size = Main.dummyList.size();
		assertEquals(4,size);
	}

	@Test
	public void testIsInputInALineage_shouldReturnTrueWhenInput_IsInALineage() {
		assertTrue(Main.isInputInALineage("3", null));
	}
	
	@Test
	public void testIsInputInALineage_shouldReturnFalseWhenInput_IsNotInALineage() {
		assertFalse(Main.isInputInALineage("4400", null));
	}

	@Test
	public void testCombineDependentLineages_sizeOfMasterListShouldShrinkBecauseOfCombinedLineages() {
		Lineage l = new Lineage("3","2");
		Main.masterList.add(l);
		String[] pair = { "3", "2" };
		Main.allPairs.add(pair);
		Main.combineDependentLineages();
		assertEquals(2, Main.masterList.size());
	}

}
