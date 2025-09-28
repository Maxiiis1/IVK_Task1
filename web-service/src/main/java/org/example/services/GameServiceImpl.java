package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.GameAlreadyFinishedException;
import org.example.exceptions.InvalidBoardException;
import org.example.exceptions.NoMoveAvailableException;
import org.example.models.*;
import org.example.services.game.GameBoard;
import org.example.services.game.GameBoardImpl;
import org.example.services.game.GameEngine;
import org.example.services.game.GameEngineImpl;
import org.example.services.mappers.ColorMapper;
import org.example.services.player.Player;
import org.example.services.player.PlayerFactory;
import org.example.services.squareDetector.SquareDetectorImpl;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final BoardValidator boardValidator;
    private final ColorMapper colorMapper;

    @Override
    public SimpleMoveDto computeNextMove(BoardDto dto) {
        boardValidator.validate(dto);

        int n = dto.getSize();
        GameBoard inputBoard = new GameBoardImpl(n);
        String flat = dto.getData().replace("\r", "").replace("\n", "");
        if (flat.length() != n * n) {
            throw new InvalidBoardException("Board data length mismatch: expected " + (n*n));
        }

        int idx = 0;
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                char c = flat.charAt(idx++);
                if (c == ' ' || c == '_' ) {
                    continue;
                }

                Color col = colorMapper.mapCharToColor(c);
                inputBoard.place(x, y, col);
            }
        }

        Color nextColor = colorMapper.mapCharToColor(dto.getNextPlayerColor().charAt(0));

        Player p1 = PlayerFactory.createComputer(nextColor);
        Player p2 = PlayerFactory.createComputer(colorMapper.opposite(nextColor));

        GameEngine engine = new GameEngineImpl();
        engine.newGame(n, p1, p2);

        GameBoard engineBoard = engine.getBoard();
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                var c = inputBoard.get(x, y);
                if (c != null) {
                    engineBoard.place(x, y, c);
                }
            }
        }

        for (Color color : Color.values()) {
            List<Point> win = new SquareDetectorImpl().findWinningSquare(engine.getBoard(), color);
            if (win != null) {
                throw new GameAlreadyFinishedException(
                        winnerMessage(color),
                        win,
                        null, null, null);
            }
        }

        Point move = engine.makeComputerMove();
        if (move == null) {
            throw new NoMoveAvailableException("No available move");
        }

        for (Color color : Color.values()) {
            List<Point> win = new SquareDetectorImpl().findWinningSquare(engine.getBoard(), color);
            if (win != null) {
                throw new GameAlreadyFinishedException(
                        winnerMessage(color),
                        win,
                        move.x(), move.y(), colorMapper.charForColor(color)
                );
            }
        }

        String colorString = colorMapper.charForColor(nextColor);
        return new SimpleMoveDto(move.x(), move.y(), colorString);
    }

    private String winnerMessage(Color color) {
        if (color == Color.W) {
            return "Белые победили!";
        }

        if (color == Color.B) {
            return "Чёрные победили!";
        }

        return color + " победили!";
    }
}
