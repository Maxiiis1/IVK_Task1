package org.example.services.game;


import org.example.models.Color;
import org.example.models.Point;

import java.util.HashSet;
import java.util.Set;

public class GameBoardImpl implements GameBoard {
    private final int n;
    private final Color[][] grid;

    public GameBoardImpl(int n) {
        if (n <= 2){
            throw new IllegalArgumentException("N must be > 2");
        }
        this.n = n;
        this.grid = new Color[n][n];
    }

    public GameBoardImpl(GameBoard other) {
        this.n = other.size();
        this.grid = new Color[n][n];
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                this.grid[y][x] = other.get(x,y);
            }
        }
    }

    @Override
    public int size() { return n; }

    @Override
    public boolean inBounds(int x, int y) { return x >= 0 && y >= 0 && x < n && y < n; }

    @Override
    public boolean isEmpty(int x, int y) { return inBounds(x,y) && grid[y][x] == null; }

    @Override
    public boolean place(int x, int y, Color c) {
        if (!inBounds(x,y) || grid[y][x] != null) return false;
        grid[y][x] = c;
        return true;
    }

    @Override
    public boolean remove(int x, int y) {
        if (!inBounds(x,y) || grid[y][x] == null) {
            return false;
        }
        grid[y][x] = null;
        return true;
    }

    @Override
    public Color get(int x, int y) { return inBounds(x,y) ? grid[y][x] : null; }

    @Override
    public boolean isFull() {
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (grid[y][x] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Set<Point> pointsOf(Color color) {
        Set<Point> s = new HashSet<>();
        for (int y = 0; y < n; y++) for (int x = 0; x < n; x++) if (color.equals(grid[y][x])) s.add(new Point(x,y));
        return s;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                sb.append(grid[y][x] == null ? '.' : (grid[y][x] == Color.W ? 'W' : 'B')).append(' ');
            }
            sb.append(' ');
        }
        return sb.toString();
    }
}