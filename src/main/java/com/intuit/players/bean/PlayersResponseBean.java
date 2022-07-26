package com.intuit.players.bean;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayersResponseBean {
    private int totalPlayers;
    private List<PlayerResponse> playerEntityList;
}
