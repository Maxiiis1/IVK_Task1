package org.example.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private int size;
    private String data;
    private String nextPlayerColor;
}