package org.example.services.player;

import org.example.models.Color;
import org.example.models.PlayerType;
import org.example.models.Point;
import org.example.services.game.GameBoard;

public interface Player {
    PlayerType type();
    Color color();
    Point nextMove(GameBoard board);
}
