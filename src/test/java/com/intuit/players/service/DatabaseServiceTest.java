package com.intuit.players.service;

import com.intuit.players.bean.PlayerResponse;
import com.intuit.players.bean.PlayersResponseBean;
import com.intuit.players.entity.PlayerEntity;
import com.intuit.players.repository.PlayersRepository;
import com.intuit.players.service.impl.DatabaseServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
class DatabaseServiceTest {

    @Mock
    private PlayersRepository playersRepository;
    private DatabaseService databaseService;

    @BeforeEach
    void setUp() {
        databaseService=new DatabaseServiceImpl(playersRepository);
    }

    @AfterEach
    void tearDown() {
    }

    private PlayerEntity getPlayer1Data(){
        return PlayerEntity.builder()
                .firstName("test")
                .lastName("dummy")
                .gender("male")
                .position("front")
                .team("ABC")
                .id(1).build();
    }

    private PlayerEntity getPlayer2Data(){
        return PlayerEntity.builder()
                .firstName("test2")
                .lastName("dummy")
                .gender("female")
                .position("front")
                .team("Z")
                .id(10).build();
    }

    @Test
    void getPlayerByIdWhenPlayerIsPresent() {
        Mockito.when(playersRepository.findById(1)).thenReturn(Optional.of(getPlayer1Data()));

        PlayerResponse playerById = databaseService.getPlayerById(1);
        Assertions.assertEquals(1, playerById.getId());
        Assertions.assertEquals("test", playerById.getFirstName());
        Assertions.assertEquals("dummy", playerById.getLastName());
        Assertions.assertEquals("male", playerById.getGender());
        Assertions.assertEquals("front", playerById.getPosition());
        Assertions.assertEquals("ABC", playerById.getTeam());
        Assertions.assertEquals(null, playerById.getMessage());
    }

    @Test
    void getPlayerByIdWhenPlayerIsNotPresent() {
        Mockito.when(playersRepository.findById(404)).thenReturn(Optional.empty());
        PlayerResponse playerById = databaseService.getPlayerById(404);
        Assertions.assertEquals("no data found", playerById.getMessage());
    }

    @Test
    void getAllPlayersReturnTwoPlayers() {
        Mockito.when(playersRepository.findAll()).thenReturn(Arrays.asList(getPlayer1Data(), getPlayer2Data()));
        PlayersResponseBean allPlayers = databaseService.getAllPlayers();
        Assertions.assertEquals(2, allPlayers.getTotalPlayers());
        Assertions.assertEquals("test2", allPlayers.getPlayerEntityList().get(1).getFirstName());
        Assertions.assertEquals("test", allPlayers.getPlayerEntityList().get(0).getFirstName());
        Assertions.assertEquals(1, allPlayers.getPlayerEntityList().get(0).getId());
        Assertions.assertEquals(10, allPlayers.getPlayerEntityList().get(1).getId());

    }

    @Test
    void getAllPlayersReturnNoPlayers() {
        Mockito.when(playersRepository.findAll()).thenReturn(Collections.emptyList());
        PlayersResponseBean allPlayers = databaseService.getAllPlayers();
        Assertions.assertEquals(0, allPlayers.getTotalPlayers());
        Assertions.assertEquals(0, allPlayers.getPlayerEntityList().size());

    }

    @Test
    void saveAll() {
    }
}