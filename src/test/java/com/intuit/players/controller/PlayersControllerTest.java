package com.intuit.players.controller;

import com.intuit.players.TestConstants;
import com.intuit.players.bean.PlayerResponse;
import com.intuit.players.bean.PlayersResponseBean;
import com.intuit.players.exception.PlayerNotFoundException;
import com.intuit.players.repository.PlayersRepository;
import com.intuit.players.service.DataLoaderService;
import com.intuit.players.service.DatabaseService;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@WebMvcTest(PlayersController.class)
class PlayersControllerTest {
    @MockBean
    private PlayersRepository playersRepository;
    @MockBean
    private DatabaseService databaseService;
    @MockBean
    private DataLoaderService dataLoaderService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void getAllPlayers(){
        when(databaseService.getAllPlayers()).thenReturn(
                PlayersResponseBean.builder()
                        .totalPlayers(1)
                        .playerEntityList(Arrays.asList(
                                PlayerResponse.builder()
                                        .id(TestConstants.getPlayer1Data().getId())
                                        .team(TestConstants.getPlayer1Data().getTeam())
                                        .firstName(TestConstants.getPlayer1Data().getFirstName())
                                        .lastName(TestConstants.getPlayer1Data().getLastName())
                                        .position(TestConstants.getPlayer1Data().getPosition())
                                        .gender(TestConstants.getPlayer1Data().getGender())
                                        .build())).build());

        String contentAsString = mockMvc.perform(get("/api/players")).andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assertions.assertThat(contentAsString.contains("\"totalPlayers\":1"));
    }

    @Test
    @SneakyThrows
    void getAllPlayersWithNoData(){
        when(databaseService.getAllPlayers()).thenReturn(
                PlayersResponseBean.builder()
                        .totalPlayers(0)
                        .playerEntityList(Collections.emptyList()).build());

        String contentAsString = mockMvc.perform(get("/api/players")).andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assertions.assertThat(contentAsString.contains("\"totalPlayers\":0"));
    }

    @Test
    @SneakyThrows
    void getPlayerById() {
        when(databaseService.getPlayerById(10)).thenReturn(PlayerResponse.builder()
                .id(TestConstants.getPlayer2Data().getId())
                .team(TestConstants.getPlayer2Data().getTeam())
                .firstName(TestConstants.getPlayer2Data().getFirstName())
                .lastName(TestConstants.getPlayer2Data().getLastName())
                .position(TestConstants.getPlayer2Data().getPosition())
                .gender(TestConstants.getPlayer2Data().getGender())
                .build());

        String contentAsString = mockMvc.perform(get("/api/players/10")).andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assertions.assertThat(contentAsString.contains("\"id\":10"));
    }

    @Test
    @SneakyThrows
    void getPlayerByIdPLayerNotFound() {
        when(databaseService.getPlayerById(404))
                .thenThrow(new PlayerNotFoundException(404));

        mockMvc.perform(get("/api/players/404"))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        Assertions.assertThatExceptionOfType(PlayerNotFoundException.class))
                ;

    }
}