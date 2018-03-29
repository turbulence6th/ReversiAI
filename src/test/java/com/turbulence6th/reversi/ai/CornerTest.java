package com.turbulence6th.reversi.ai;

import org.junit.Assert;
import org.junit.Test;

public class CornerTest {

	@Test
	public void catchDiagonal() {
		
		int[][] board = {
				{0, 1, -1, -1, -1, -1, 0 ,0},	
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 1, 0 ,0},
				{0, 0, 0, 0, 0, 0, -1 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0}
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertArrayEquals(new int[]{7, 7}, ai.play()); 
		
	}
	
	@Test
	public void catchFromEdge() {
		
		int[][] board = {
				{0, 1, -1, -1, -1, -1, 0 ,0},	
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 1, -1 ,0}
		};
		
		ReversiAI ai = new ReversiAI(board);
		
		Assert.assertArrayEquals(new int[]{7, 7}, ai.play()); 
		
	}
	
}
