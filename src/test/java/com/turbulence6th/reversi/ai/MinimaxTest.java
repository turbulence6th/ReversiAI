package com.turbulence6th.reversi.ai;

import org.junit.Test;

public class MinimaxTest {

	@Test
	public void isBest() {
		
		int[][] board = {
				{ -1,  1,  1,  1,  1, -1, -1 , -1 },	
				{  1, -1,  1,  1,  1,  1, -1 ,  1 },
				{  1,  1, -1, -1, -1, -1,  1 ,  1 },
				{  1,  1, -1,  1, -1, -1,  1 ,  1 },
				{  1,  1, -1, -1,  1, -1,  1 ,  1 },
				{  1, -1, -1, -1,  1,  1, -1 ,  1 },
				{  1, -1, -1, -1, -1, -1,  1 ,  1 },
				{ -1,  1,  1,  1,  1,  0,  0 ,  1 }
		};	
		
		ReversiAI ai = new ReversiAI(board);
		
		System.out.println(ai.play());
		
	}
	
}
