package org.example.services.game;


import org.example.models.Color;
import org.example.models.GameState;
import org.example.models.Point;
import org.example.services.player.Player;
import org.example.services.squareDetector.SquareDetector;
import org.example.services.squareDetector.SquareDetectorImpl;

public class GameEngineImpl implements GameEngine {
    private GameBoard board;
    private Player player1;
    private Player player2;
    private Player current;
    private final SquareDetector detector = new SquareDetectorImpl();
    private GameState state = GameState.ONGOING;

    @Override
    public void newGame(int n, Player player1, Player player2) {
        this.board = new GameBoardImpl(n);
        this.player1 = player1;
        this.player2 = player2;
        this.current = player1;
        this.state = GameState.ONGOING;
    }

    @Override
    public Player getCurrentPlayer() { return current; }

    @Override
    public boolean makeMove(int x, int y) {
        if (state != GameState.ONGOING) return false;
        boolean ok = board.place(x,y, current.color());
        if (!ok) return false;
        updateStateAfterMove(current.color());
        if (state == GameState.ONGOING) swapTurn();
        return true;
    }

    @Override
    public Point makeComputerMove() {
        if (state != GameState.ONGOING) return null;
        Point p = current.nextMove(board);
        if (p == null) {
            return null;
        }

        boolean ok = makeMove(p.x(), p.y());
        return ok ? p : null;
    }

    private void swapTurn() {
        current = (this.current == this.player1) ? this.player2 : this.player1;
    }

    private void updateStateAfterMove(Color movedColor) {
        if (detector.hasSquare(board, movedColor)) {
            state = (movedColor == Color.W) ? GameState.W : GameState.B;
            return;
        }

        if (board.isFull()) {
            state = GameState.DRAW;
        }
    }

    @Override public GameState getState() { return this.state; }
    @Override public GameBoard getBoard() { return this.board; }
}
