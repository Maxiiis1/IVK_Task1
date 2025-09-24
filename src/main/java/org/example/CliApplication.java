package org.example;


import org.example.models.Color;
import org.example.models.GameState;
import org.example.models.PlayerType;
import org.example.services.game.GameEngine;
import org.example.services.game.GameEngineImpl;
import org.example.services.player.Player;
import org.example.services.player.PlayerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class CliApplication {
    private final GameEngine engine = new GameEngineImpl();

    public void repl() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type HELP for commands");
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim(); if (line.isEmpty()) continue;
            String up = line.toUpperCase(Locale.ROOT);
            try {
                if (up.equals("EXIT")) { System.out.println("Bye"); break; }
                if (up.equals("HELP")) { printHelp(); continue; }
                if (up.startsWith("GAME")) { handleGame(line); continue; }
                if (up.startsWith("MOVE")) { handleMove(line); continue; }
                System.out.println("Incorrect command");
            } catch (Exception e) { System.out.println("Incorrect command"); }
        }
    }

    private void printHelp() {
        System.out.println("Commands: GAME N, TYPE C, TYPE C MOVE X, Y EXIT HELP");
    }

    private void handleGame(String line) {
        String rest = line.substring(4).trim();
        String[] parts = rest.split(",");
        if (parts.length < 3) {
            System.out.println("Incorrect command");
            return;
        }
        int n = Integer.parseInt(parts[0].trim());
        Player pl1 = parsePlayer(parts[1].trim());
        Player pl2 = parsePlayer(parts[2].trim());
        engine.newGame(n, pl1, pl2);
        System.out.println("New game started");

        if (pl1.type() == PlayerType.COMP && pl2.type() == PlayerType.COMP) {
            while (engine.getState() == GameState.ONGOING) {
                var current = engine.getCurrentPlayer();
                var p = engine.makeComputerMove();
                if (p != null) {
                    System.out.printf("%s (%d, %d)%n", current.color(), p.x(), p.y());
                }
                reportState();
            }
        }
        else if (pl1.type() == PlayerType.COMP) {
            var current = engine.getCurrentPlayer();
            var p = engine.makeComputerMove();
            if (p != null) {
                System.out.printf("%s (%d, %d)", current.color(), p.x(), p.y());
            }
            reportState();
        }
    }

    private Player parsePlayer(String s) {
        String[] t = s.trim().split("\s+");
        if (t.length < 2) throw new IllegalArgumentException();
        var type = t[0].equalsIgnoreCase("user") ? PlayerType.USER : PlayerType.COMP;
        var color = Color.valueOf(t[1].trim().toUpperCase());
        return type == PlayerType.USER ? PlayerFactory.createUser(color) : PlayerFactory.createComputer(color);
    }

    private void handleMove(String line) {
        if (engine.getBoard() == null) {
            System.out.println("Incorrect command");
            return;
        }
        String rest = line.substring(4).trim(); String[] t = rest.split(",");
        if (t.length < 2) {
            System.out.println("Incorrect command");
            return;
        }

        int x = Integer.parseInt(t[0].trim()); int y = Integer.parseInt(t[1].trim());
        var current = engine.getCurrentPlayer();
        if (current.type().name().equals("COMP")) {
            System.out.println("Incorrect command");
            return;
        }
        boolean ok = engine.makeMove(x,y);
        if (!ok) { System.out.println("Incorrect command"); return; }
        System.out.printf("%s (%d, %d)", current.color(), x, y);

        reportState();

        if (engine.getState() == GameState.ONGOING && engine.getCurrentPlayer().type().name().equals("COMP")) {
            var current1 = engine.getCurrentPlayer();
            var p = engine.makeComputerMove();
            if (p != null) {
                System.out.printf("%s (%d, %d)", current1.color(), p.x(), p.y());
                reportState();
            }
        }
    }

    private void reportState() {
        var s = engine.getState();
        if (s == GameState.DRAW) { System.out.println("Game finished. Draw"); }
        else if (s == GameState.W) { System.out.println("Game finished. W wins!"); }
        else if (s == GameState.B) { System.out.println("Game finished. B wins!"); }
    }

    public static void main(String[] args) throws Exception {
        new CliApplication().repl();
    }
}