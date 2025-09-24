package org.example.services.squareDetector;

import org.example.models.Color;
import org.example.services.game.GameBoard;

public interface SquareDetector {
    boolean hasSquare(GameBoard board, Color color);
}