package net.coderodde.games.tictactoe.impl;

import java.util.Objects;
import net.coderodde.games.tictactoe.Bot;
import net.coderodde.games.tictactoe.PlayerColor;
import net.coderodde.games.tictactoe.TicTacToeState;
import net.coderodde.zerosum.ai.GameEngine;

/**
 * This class implements the smart bot relying on Alpha-beta pruning.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (May 24, 2019)
 */
public final class SmartBot implements Bot {
    
    private final PlayerColor myPlayerColor;
    private final GameEngine<TicTacToeState, PlayerColor> engine;
    
    public SmartBot(PlayerColor me, 
                    GameEngine<TicTacToeState, PlayerColor> engine) {
        this.myPlayerColor =
                Objects.requireNonNull(me, "The input player is null.");
        
        this.engine = Objects.requireNonNull(engine,
                                             "The input engine is null.");
    }

    @Override
    public TicTacToeState computeNextState(TicTacToeState state) {
        state.setDepth(engine.getDepth());
        long startTime = System.currentTimeMillis();
        TicTacToeState nextState = 
                engine.makePly(state, 
                               PlayerColor.MINIMIZING_PLAYER, 
                               PlayerColor.MAXIMIZING_PLAYER, 
                               myPlayerColor);
        long endTime = System.currentTimeMillis();
        System.out.println("SmartBot in " + (endTime - startTime) + " ms:");
        return nextState;
    }

    @Override
    public PlayerColor getPlayerColor() {
        return myPlayerColor;
    }
}
