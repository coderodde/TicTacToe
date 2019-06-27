package net.coderodde.games.tictactoe.impl;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import net.coderodde.games.tictactoe.Bot;
import net.coderodde.games.tictactoe.PlayerColor;
import net.coderodde.games.tictactoe.TicTacToeState;

/**
 * This class implements a human bot controllable from the console.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (May 25, 2019)
 */
public final class Human implements Bot {

    /**
     * The color of this human.
     */
    private final PlayerColor myPlayerColor;
    
    /**
     * The command prompt for the user input.
     */
    private final String commandPrompt;
    
    /**
     * The scanner for reading the input.
     */
    private final Scanner scanner;
    
    public Human(PlayerColor me, String commandPrompt, Scanner scanner) {
        this.myPlayerColor = 
                Objects.requireNonNull(me, "The input player is null.");
        
        this.commandPrompt = 
                Objects.requireNonNull(
                        commandPrompt, 
                        "The input command prompt is null.");
        
        this.scanner = Objects.requireNonNull(scanner, 
                                              "The input scanner is null.");
    }
    
    @Override
    public TicTacToeState computeNextState(TicTacToeState state) {
        loop:
        while (true) {
            try {
                System.out.print(commandPrompt);
                int columnIndex = scanner.nextInt();
                int rowIndex = scanner.nextInt();
                checkColumn(columnIndex);
                checkRow(rowIndex);
                // Convert human indexing from 1 to computer indexing from 0:
                columnIndex--;
                rowIndex--;
                
                if (state.read(columnIndex, rowIndex) != null) {
                    System.out.println("(" + (columnIndex + 1) + ", " + 
                                       (rowIndex + 1) + ") is occupied.");
                    continue loop;
                }
                
                TicTacToeState nextState = state.move(columnIndex, rowIndex);
                return nextState;
            } catch (InputMismatchException ex) {
                throw new IllegalStateException("Unrecognized column number: " +
                                                ex.getMessage());
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    private void checkColumn(int columnIndex) {
        if (columnIndex < 1) {
            throw new IllegalArgumentException(
                    "Too small column index: " + columnIndex + ". Must be at " + 
                    "least 1.");
        }
        
        if (columnIndex > TicTacToeState.WIDTH) {
            throw new IllegalArgumentException(
                    "Too large column index: " + columnIndex + ". Must be at " +
                    "mosst " + TicTacToeState.WIDTH + ".");
        }
    }
    
    private void checkRow(int rowIndex) {
        if (rowIndex < 1) {
            throw new IllegalArgumentException(
                    "Too small column index: " + rowIndex + ". Must be at " + 
                    "least 1.");
        }
        
        if (rowIndex > TicTacToeState.HEIGHT) {
            throw new IllegalArgumentException(
                    "Too large column index: " + rowIndex + ". Must be at " +
                    "mosst " + TicTacToeState.HEIGHT + ".");
        }
    }

    @Override
    public PlayerColor getPlayerColor() {
        return myPlayerColor;
    }
}
