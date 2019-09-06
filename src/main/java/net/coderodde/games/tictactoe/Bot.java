package net.coderodde.games.tictactoe;

/**
 * This interface defines the API for playing bots.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (May 24, 2019)
 */
public interface Bot {
    
    /**
     * Computes the next state from information available in the input state.
     * @param state the current state.
     * @return the next state.
     */
    public TicTacToeState computeNextState(TicTacToeState state);
    
    /**
     * Returns the player color of this bot.
     * @return the player color of this bot.
     */
    public TicTacToePlayerColor getPlayerColor();
}
