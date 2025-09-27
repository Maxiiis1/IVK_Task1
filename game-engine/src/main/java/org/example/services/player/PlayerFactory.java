package org.example.services.player;

import org.example.models.Color;
import org.example.models.PlayerType;
import org.example.services.gameStrategy.BasicGameStrategy;
import org.example.services.gameStrategy.GameStrategy;

public final class PlayerFactory {
    private PlayerFactory() {}

    public static Player createUser(Color color) {
        return new PlayerImpl(PlayerType.USER, color, null);
    }

    public static Player createComputer(Color color) {
        GameStrategy s = new BasicGameStrategy();
        return new PlayerImpl(PlayerType.COMP, color, s);
    }
}