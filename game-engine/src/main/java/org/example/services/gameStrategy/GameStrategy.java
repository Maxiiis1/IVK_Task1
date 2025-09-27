package org.example.services.gameStrategy;

import org.example.models.Color;
import org.example.models.Point;
import org.example.services.game.GameBoard;

public interface GameStrategy {
    Point chooseMove(GameBoard board, Color myColor);
}
