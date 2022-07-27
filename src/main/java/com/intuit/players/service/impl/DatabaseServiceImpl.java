package com.intuit.players.service.impl;

import com.intuit.players.bean.PlayerResponse;
import com.intuit.players.bean.PlayersResponseBean;
import com.intuit.players.entity.PlayerEntity;
import com.intuit.players.exception.PlayerNotFoundException;
import com.intuit.players.repository.PlayersRepository;
import com.intuit.players.service.DatabaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    private final PlayersRepository playersRepository;

    public PlayersResponseBean getAllPlayers() {
        log.info("querying database for all players");
        List<PlayerResponse> playerResponses = playersRepository.findAll().stream()
                .map(playerEntity -> PlayerResponse.builder()
                        .id(playerEntity.getId())
                        .firstName(playerEntity.getFirstName())
                        .lastName(playerEntity.getLastName())
                        .gender(playerEntity.getGender())
                        .position(playerEntity.getPosition())
                        .team(playerEntity.getTeam())
                        .build()).collect(Collectors.toList());

        return PlayersResponseBean.builder()
                .totalPlayers(playerResponses.size())
                .playerEntityList(playerResponses)
                .build();
    }

    public PlayerResponse getPlayerById(int playerId) {
        Optional<PlayerEntity> playerEntityOptional = playersRepository.findById(playerId);

        if(playerEntityOptional.isEmpty())
            throw new PlayerNotFoundException(playerId);

        PlayerEntity playerEntity = playerEntityOptional.get();
        return PlayerResponse.builder()
                .id(playerEntity.getId())
                .firstName(playerEntity.getFirstName())
                .lastName(playerEntity.getLastName())
                .team(playerEntity.getTeam())
                .position(playerEntity.getPosition())
                .gender(playerEntity.getGender())
                .build();
    }

    public void saveAll(List<PlayerEntity> playerEntities) {
        log.info("saving players list to database");
        playersRepository.saveAll(playerEntities);
    }
}
