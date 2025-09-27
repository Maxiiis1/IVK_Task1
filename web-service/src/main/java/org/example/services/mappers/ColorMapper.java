package org.example.services.mappers;

import org.example.models.Color;

public interface ColorMapper {
    Color mapCharToColor(char c);
    String charForColor(Color c);
    Color opposite(Color c);
}
