package com.turbulence6th.reversi.ai;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		
		int[][] board = {
				{0, 0, 0, 0, 0, 0, 0 ,0},	
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0},
				{0, 0, 0, -1, 1, 0, 0 ,0},
				{0, 0, 0, 1, -1, -1, 0 ,0},
				{0, 0, 0, 0, 0, 1, 0 ,0},
				{0, 0, 0, 0, 0, -1, 0 ,0},
				{0, 0, 0, 0, 0, 0, 0 ,0}
		};
		
		ReversiAI ai = new ReversiAI(board);
		System.out.println(Arrays.toString(ai.play()));
		
	}
	
}
