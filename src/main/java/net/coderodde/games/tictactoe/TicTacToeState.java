package net.coderodde.games.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.coderodde.zerosum.ai.AbstractState;

/**
 * This class implements the Tic Tac Toe board state.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.61 (Aug 25, 2019)
 * @since 1.6 (Jun 16, 2019)
 */
public final class TicTacToeState 
     extends AbstractState<TicTacToeState, TicTacToePlayerColor> {
    
    /**
     * The width of Tic Tac Toe boards.
     */
    public static final int WIDTH = 3;
    
    /**
     * The height of Tic Tac Toe boards.
     */
    public static final int HEIGHT = 3;

    /**
     * The color of the player
     */
    private final TicTacToePlayerColor playerColor;
    
    /**
     * The number of empty slots.
     */
    private final int numberOfEmptySlots;
    
    /**
     * The collection of immediate child Tic Tac Toe boards.
     */
    private List<TicTacToeState> children;
    
    /**
     * The depth of this board state.
     */
    private int depth;
    
    /**
     * The actual Tic Tac Toe board state.
     */
    private final TicTacToePlayerColor[][] board;
    
    public TicTacToeState(TicTacToePlayerColor playerColor) {
        this.playerColor = playerColor;
        this.board = new TicTacToePlayerColor[HEIGHT][WIDTH];
        this.numberOfEmptySlots = HEIGHT * WIDTH;
    }
    
    private TicTacToeState(TicTacToePlayerColor playerColor,
                           TicTacToePlayerColor[][] board,
                           int numberOfEmptySlots,
                           int depth) {
        this.numberOfEmptySlots = numberOfEmptySlots;
        this.board = cloneBoard(board);
        this.playerColor = playerColor;
        this.depth = depth;
    }
    
    private TicTacToePlayerColor[][] cloneBoard(TicTacToePlayerColor[][] board) {
        TicTacToePlayerColor[][] clone = 
                new TicTacToePlayerColor[board.length]
                                        [board[0].length];
        
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                clone[y][x] = board[y][x];
            }
        }
        
        return clone;
    }
    
    public int getDepth() {
        return depth;
    }
    
    public void setDepth(int depth) {
        this.depth = depth;
    }
    
    @Override
    public List<TicTacToeState> children() {
        if (children != null) {
            return children;
        }
        
        children = new ArrayList<>(numberOfEmptySlots);
        
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[y][x] == null) {
                    TicTacToeState childState = move(x, y);
                    childState.setDepth(depth - 1);
                    children.add(childState);
                }
            }
        }
        
        return children;
    }
    
    public TicTacToePlayerColor checkVictory() {
        TicTacToePlayerColor playerColor = checkMaximizingVictory();
        
        if (playerColor == null) {
            return checkMinimizingVictory();
        } else {
            return playerColor;
        }
    }
    
    private TicTacToePlayerColor checkMaximizingVictory() {
        return checkVictory(TicTacToePlayerColor.MAXIMIZING_PLAYER);
    }
    
    private TicTacToePlayerColor checkMinimizingVictory() {
        return checkVictory(TicTacToePlayerColor.MINIMIZING_PLAYER);
    }
    
    private TicTacToePlayerColor checkVictory(TicTacToePlayerColor playerColor) {
        outer1:
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (playerColor != read(x, y)) {
                    continue outer1;
                }
            }
            
            return playerColor;
        }
        
        outer2:
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (playerColor != read(x, y)) {
                    continue outer2;
                }
            }
            
            return playerColor;
        }
        
        for (int i = 0; i < WIDTH; i++) {
            if (playerColor != read(i, i)) {
                break;
            } else if (i == WIDTH - 1) {
                return playerColor;
            }
        }
        
        for (int i = 0; i < WIDTH; i++) {
            if (playerColor != read(i, WIDTH - 1 - i)) {
                break;
            } else if (i == WIDTH - 1) {
                return playerColor;
            }            
        }
        
        return null;
    }
    
    private static final String HORIZONTAL_BAR1 = "+-------+-------+-------+\n";
    private static final String HORIZONTAL_BAR2 = "|       |       |       |\n";
   
    private static char getChar(TicTacToePlayerColor playerColor) {
        return playerColor != null ? playerColor.getChar() : ' ';
    }
    
    private static final String row(TicTacToePlayerColor[] rowData) {
        return "|   " + getChar(rowData[0]) + 
            "   |   " + getChar(rowData[1]) + 
            "   |   " + getChar(rowData[2]) + 
            "   |\n";
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append(HORIZONTAL_BAR1)
                                  .append(HORIZONTAL_BAR2)
                                  .append(row(board[0]))
                                  .append(HORIZONTAL_BAR2)
                                  .append(HORIZONTAL_BAR1)
                                  .append(HORIZONTAL_BAR2)
                                  .append(row(board[1]))
                                  .append(HORIZONTAL_BAR2)
                                  .append(HORIZONTAL_BAR1)
                                  .append(HORIZONTAL_BAR2)
                                  .append(row(board[2]))
                                  .append(HORIZONTAL_BAR2)
                                  .append(HORIZONTAL_BAR1).toString();
    }
    
    @Override
    public boolean isTerminal() {
        if (numberOfEmptySlots == 0) {
            return true;
        }
        
        outer1:
        for (int y = 0; y < HEIGHT; y++) {
            TicTacToePlayerColor playerColor = read(0, y);
            
            if (playerColor == null) {
                break;
            }
            
            for (int x = 1; x < WIDTH; x++) {
                if (playerColor != read(x, y)) {
                    continue outer1;
                }
            }
            
            return true;
        }
        
        outer2:
        for (int x = 0; x < WIDTH; x++) {
            TicTacToePlayerColor playerColor = read(x, 0);
            
            if (playerColor == null) {
                break;
            }
            
            for (int y = 1; y < HEIGHT; y++) {
                if (playerColor != read(x, y)) {
                    continue outer2;
                }
            }
            
            return true;
        }
        
        TicTacToePlayerColor playerColor = read(0, 0);
        
        if (playerColor != null) {
            for (int i = 1; i < WIDTH; i++) {
                if (playerColor != read(i, i)) {
                    break;
                } else if (i == WIDTH - 2) {
                    return true;
                }
            }
        }
        
        playerColor = read(0, WIDTH - 1);
        
        if (playerColor != null) {
            for (int i = 1; i < WIDTH; i++) {
                if (playerColor != read(i, WIDTH - 1 - i)) {
                    break;
                } else if (i == WIDTH - 1) {
                    return true;
                }
            }            
        }
        
        return false;
    }
    
    public boolean isFull() {
        return numberOfEmptySlots == 0;
    }
    
    public TicTacToeState move(int x, int y) {
        TicTacToePlayerColor nextPlayerColor = 
                playerColor == TicTacToePlayerColor.MAXIMIZING_PLAYER ? 
                TicTacToePlayerColor.MINIMIZING_PLAYER :
                TicTacToePlayerColor.MAXIMIZING_PLAYER;
        
        TicTacToeState nextState = 
                new TicTacToeState(nextPlayerColor,
                                   board, 
                                   numberOfEmptySlots - 1,
                                   depth - 1);
        
        nextState.write(x, y, playerColor);
        return nextState;
    }
    
    public TicTacToePlayerColor read(int x, int y) {
        checkXCoordinate(x);
        checkYCoordinate(y);
        return board[y][x];
    }
    
    public void write(int x, int y, TicTacToePlayerColor player) {
        checkXCoordinate(x);
        checkYCoordinate(y);
        board[y][x] = Objects.requireNonNull(player, "The player is null.");
    }
    
    private void checkXCoordinate(int x) {
        checkCoordinateImpl(
                x,
                this.board[0].length - 1,
                "The X-coordinate is negative: " + x,
                "The X-coordinate ("  + x + ") is too large. Must be at most " +
                (board[0].length - 1));
    }
    
    private void checkYCoordinate(int y) {
        checkCoordinateImpl(
                y,
                this.board.length - 1,
                "The Y-coordinate is negative: " + y,
                "The Y-coordinate (" + y + ") is too large. Must be at most " + 
                (board.length - 1));
    }
    
    private void checkCoordinateImpl(int coordinate,
                                     int maxCoordinate,
                                     String negativeMessage,
                                     String tooLargeMesage) {
        if (coordinate < 0) {
            throw new IllegalArgumentException(negativeMessage);
        }
        
        if (coordinate > maxCoordinate) {
            throw new IllegalArgumentException(tooLargeMesage);
        }
    }
}
