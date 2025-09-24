package org.example.services.player;

import org.example.models.Color;
import org.example.models.PlayerType;
import org.example.models.Point;
import org.example.services.game.GameBoard;
import org.example.services.gameStrategy.GameStrategy;

public class PlayerImpl implements Player {
    private final PlayerType type;
    private final Color color;
    private final GameStrategy strategy;

    public PlayerImpl(PlayerType type, Color color, GameStrategy strategy) {
        this.type = type;
        this.color = color;
        this.strategy = strategy;
    }

    @Override
    public PlayerType type() { return this.type; }

    @Override
    public Color color() { return this.color; }

    @Override
    public Point nextMove(GameBoard board) {
        if (type == PlayerType.USER) {
            return null;
        }

        return strategy.chooseMove(board, color);
    }
}