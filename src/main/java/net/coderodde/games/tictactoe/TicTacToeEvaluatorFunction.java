package net.coderodde.games.tictactoe;

import net.coderodde.zerosum.ai.EvaluatorFunction;

/**
 * This class implements the evaluator function for Tic-Tac-Toe game.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jun 27, 2019)
 */
public final class TicTacToeEvaluatorFunction 
        implements EvaluatorFunction<TicTacToeState> {

    @Override
    public double evaluate(TicTacToeState s) {
        PlayerColor[][] board = s.board;
        
        int winningMaximizingPatterns = 
                countWinningPatterns(board, PlayerColor.MAXIMIZING_PLAYER);
        int winningMinimizingPatterns = 
                countWinningPatterns(board, PlayerColor.MINIMIZING_PLAYER);
        
        if (winningMaximizingPatterns == 0
                && winningMinimizingPatterns == 0) {
            return noWinningValue(s);
        } else {
            if (winningMaximizingPatterns > winningMinimizingPatterns) {
                return 1e3 - s.getDepth();
            } else if (winningMinimizingPatterns > winningMaximizingPatterns) {
                return -1e3 + s.getDepth();
            } else {
                return 0.0;
            }
        }
    }
    
    private double noWinningValue(TicTacToeState s) {
        return 0.0;
    }
    
    private int countWinningPatterns(PlayerColor[][] board,
                                     PlayerColor targetPlayerColor) {
        final int HEIGHT = board.length;
        final int WIDTH = board[0].length;
        
        // Count horizontal winning patterns:
        int horizontalCount = 0;
        int verticalCount = 0;
        int diagonalCount = 0;
        
        outer1:
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (targetPlayerColor != board[y][x]) {
                    continue outer1;
                }
            }
            
            horizontalCount++;
        }
        
        outer2:
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (targetPlayerColor != board[y][x]) {
                    continue outer2;
                }
            }
            
            verticalCount++;
        }
        
        for (int i = 0; i < WIDTH; i++) {
            if (targetPlayerColor != board[i][i]) {
                break;
            } else if (i == WIDTH - 1) {
                diagonalCount++;
            }
        }
        
        for (int i = 0; i < WIDTH; i++) {
            if (targetPlayerColor != board[i][WIDTH - 1 - i]) {
                break;
            } else if (i == WIDTH - 1) {
                diagonalCount++;
            }
        }
        
        return verticalCount + horizontalCount + diagonalCount;
    }
}
