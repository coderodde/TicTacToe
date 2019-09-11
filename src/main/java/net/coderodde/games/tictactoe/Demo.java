package net.coderodde.games.tictactoe;;

import net.coderodde.zerosum.ai.AbstractGameEngine;
import java.util.Objects;
import java.util.Scanner;
import net.coderodde.games.tictactoe.impl.Human;
import net.coderodde.games.tictactoe.impl.SmartBot;
import net.coderodde.zerosum.ai.EvaluatorFunction;
import net.coderodde.zerosum.ai.impl.MinimaxGameEngine;

/**
 * This class implements the Connect Four game in the command line/console.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (May 25, 2019)
 */
public class Demo {

    public static final double MAX_WEIGHT_MATRIX_ENTRY = 10.0;
    
    public static final int DEFAULT_SEARCH_DEPTH = 1;
    
    public static void main(String[] args) {
        EvaluatorFunction<TicTacToeState> evaluatorFunction1 = 
                new TicTacToeEvaluatorFunction();
        
        AbstractGameEngine<TicTacToeState, TicTacToePlayerColor> gameEngine = 
                new MinimaxGameEngine<TicTacToeState, TicTacToePlayerColor>(
                                        evaluatorFunction1, 
                                        DEFAULT_SEARCH_DEPTH);
        
        // 'bot3' is connected to cin:
        Bot bot3 = new Human(TicTacToePlayerColor.MAXIMIZING_PLAYER, 
                             "X >>> ", 
                             new Scanner(System.in));
        
        Bot bot4 = new SmartBot(TicTacToePlayerColor.MINIMIZING_PLAYER, 
                                gameEngine);
        playMatch(bot4, bot3);
    }
    
    /**
     * Plays a single match.
     * 
     * @param bot1 the first bot. This bot makes the first move.
     * @param bot2 the second bot.
     */
    private static final void playMatch(Bot bot1, Bot bot2) {
        checkBotPlayers(bot1, bot2);
        TicTacToeState state = new TicTacToeState(bot1.getPlayerColor());
        System.out.println(state);
        System.out.println();
        
        // bot1 begins the game.
        Bot currentBot = bot1;
        TicTacToePlayerColor winner = null;
        
        // While there is room in the board:
        while (!state.isFull()) {
            if (currentBot == bot1) {
                state = bot1.computeNextState(state);
                
                if (winner != null) {
                    break;
                }
                
                currentBot = bot2;
            } else {
                state = bot2.computeNextState(state);
                winner = state.checkVictory();
                
                if (winner != null) {
                    break;
                }
                
                currentBot = bot1;
            }
            
            System.out.println(state);
            System.out.println();
        }
        
        System.out.println(state);
        
        if (winner != null) {
            char winnerChar = winner.getChar();
            System.out.println("RESULT: The " + winnerChar + " won!");
        } else {
            System.out.println("RESULT: It's a draw!");
        }
    }
    
    private static final void checkBotPlayers(Bot bot1, Bot bot2) {
        Objects.requireNonNull(bot1, "The bot1 is null.");
        Objects.requireNonNull(bot1, "The bot2 is null.");
        Objects.requireNonNull(bot1.getPlayerColor(), 
                               "The PlayerColor of bot1 is null.");
        Objects.requireNonNull(bot2.getPlayerColor(),
                               "The PlayerColor of bot2 is null.");
        
        if (bot1.getPlayerColor() == bot2.getPlayerColor()) {
            throw new IllegalArgumentException(
                    "The two input bots have the same player color.");
        }
    }
}
