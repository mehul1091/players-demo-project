package com.intuit.players.service;

import com.intuit.players.bean.PlayerResponse;
import com.intuit.players.bean.PlayersResponseBean;
import com.intuit.players.entity.PlayerEntity;

import java.util.List;
import java.util.Optional;

public interface DatabaseService {
    public PlayerResponse getPlayerById(int playerId);
    public PlayersResponseBean getAllPlayers();
    void saveAll(List<PlayerEntity> playerEntities);
}
