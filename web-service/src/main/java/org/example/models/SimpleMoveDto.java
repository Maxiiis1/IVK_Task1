package org.example.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMoveDto {
    private int x;
    private int y;
    private String color;
}