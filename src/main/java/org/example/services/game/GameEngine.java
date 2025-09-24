package org.example.services.game;

import org.example.models.GameState;
import org.example.models.Point;
import org.example.services.player.Player;

public interface GameEngine {
    void newGame(int n, Player player1, Player player2);
    Player getCurrentPlayer();
    boolean makeMove(int x, int y);
    Point makeComputerMove(); // for convenience
    GameState getState();
    GameBoard getBoard();
}