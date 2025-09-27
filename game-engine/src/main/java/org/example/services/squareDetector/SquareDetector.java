package org.example.services.squareDetector;

import org.example.models.Color;
import org.example.models.Point;
import org.example.services.game.GameBoard;

import java.util.List;

public interface SquareDetector {
    boolean hasSquare(GameBoard board, Color color);
    List<Point> findWinningSquare(GameBoard board, Color color);
}