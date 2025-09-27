package org.example.exceptions;

import lombok.Getter;
import org.example.models.Point;

import java.util.List;

@Getter
public class GameAlreadyFinishedException extends RuntimeException {
    private final String result;
    private final List<Point> winPoints;

    public GameAlreadyFinishedException(String result, List<Point> winPoints) {
        super("Game already finished!");
        this.result = result;
        this.winPoints = winPoints;
    }
}
