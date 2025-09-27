package org.example.services;

import org.example.models.BoardDto;
import org.example.models.SimpleMoveDto;

public interface GameService {
    SimpleMoveDto computeNextMove(BoardDto dto);
}
