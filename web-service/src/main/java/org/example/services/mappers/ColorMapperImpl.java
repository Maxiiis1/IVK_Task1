package org.example.services.mappers;

import org.example.exceptions.InvalidBoardException;
import org.example.models.Color;
import org.springframework.stereotype.Component;

@Component
public class ColorMapperImpl implements ColorMapper {

    @Override
    public Color mapCharToColor(char c) {
        String s = String.valueOf(Character.toUpperCase(c));
        for (Color col : Color.values()) {
            if (col.name().equalsIgnoreCase(s)
                    || (col == Color.W && s.equals("W"))
                    || (col == Color.B && s.equals("B"))) {
                return col;
            }
        }
        throw new InvalidBoardException("Unknown color char: " + c);
    }

    @Override
    public String charForColor(Color c) {
        if (c == Color.W) {
            return "w";
        }
        if (c == Color.B) {
            return "b";
        }
        return c.name().toLowerCase();
    }

    @Override
    public Color opposite(Color c) {
        return (c == Color.W) ? Color.B : Color.W;
    }
}

