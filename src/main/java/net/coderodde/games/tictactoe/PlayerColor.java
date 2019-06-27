package net.coderodde.games.tictactoe;

/**
 * This class enumerates all the player colors for the Tic-Tac-Toe game.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jun 27, 2019)
 */
public enum PlayerColor {
    
    MAXIMIZING_PLAYER('X'),
    MINIMIZING_PLAYER('O');
    
    private final char ch;
    
    private PlayerColor(char ch) {
        this.ch = ch;
    }
    
    public char getChar() {
        return ch;
    }
}
