package org.example.exceptions.handlers;

import org.example.exceptions.GameAlreadyFinishedException;
import org.example.exceptions.InvalidBoardException;
import org.example.exceptions.NoMoveAvailableException;
import org.example.models.GameFinishedResponse;
import org.example.models.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GameAlreadyFinishedException.class)
    public GameFinishedResponse handleException(GameAlreadyFinishedException ex){
        return new GameFinishedResponse(ex.getMessage(), ex.getResult(), ex.getWinPoints(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NoMoveAvailableException.class)
    public ResponseError handleNoMoveAvailable(NoMoveAvailableException ex) {
        return new ResponseError(ex.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidBoardException.class)
    public ResponseError handleInvalidBoard(InvalidBoardException ex) {
        return new ResponseError(ex.getMessage(), LocalDateTime.now());
    }
}
