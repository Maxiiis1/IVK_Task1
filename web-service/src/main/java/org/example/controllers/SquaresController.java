package org.example.controllers;
import lombok.RequiredArgsConstructor;
import org.example.models.BoardDto;
import org.example.models.SimpleMoveDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.example.services.GameService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SquaresController {
    private final GameService gameService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{rules}/nextMove")
    public SimpleMoveDto nextMove(@PathVariable String rules, @RequestBody BoardDto boardDto) {
        return gameService.computeNextMove(boardDto);
    }
}
