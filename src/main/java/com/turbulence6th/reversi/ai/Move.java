package com.turbulence6th.reversi.ai;

import java.util.LinkedList;
import java.util.List;

public class Move implements Comparable<Move> {

	private int[] position;
	
	private List<int[]> flips;
	
	private List<int[]> origins;
	
	public Move(int[] position) {
		this.position = position;
		this.flips = new LinkedList<>();
		this.origins = new LinkedList<>();
	}
	
	@Override
	public int compareTo(Move move) {
		return move.flips.size() - flips.size();
	}
	
	@Override
	public String toString() {
		return String.format("[x: %d, y: %d]", position[0], position[1]);
	}
	
	public int[] getPosition() {
		return position;
	}
	
	public List<int[]> getFlips() {
		return flips;
	}
	
	public List<int[]> getOrigins() {
		return origins;
	}
	
}
