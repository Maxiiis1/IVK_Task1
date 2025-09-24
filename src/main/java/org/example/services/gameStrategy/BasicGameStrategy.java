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
        int n = board.size();

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (board.isEmpty(x,y)) {
                    GameBoard copy = new GameBoardImpl(board);
                    copy.place(x,y,myColor);
                    if (detector.hasSquare(copy, myColor)) {
                        return new Point(x,y);
                    }
                }
            }
        }

        for (int y = 0; y < n; y++){
            for (int x = 0; x < n; x++) {
                if (board.isEmpty(x,y)) {
                    GameBoard copy = new GameBoardImpl(board);
                    copy.place(x,y,opponent);
                    if (detector.hasSquare(copy, opponent)){
                        return new Point(x,y);
                    }
                }
            }
        }
        int cx = n/2, cy = n/2;
        if (board.isEmpty(cx,cy)) return new Point(cx,cy);

        List<Point> freePlace = new ArrayList<>();
        for (int y = 0; y < n; y++) for (int x = 0; x < n; x++){
            if (board.isEmpty(x,y)) {
                freePlace.add(new Point(x,y));
            }
        }
        if (freePlace.isEmpty()){
            return null;
        }

        return freePlace.get(rnd.nextInt(freePlace.size()));
    }
}