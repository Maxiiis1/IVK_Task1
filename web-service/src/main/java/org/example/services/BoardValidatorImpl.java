package org.example.services;

import org.example.exceptions.InvalidBoardException;
import org.example.models.BoardDto;
import org.springframework.stereotype.Component;

@Component
public class BoardValidatorImpl implements BoardValidator {

    @Override
    public void validate(BoardDto dto) {
        if (dto == null) {
            throw new InvalidBoardException("Missing body");
        }
        if (dto.getSize() <= 2) {
            throw new InvalidBoardException("Invalid size");
        }
        if (dto.getData() == null || dto.getNextPlayerColor() == null) {
            throw new InvalidBoardException("Missing fields");
        }
    }
}
