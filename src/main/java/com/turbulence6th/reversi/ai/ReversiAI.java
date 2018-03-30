package com.turbulence6th.reversi.ai;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
		return minimax(1, board, spaces, Integer.MIN_VALUE, Integer.MAX_VALUE,
				spaces.size() <= 11 ? Integer.MAX_VALUE : 6).getMove().getPosition();

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

	private int edgePoint(int[][] board) {
		int total = 0;
		total += 5 * board[0][0] + 5 * board[0][7] + 5 * board[7][0] + 5 * board[7][7];
		for (int i = 1; i < 7; i++) {
			total += board[i][0];
		}

		for (int i = 1; i < 7; i++) {
			total += board[0][i];
		}

		for (int i = 1; i < 7; i++) {
			total += board[7][i];
		}

		for (int i = 1; i < 7; i++) {
			total += board[i][7];
		}

		return total;
	}

	private Node minimax(int player, int[][] board, List<int[]> spaces, int alpha, int beta, int depth) {

		if (isFinished(board, spaces)) {
			return new Node(point(board));
		}

		else if (depth == 0) {
			return new Node(edgePoint(board));
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
				
				/**
				 * Alpha Beta Prunning
				 */
				if (alpha >= beta) {
					return score;
	            }
			}

			else if (player == -1) {
				temp = minimax(1, board, spaces, alpha, beta, depth - 1);

				if (temp.getPoint() < beta) {
					beta = temp.getPoint();
					score = temp;
				}
				
				/**
				 * Alpha Beta Prunning
				 */
				if (alpha >= beta) {
					return score;
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
				
				/**
				 * Alpha Beta Prunning
				 */
				if (alpha >= beta) {
					return score;
	            }
			}

			else if (player == -1) {
				temp = minimax(1, playedBoard, remainingSpaces, alpha, beta, depth - 1);

				if (temp.getPoint() < beta) {
					beta = temp.getPoint();
					score = temp;
					score.setMove(move);
				}
				
				/**
				 * Alpha Beta Prunning
				 */
				if (alpha >= beta) {
					return score;
	            }
			}
		}

		return score != null ? score : temp;
	}

}
