package org.example.services.squareDetector;


import org.example.models.Color;
import org.example.models.Point;
import org.example.services.game.GameBoard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SquareDetectorImpl implements SquareDetector {

    @Override
    public boolean hasSquare(GameBoard board, Color color) {
        Set<Point> pts = board.pointsOf(color);

        if (pts.size() < 4) {
            return false;
        }

        Set<Long> set = new HashSet<>();
        for (Point p : pts) set.add(hash(p.x(), p.y()));
        Point[] arr = pts.toArray(new Point[0]);
        int m = arr.length;
        for (int i = 0; i < m; i++) {
            for (int j = i+1; j < m; j++) {
                Point p1 = arr[i];
                Point p2 = arr[j];
                int dx = p2.x() - p1.x();
                int dy = p2.y() - p1.y();
                int ux = -dy, uy = dx;
                if (checkSquare(p1, p2, ux, uy, set)) {
                    return true;
                }

                if (checkSquare(p1, p2, -ux, -uy, set)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Point> findWinningSquare(GameBoard board, Color color) {
        Set<Point> pts = board.pointsOf(color);
        if (pts.size() < 4) {
            return null;
        }

        Set<Long> set = new HashSet<>();
        for (Point p : pts) set.add(hash(p.x(), p.y()));
        Point[] arr = pts.toArray(new Point[0]);
        int m = arr.length;
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                Point p1 = arr[i];
                Point p2 = arr[j];
                int dx = p2.x() - p1.x();
                int dy = p2.y() - p1.y();
                int ux = -dy, uy = dx;

                List<Point> sq1 = tryBuildSquare(p1, p2, ux, uy, set);
                if (sq1 != null) {
                    return sq1;
                }

                List<Point> sq2 = tryBuildSquare(p1, p2, -ux, -uy, set);
                if (sq2 != null) {
                    return sq2;
                }
            }
        }
        return null;
    }

    private List<Point> tryBuildSquare(Point p1, Point p2, int ux, int uy, Set<Long> set) {
        int x3 = p1.x() + ux;
        int y3 = p1.y() + uy;
        int x4 = p2.x() + ux;
        int y4 = p2.y() + uy;

        if (set.contains(hash(x3, y3)) && set.contains(hash(x4, y4))) {
            return List.of(p1, p2, new Point(x3, y3), new Point(x4, y4));
        }
        return null;
    }

    private boolean checkSquare(Point p1, Point p2, int ux, int uy, Set<Long> set) {
        int x3 = p1.x() + ux, y3 = p1.y() + uy;
        int x4 = p2.x() + ux, y4 = p2.y() + uy;
        return set.contains(hash(x3,y3)) && set.contains(hash(x4,y4));
    }

    private long hash(int x, int y) { return (((long)x) << 32) ^ (y & 0xffffffffL); }
}