package com.turbulence6th.reversi.ai;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ReversiAI {

	private int[][] board;

	private List<int[]> spaces;

	public ReversiAI(int[][] board) {
		this.board = board;
		this.spaces = new LinkedList<>();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board[x][y] == 0) {
					spaces.add(new int[] { x, y });
				}
			}
		}
	}

	public int[] play() {
		List<Move> moves = getValidMoves(board, spaces, 1);

		if (moves.isEmpty()) {
			return null;
		}

		/**
		 * Try Minimax
		 */
		if (spaces.size() <= 10) {
			Node node = minimax(1, board, spaces, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
//			System.out.println(node);
			return node.getMove().getPosition();
		}

		// Heuristic Approachs

		/**
		 * If you can catch the corner
		 */
		Queue<Move> corners = getCorners(moves);
		if (!corners.isEmpty()) {
			return corners.poll().getPosition();
		}

		Queue<Move> edges = getEdges(moves);

		/**
		 * Take neighbor edge opponents on the edge if there is an opponent on near and
		 * there is not an opponent on another side
		 */
		for (Move move : edges) {
			int[] position = move.getPosition();

			/**
			 * Left Edge or Right Edge
			 */
			if (position[0] == 0 || position[0] == 7) {
				for (int[] origin : move.getOrigins()) {
					if (origin[0] == position[0]) {
						if (origin[1] == 0 || origin[1] == 7) {
							return position;
						}

						int diff = origin[1] - position[1];
						int y = origin[1];
						if (diff < 0) {
							if (board[position[0]][position[1] + 1] != -1) {
								y--;
								while (y >= 0) {
									if (board[position[0]][y] == 1) {
										y--;
										continue;
									}

									else if (board[position[0]][y] == -1) {
										break;
									}

									else {
										return position;
									}
								}
							}
						}

						else if (diff > 0) {
							if (board[position[0]][position[1] - 1] != -1) {
								y++;
								while (y < 8) {
									if (board[position[0]][y] == 1) {
										y++;
										continue;
									}

									else if (board[position[0]][y] == -1) {
										break;
									}

									else {
										return position;
									}
								}
							}
						}
					}
				}
			}

			/**
			 * Up or Down Edge
			 */
			else if (position[1] == 0 || position[1] == 7) {
				for (int[] origin : move.getOrigins()) {
					if (origin[1] == position[1]) {
						if (origin[0] == 0 || origin[0] == 7) {
							return position;
						}

						int diff = origin[0] - position[0];
						int x = origin[0];
						if (diff < 0) {
							if (board[position[0] + 1][position[1]] != -1) {
								x--;
								while (x >= 0) {
									if (board[x][position[1]] == 1) {
										x--;
										continue;
									}

									else if (board[x][position[1]] == -1) {
										break;
									}

									else {
										return position;
									}
								}
							}
						}

						else if (diff > 0) {
							if (board[position[0] - 1][position[1]] != -1) {
								x++;
								while (x < 8) {
									if (board[x][position[1]] == 1) {
										x++;
										continue;
									}

									else if (board[x][position[1]] == -1) {
										break;
									}

									else {
										return position;
									}
								}
							}
						}
					}
				}
			}

		}

		/**
		 * On the edge if the opponent is on the both side catch it
		 */
		for (Move move : edges) {
			int[] position = move.getPosition();
			int[][] tempBoard = makeMove(board, move, 1);

			/**
			 * Left Edge or Right Edge
			 */
			if (position[0] == 0 || position[0] == 7) {
				int x = position[0];
				int y = position[1];

				y--;
				while (y >= 0) {
					if (tempBoard[x][y] == -1) {
						y = position[1];
						y++;
						while (y < 8) {
							if (tempBoard[x][y] == -1) {
								return position;
							}

							else if (tempBoard[x][y] == 1) {
								y++;
								continue;
							}

							else {
								break;
							}
						}

						break;
					}

					else if (tempBoard[x][y] == 1) {
						y--;
						continue;
					}

					else {
						break;
					}
				}

			}

			/**
			 * Up Edge or Down Edge
			 */
			else if (position[1] == 0 || position[1] == 7) {
				int x = position[0];
				int y = position[1];

				x--;
				while (x >= 0) {
					if (tempBoard[x][y] == -1) {
						x = position[0];
						x++;
						while (x < 8) {
							if (tempBoard[x][y] == -1) {
								return position;
							}

							else if (tempBoard[x][y] == 1) {
								x++;
								continue;
							}

							else {
								break;
							}
						}

						break;
					}

					else if (tempBoard[x][y] == 1) {
						x--;
						continue;
					}

					else {
						break;
					}
				}
			}

		}

		/**
		 * Take the edge if no near opponent Dont make 1 and 3 hole
		 */
		for (Move move : edges) {
			int[] position = move.getPosition();
			int x = position[0];
			int y = position[1];

			/**
			 * Left Edge or Right Edge
			 */
			if (x == 0 || x == 7) {

				/**
				 * Empty Edge
				 */
				if (board[x][y - 1] == 0 && board[x][y + 1] == 0) {

					/**
					 * is Hole?
					 */
					if ((y >= 2 && board[x][y - 2] == 1) || (y < 5 && board[x][y + 2] == 1)) {
						continue;
					}

					/**
					 * is Triple Hole?
					 */
					else if ((y >= 4 && board[x][y - 4] == 1 && board[x][y - 3] == 0 && board[x][y - 2] == 0)
							|| (y < 4 && board[x][y + 4] == 1 && board[x][y + 3] == 0 && board[x][y + 2] == 0)) {
						continue;
					}

					return position;
				}

				/**
				 * My Edge
				 */
				else if (board[x][y - 1] == 1) {

					/**
					 * Safe
					 */
					if (board[x][y + 1] == 1) {
						return position;
					}

					/**
					 * is Hole?
					 */
					else if (y < 6 && board[x][y + 1] == 0 && board[x][y + 2] == 1) {
						continue;
					}

					/**
					 * Safe
					 */
					else if (board[x][y + 1] == 0) {
						return position;
					}

				}

				else if (board[x][y + 1] == 1 && board[x][y - 1] != -1) {

					/**
					 * Safe
					 */
					if (board[x][y - 1] == 1) {
						return position;
					}

					/**
					 * is Hole?
					 */
					else if (y >= 2 && board[x][y - 1] == 0 && board[x][y - 2] == 1) {
						continue;
					}

					/**
					 * Safe
					 */
					else if (board[x][y - 1] == 0) {
						return position;
					}

				}
			}

			/**
			 * Up Edge or Down Edge
			 */
			else if (y == 0 || y == 7) {

				/**
				 * Empty Edge
				 */
				if (board[x - 1][y] == 0 && board[x + 1][y] == 0) {

					/**
					 * is Hole?
					 */
					if ((x >= 2 && board[x - 2][y] == 1) || (x < 5 && board[x + 2][y] == 1)) {
						continue;
					}

					/**
					 * is Triple Hole?
					 */
					else if ((x >= 4 && board[x - 4][y] == 1 && board[x - 3][y] == 0 && board[x - 2][y] == 0)
							|| (x < 4 && board[x + 4][y] == 1 && board[x + 3][y] == 0 && board[x + 2][y] == 0)) {
						continue;
					}

					return position;

				}

				/**
				 * My Edge
				 */
				else if (board[x - 1][y] == 1) {

					/**
					 * Safe
					 */
					if (board[x + 1][y] == 1) {
						return position;
					}

					/**
					 * is Hole?
					 */
					else if (x < 6 && board[x + 1][y] == 0 && board[x + 2][y] == 1) {
						continue;
					}

					/**
					 * Safe
					 */
					else if (board[x + 1][y] == 0) {
						return position;
					}

				}

				else if (board[x + 1][y] == 1 && board[x - 1][y] != -1) {

					/**
					 * Safe
					 */
					if (board[x - 1][y] == 1) {
						return position;
					}

					/**
					 * is Hole?
					 */
					else if (x >= 2 && board[x - 1][y] == 0 && board[x - 2][y] == 1) {
						continue;
					}

					/**
					 * Safe
					 */
					else if (board[x - 1][y] == 0) {
						return position;
					}

				}
			}
		}

		Queue<Move> centers = getCenters(moves);
		if (!centers.isEmpty()) {
			return centers.poll().getPosition();
		}

		Queue<Move> secondEdges = get2ndEdge(board, moves);
		if (!secondEdges.isEmpty()) {
			return secondEdges.poll().getPosition();
		}

		/**
		 * Heuristic Minimax
		 */
		Node node = minimax(1, board, spaces, Integer.MIN_VALUE, Integer.MAX_VALUE, 6);
//		System.out.println(node);

		return node.getMove().getPosition();
	}

	private static Queue<Move> getCorners(List<Move> moves) {
		Queue<Move> corners = new PriorityQueue<>();
		for (Move move : moves) {
			if (move.getPosition()[0] == 0 && move.getPosition()[1] == 0) {
				corners.add(move);
			}

			else if (move.getPosition()[0] == 0 && move.getPosition()[1] == 7) {
				corners.add(move);
			}

			else if (move.getPosition()[0] == 7 && move.getPosition()[1] == 0) {
				corners.add(move);
			}

			else if (move.getPosition()[0] == 7 && move.getPosition()[1] == 7) {
				corners.add(move);
			}
		}

		return corners;
	}

	private static Queue<Move> getEdges(List<Move> moves) {
		Queue<Move> edges = new PriorityQueue<>();
		for (Move move : moves) {
			if (move.getPosition()[0] == 0 && move.getPosition()[1] == 0) {
				continue;
			}

			else if (move.getPosition()[0] == 0 && move.getPosition()[1] == 7) {
				continue;
			}

			else if (move.getPosition()[0] == 7 && move.getPosition()[1] == 0) {
				continue;
			}

			else if (move.getPosition()[0] == 7 && move.getPosition()[1] == 7) {
				continue;
			}

			else if (move.getPosition()[0] == 0) {
				edges.add(move);
			}

			else if (move.getPosition()[0] == 7) {
				edges.add(move);
			}

			else if (move.getPosition()[1] == 0) {
				edges.add(move);
			}

			else if (move.getPosition()[1] == 7) {
				edges.add(move);
			}
		}

		return edges;
	}

	private static Queue<Move> getCenters(List<Move> moves) {
		Queue<Move> centers = new PriorityQueue<>();
		for (Move move : moves) {
			int x = move.getPosition()[0];
			int y = move.getPosition()[1];
			if (x >= 2 && x <= 5 && y >= 2 && y <= 5) {
				centers.add(move);
			}
		}

		return centers;
	}

	private static Queue<Move> get2ndEdge(int[][] board, List<Move> moves) {
		Queue<Move> centers = new PriorityQueue<>();
		for (Move move : moves) {
			int x = move.getPosition()[0];
			int y = move.getPosition()[1];
			if (!((board[0][0] == 0 && x == 1 && y == 1) || (board[0][7] == 0 && x == 1 && y == 6)
					|| (board[7][0] == 0 && x == 6 && y == 1) || (board[7][7] == 0 && x == 6 && y == 6))) {
				if ((x >= 1 && x <= 6 && (y == 1 || y == 6)) || (y >= 1 && y <= 6 && (x == 1 || x == 6))) {
					centers.add(move);
				}
			}
		}

		return centers;
	}

	private static int[][] makeMove(int[][] board, Move move, int player) {
		board = clone(board);
		int[] position = move.getPosition();
		board[position[0]][position[1]] = player;
		for (int[] flip : move.getFlips()) {
			board[flip[0]][flip[1]] = player;
		}

		return board;
	}

	private static int[][] clone(int[][] board) {
		return Arrays.stream(board).map(e -> e.clone()).toArray(int[][]::new);
	}

	private static List<Move> getValidMoves(int[][] board, List<int[]> spaces, int player) {
		List<Move> moves = new LinkedList<>();
		for (int[] position : spaces) {
			Move move = new Move(position);
			getFlips(board, move, player);
			if (!move.getFlips().isEmpty()) {
				moves.add(move);
			}
		}

		return moves;
	}

	private static void getFlips(int[][] board, Move position, int player) {
		checkFlipLeft(board, position, player);
		checkFlipRight(board, position, player);
		checkFlipUp(board, position, player);
		checkFlipDown(board, position, player);
		checkFlipUpLeft(board, position, player);
		checkFlipUpRight(board, position, player);
		checkFlipDownLeft(board, position, player);
		checkFlipDownRight(board, position, player);
	}

	private static void checkFlipLeft(int[][] board, Move move, int player) {
		int x = move.getPosition()[0];
		int y = move.getPosition()[1];
		boolean search = false;
		x--;
		List<int[]> positions = new LinkedList<>();
		while (x >= 0) {
			if (board[x][y] == -player) {
				search = true;
				positions.add(new int[] { x, y });
			}

			else if (search && board[x][y] == player) {
				move.getFlips().addAll(positions);
				move.getOrigins().add(new int[] { x, y });
				return;
			}

			else {
				return;
			}

			x--;
		}
	}

	private static void checkFlipRight(int[][] board, Move move, int player) {
		int x = move.getPosition()[0];
		int y = move.getPosition()[1];
		boolean search = false;
		x++;
		List<int[]> positions = new LinkedList<>();
		while (x < 8) {
			if (board[x][y] == -player) {
				search = true;
				positions.add(new int[] { x, y });
			}

			else if (search && board[x][y] == player) {
				move.getFlips().addAll(positions);
				move.getOrigins().add(new int[] { x, y });
				return;
			}

			else {
				return;
			}

			x++;
		}
	}

	private static void checkFlipUp(int[][] board, Move move, int player) {
		int x = move.getPosition()[0];
		int y = move.getPosition()[1];
		boolean search = false;
		y--;
		List<int[]> positions = new LinkedList<>();
		while (y >= 0) {
			if (board[x][y] == -player) {
				search = true;
				positions.add(new int[] { x, y });
			}

			else if (search && board[x][y] == player) {
				move.getFlips().addAll(positions);
				move.getOrigins().add(new int[] { x, y });
				return;
			}

			else {
				return;
			}

			y--;
		}
	}

	private static void checkFlipDown(int[][] board, Move move, int player) {
		int x = move.getPosition()[0];
		int y = move.getPosition()[1];
		boolean search = false;
		y++;
		List<int[]> positions = new LinkedList<>();
		while (y < 8) {
			if (board[x][y] == -player) {
				search = true;
				positions.add(new int[] { x, y });
			}

			else if (search && board[x][y] == player) {
				move.getFlips().addAll(positions);
				move.getOrigins().add(new int[] { x, y });
				return;
			}

			else {
				return;
			}

			y++;
		}
	}

	private static void checkFlipUpLeft(int[][] board, Move move, int player) {
		int x = move.getPosition()[0];
		int y = move.getPosition()[1];
		boolean search = false;
		x--;
		y--;
		List<int[]> positions = new LinkedList<>();
		while (x >= 0 && y >= 0) {
			if (board[x][y] == -player) {
				search = true;
				positions.add(new int[] { x, y });
			}

			else if (search && board[x][y] == player) {
				move.getFlips().addAll(positions);
				move.getOrigins().add(new int[] { x, y });
				return;
			}

			else {
				return;
			}

			x--;
			y--;
		}
	}

	private static void checkFlipUpRight(int[][] board, Move move, int player) {
		int x = move.getPosition()[0];
		int y = move.getPosition()[1];
		boolean search = false;
		x++;
		y--;
		List<int[]> positions = new LinkedList<>();
		while (x < 8 && y >= 0) {
			if (board[x][y] == -player) {
				search = true;
				positions.add(new int[] { x, y });
			}

			else if (search && board[x][y] == player) {
				move.getFlips().addAll(positions);
				move.getOrigins().add(new int[] { x, y });
				return;
			}

			else {
				return;
			}

			x++;
			y--;
		}
	}

	private static void checkFlipDownLeft(int[][] board, Move move, int player) {
		int x = move.getPosition()[0];
		int y = move.getPosition()[1];
		boolean search = false;
		x--;
		y++;
		List<int[]> positions = new LinkedList<>();
		while (x >= 0 && y < 8) {
			if (board[x][y] == -player) {
				search = true;
				positions.add(new int[] { x, y });
			}

			else if (search && board[x][y] == player) {
				move.getFlips().addAll(positions);
				move.getOrigins().add(new int[] { x, y });
				return;
			}

			else {
				return;
			}

			x--;
			y++;
		}
	}

	private static void checkFlipDownRight(int[][] board, Move move, int player) {
		int x = move.getPosition()[0];
		int y = move.getPosition()[1];
		boolean search = false;
		x++;
		y++;
		List<int[]> positions = new LinkedList<>();
		while (x < 8 && y < 8) {
			if (board[x][y] == -player) {
				search = true;
				positions.add(new int[] { x, y });
			}

			else if (search && board[x][y] == player) {
				move.getFlips().addAll(positions);
				move.getOrigins().add(new int[] { x, y });
				return;
			}

			else {
				return;
			}

			x++;
			y++;
		}
	}

	private boolean isFinished(int[][] board, List<int[]> spaces) {
		return spaces.isEmpty()
				|| (getValidMoves(board, spaces, 1).isEmpty() && getValidMoves(board, spaces, -1).isEmpty());
	}

	private int point(int[][] board) {
		return Arrays.stream(board).map(row -> Arrays.stream(row).sum()).mapToInt(Integer::intValue).sum();
	}

	private Node minimax(int player, int[][] board, List<int[]> spaces, int alpha, int beta, int depth) {

		if (depth == 0 || isFinished(board, spaces)) {
			return new Node(point(board));
		}

		Node score = null, temp = null;
		List<Move> validMoves = getValidMoves(board, spaces, player);
		if (validMoves.isEmpty()) {
			if (player == 1) {
				temp = minimax(-1, board, spaces, alpha, beta, depth - 1);

				if (temp.getPoint() > alpha) {
					alpha = temp.getPoint();
					score = temp;
				}
			}

			else if (player == -1) {
				temp = minimax(1, board, spaces, alpha, beta, depth - 1);

				if (temp.getPoint() < beta) {
					beta = temp.getPoint();
					score = temp;
				}
			}
		}

		for (Move move : validMoves) {

			int[][] playedBoard = makeMove(board, move, player);

			List<int[]> remainingSpaces = new LinkedList<>(spaces);
			remainingSpaces.removeIf(s -> {
				int[] p = move.getPosition();
				if (s[0] == p[0] && s[1] == p[1]) {
					return true;
				}

				return false;
			});

			if (player == 1) {
				temp = minimax(-1, playedBoard, remainingSpaces, alpha, beta, depth - 1);

				if (temp.getPoint() > alpha) {
					alpha = temp.getPoint();
					score = temp;
					score.setMove(move);
				}
			}

			else if (player == -1) {
				temp = minimax(1, playedBoard, remainingSpaces, alpha, beta, depth - 1);

				if (temp.getPoint() < beta) {
					beta = temp.getPoint();
					score = temp;
					score.setMove(move);
				}
			}
		}

		return score != null ? score : temp;
	}

}
