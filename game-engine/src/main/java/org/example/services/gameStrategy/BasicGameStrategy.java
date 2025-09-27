package org.example.services.gameStrategy;

import org.example.models.Color;
import org.example.models.Point;
import org.example.services.game.GameBoard;
import org.example.services.game.GameBoardImpl;
import org.example.services.squareDetector.SquareDetector;
import org.example.services.squareDetector.SquareDetectorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasicGameStrategy implements GameStrategy {
    private final Random rnd = new Random();
    private final SquareDetector detector = new SquareDetectorImpl();

    @Override
    public Point chooseMove(GameBoard board, Color myColor) {
        Color opponent = (myColor == Color.W) ? Color.B : Color.W;

        Point move = findWinningMove(board, myColor);
        if (move != null) {
            return move;
        }

        move = findWinningMove(board, opponent);
        if (move != null) {
            return move;
        }

        int cx = board.size() / 2, cy = board.size() / 2;
        if (board.isEmpty(cx, cy)) {
            return new Point(cx, cy);
        }

        List<Point> freePlace = new ArrayList<>();
        for (int y = 0; y < board.size(); y++) {
            for (int x = 0; x < board.size(); x++) {
                if (board.isEmpty(x, y)) {
                    freePlace.add(new Point(x, y));
                }
            }
        }

        return freePlace.isEmpty() ? null : freePlace.get(rnd.nextInt(freePlace.size()));
    }

    private Point findWinningMove(GameBoard board, Color color) {
        int n = board.size();
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (board.isEmpty(x, y)) {
                    GameBoard copy = new GameBoardImpl(board);
                    copy.place(x, y, color);
                    if (detector.hasSquare(copy, color)) {
                        return new Point(x, y);
                    }
                }
            }
        }

        return null;
    }
}