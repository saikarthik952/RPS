package com.multiplayer.RPS.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Player {

    String id;
    Action action;
}
