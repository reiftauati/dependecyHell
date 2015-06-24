package com.reif.mathematics;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class LineageTest {
	
	static Lineage l;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//initialize a Lineage for use in testing
		l = new Lineage("3", "2");
	}

	@Test
	public void testGetRoot_shouldReturn2() {
		assertEquals("2", l.getRoot());
	}

	@Test
	public void testGetDependent_shouldReturn3() {
		assertEquals("3", l.getDependent());
	}

	@Test
	public void testIsLoop_shouldReturnTrue_WithRootCycle() {
		assertTrue(l.isLoop("3", "2", "root"));
	}
	
	@Test
	public void testIsLoop_shouldReturnTrue_WithDependentCycle() {
		assertTrue(l.isLoop("2", "3", "dependent"));
	}

}
