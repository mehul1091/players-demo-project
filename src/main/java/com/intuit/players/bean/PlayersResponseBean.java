package com.intuit.players.bean;

import com.intuit.players.entity.PlayerEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayersResponseBean {
    private int totalPlayers;
    private List<PlayerResponse> playerEntityList;
}
