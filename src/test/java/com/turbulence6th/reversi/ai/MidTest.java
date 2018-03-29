package com.turbulence6th.reversi.ai;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Test;

public class MidTest {

	@Test
	public void dontBeTooGreedy() {
		
		int[][] board = {
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },	
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, 0, 0 ,0 },
				{ 0, 0, 0, 0, 0, -1, 0 ,0 },
				{ 0, 0, -1, -1, -1, 1, 0 ,0 },
				{ 0, 0, 0, -1, 0, 0, 0 ,0 }
		};
		
		ReversiAI ai = new ReversiAI(board);
		Assert.assertThat(new int[] { 6, 1 }, IsNot.not(IsEqual.equalTo(ai.play())));
		
	}
	
}
