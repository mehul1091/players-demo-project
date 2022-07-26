package com.intuit.players.controller;

import com.intuit.players.bean.PlayerResponse;
import com.intuit.players.bean.PlayersResponseBean;
import com.intuit.players.service.DataLoaderService;
import com.intuit.players.service.DatabaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/players")
@AllArgsConstructor
@Slf4j
public class PlayersController {

    private final DatabaseService databaseService;
    private final DataLoaderService dataLoaderService;

    @GetMapping
    public ResponseEntity<PlayersResponseBean> getAllPlayers(){
        log.info("fetching all players from database");
        return ResponseEntity.ok(databaseService.getAllPlayers());
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable int playerId){
        log.info("finding data for playerId {}", playerId);
        return ResponseEntity.ok(databaseService.getPlayerById(playerId));
    }


    @PostMapping
    public ResponseEntity<String> csvUpload(@RequestParam("file") MultipartFile csvFile) throws IOException {
        String csvFileOriginalFilename= csvFile.getOriginalFilename();
        log.info("loading csv file : {}", csvFileOriginalFilename);
        dataLoaderService.loadData(csvFile.getInputStream());
        log.info("loading of csv data from file : {} completed", csvFileOriginalFilename);
        return ResponseEntity.ok("data loaded successfully");
    }
}
