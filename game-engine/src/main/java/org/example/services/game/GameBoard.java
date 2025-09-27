package org.example.services.game;

import org.example.models.Color;
import org.example.models.Point;
import java.util.Set;

public interface GameBoard {
    int size();

    boolean inBounds(int x, int y);

    boolean isEmpty(int x, int y);

    boolean place(int x, int y, Color c);

    boolean remove(int x, int y);

    Color get(int x, int y);

    boolean isFull();

    Set<Point> pointsOf(Color color);

    String toString();
}
