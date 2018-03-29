package com.turbulence6th.reversi.ai;

public class Node implements Comparable<Node> {

	private int point;
	
	private Move move;
	
	public Node(int point) {
		this.point = point;
	}
	
	@Override
	public int compareTo(Node node) {
		return point - node.point;
	}
	
	@Override
	public String toString() {
		return String.format("[point: %d, move: %s]", point, move);
	}
	
	public int getPoint() {
		return point;
	}
	
	public void setMove(Move move) {
		this.move = move;
	}
	
	public Move getMove() {
		return move;
	}
	
}
