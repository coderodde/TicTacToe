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
    
    private static final double[][] weightMatrix = new double[3][3];
    
    static {
        weightMatrix[0][0] = 8.0;
        weightMatrix[0][1] = 1.0;
        weightMatrix[0][2] = 8.0;
        weightMatrix[1][0] = 1.0;
        weightMatrix[1][1] = 64.0;
        weightMatrix[1][2] = 1.0;
        weightMatrix[2][0] = 8.0;
        weightMatrix[2][1] = 1.0;
        weightMatrix[2][2] = 8.0;
    }

    @Override
    public double evaluate(TicTacToeState state) {
        TicTacToePlayerColor playerColor;
        double value = 0.0;
        
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if ((playerColor = state.read(x, y)) == null) {
                    continue;
                }
                
                double weight = weightMatrix[y][x];
                
                if (playerColor == TicTacToePlayerColor.MINIMIZING_PLAYER) {
                    value -= weight;
                } else {
                    value += weight;
                }
            }
        }
        
        TicTacToePlayerColor winnerColor = state.checkVictory();
        
        if (winnerColor == TicTacToePlayerColor.MINIMIZING_PLAYER) {
            return -1000.0 - state.getDepth() + value;
        } else if (winnerColor == TicTacToePlayerColor.MAXIMIZING_PLAYER) {
            return 1000.0 + state.getDepth() + value;
        }
        
        return value;
    }

//    @Override
//    public double evaluate(TicTacToeState s) {
//        TicTacToePlayerColor winningPlayerColor = s.checkVictory();
//        
//        if (winningPlayerColor == null) {
//            return 0.0;
//        } else if (winningPlayerColor 
//                == TicTacToePlayerColor.MAXIMIZING_PLAYER) {
//            return 100.0 + s.getDepth();
//        } else {
//            return -100.0 - s.getDepth();
//        }
//    }
//        int maximizingWinningPatternCount = 
//                countWinningPatterns(s.board, PlayerColor.MAXIMIZING_PLAYER);
//        
//        int minimizingWinningPatternCount = 
//                countWinningPatterns(s.board, PlayerColor.MINIMIZING_PLAYER);
//        
//        if (maximizingWinningPatternCount > minimizingWinningPatternCount) {
//            return 1e3;
//        } else if (maximizingWinningPatternCount 
//                 < minimizingWinningPatternCount) {
//            return -1e3;
//        } else {
//            return 0.0;
//        }
//    }
//    
//    private double noWinningValue(TicTacToeState s) {
//        return 0.0;
//    }
//    
//    private int countWinningPatterns(PlayerColor[][] board,
//                                     PlayerColor targetPlayerColor) {
//        final int HEIGHT = board.length;
//        final int WIDTH = board[0].length;
//        
//        // Count horizontal winning patterns:
//        int horizontalCount = 0;
//        int verticalCount = 0;
//        int diagonalCount = 0;
//        
//        outer1:
//        for (int y = 0; y < HEIGHT; y++) {
//            for (int x = 0; x < WIDTH; x++) {
//                if (targetPlayerColor != board[y][x]) {
//                    continue outer1;
//                }
//            }
//            
//            horizontalCount++;
//        }
//        
//        outer2:
//        for (int x = 0; x < WIDTH; x++) {
//            for (int y = 0; y < HEIGHT; y++) {
//                if (targetPlayerColor != board[y][x]) {
//                    continue outer2;
//                }
//            }
//            
//            verticalCount++;
//        }
//        
//        for (int i = 0; i < WIDTH; i++) {
//            if (targetPlayerColor != board[i][i]) {
//                break;
//            } else if (i == WIDTH - 1) {
//                diagonalCount++;
//            }
//        }
//        
//        for (int i = 0; i < WIDTH; i++) {
//            if (targetPlayerColor != board[i][WIDTH - 1 - i]) {
//                break;
//            } else if (i == WIDTH - 1) {
//                diagonalCount++;
//            }
//        }
//        
//        return verticalCount + horizontalCount + diagonalCount;
//    }
}
