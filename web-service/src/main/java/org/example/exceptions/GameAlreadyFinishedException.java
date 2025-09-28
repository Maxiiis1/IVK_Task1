package org.example.exceptions;

import lombok.Getter;
import org.example.models.Point;

import java.util.List;

@Getter
public class GameAlreadyFinishedException extends RuntimeException {
    private final String result;
    private final List<Point> winPoints;
    private final Integer lastX;
    private final Integer lastY;
    private final String lastColor;

    public GameAlreadyFinishedException(String result, List<Point> winPoints, Integer lastX,
                                        Integer lastY, String lastColor) {
        super("Game already finished!");
        this.result = result;
        this.winPoints = winPoints;
        this.lastX = lastX;
        this.lastY = lastY;
        this.lastColor = lastColor;
    }
}
