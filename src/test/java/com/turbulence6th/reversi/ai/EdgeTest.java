package com.turbulence6th.reversi.ai;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Test;

public class EdgeTest {
	
	@Test
	public void catchNeighbor() {
		
		int[][] board = {
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },	
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, -1, 1, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 1, -1, 0, 0, 0 ,0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertArrayEquals(new int[] { 7, 4 }, ai.play()); 
		
	}
	
	@Test
	public void dontCatchNeighbor() {
		
		int[][] board = {
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },	
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, -1, -1, 1, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 1, -1, 0, -1, 0 ,0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertThat(new int[] { 7, 4 }, IsNot.not(IsEqual.equalTo(ai.play())));
		
	}
	
	@Test
	public void catchNeighborFromMid() {
		
		int[][] board = {
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },	
				{ 0, 0, 1, -1, -1, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 1, 0, 0, 0 ,0 },
				{ 0, 0, 0, -1, 0, 0, 0 ,0 },
				{ 0, 0, 1, 0, 0, 0, 0 ,0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertArrayEquals(new int[] { 7, 3 }, ai.play()); 
		
	}
	
	@Test
	public void catchEdge() {
		
		int[][] board = {
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },	
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, -1, -1, 1, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 1, 0 ,0 },
				{ 0, 0, 0, 0, 0, -1, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertArrayEquals(new int[] { 7, 5 }, ai.play()); 
		
	}
	
	@Test
	public void dontCatchOneHoleEdge() {
		
		int[][] board = {
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },	
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, -1, -1, 1, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 1, 0 ,0 },
				{ 0, 0, 0, 0, 0, -1, 0 ,0 },
				{ 0, 0, 0, 1, 0, 0, 0 ,0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertThat(new int[] { 7, 5 }, IsNot.not(IsEqual.equalTo(ai.play())));
		
	}
	
	@Test
	public void dontMakeNeighborhood() {
		
		int[][] board = {
				{ 0, 0, 1, 0, -1, 0, 0 ,0 },	
				{ 0, 0, 0, -1, 0, 0, 0 ,0 },
				{ 0, 0, -1, 1, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertThat(new int[] { 0, 3 }, IsNot.not(IsEqual.equalTo(ai.play())));
		
	}
	
	@Test
	public void dontMakeNeighborhood2() {
		
		int[][] board = {
				{ 0, 0, 0, 0, -1, 0, 0 ,0 },	
				{ 0, 0, 0, -1, 0, 0, 0 ,0 },
				{ 0, 0, -1, 1, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertThat(new int[] { 0, 3 }, IsNot.not(IsEqual.equalTo(ai.play())));
		
	}
	
	@Test
	public void beAtMiddle() {
		
		int[][] board = {
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },	
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 , 0 },
				{ 0, 0, 0, 0, 0, 0, 0 , 0 },
				{ 0, 0, 0, 1, -1, 0, 0 ,0 },
				{ 0, 0, -1, 1, -1, -1, 0 ,0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertThat(new int[] { 7, 6 }, IsNot.not(IsEqual.equalTo(ai.play())));
		
	}
	
	@Test
	public void dontCatchOneHoleEdge2() {
		
		int[][] board = {
				{ 0, 0, 1, 0, 0, 1, 0, 0 },	
				{ 0, 0, 0,-1, 0, 0, 0, 0 },
				{ 0, 0, 0, 1,-1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertThat(new int[] { 0, 3 }, IsNot.not(IsEqual.equalTo(ai.play())));
		
	}

	
}
