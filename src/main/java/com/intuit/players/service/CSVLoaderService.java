package com.intuit.players.service;

import com.intuit.players.bean.PlayerObjectFromCSV;
import com.intuit.players.entity.PlayerEntity;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CSVLoaderService {

    @Autowired
    private final DatabaseService databaseService;

    public void loadData(final MultipartFile csvFile){
        List<PlayerObjectFromCSV> listOfPlayersFromCsv;
        final String csvFileOriginalFilename = csvFile.getOriginalFilename();
        log.info("loading csv file : {}", csvFileOriginalFilename);
        try(InputStream inputStream=csvFile.getInputStream();
            Reader reader= new InputStreamReader(inputStream)) {
            listOfPlayersFromCsv = new CsvToBeanBuilder(reader)
                    .withType(PlayerObjectFromCSV.class)
                    .build()
                    .parse();
            log.info("loading of csv data from file : {} completed", csvFileOriginalFilename);
        } catch (FileNotFoundException e) {
            log.error("file [{}] not found", csvFileOriginalFilename);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("exception occurred while parsing data from csv for file [{}]", csvFileOriginalFilename);
            throw new RuntimeException(e);
        }

        saveToDatabase(mapPlayerObjectToEntity(listOfPlayersFromCsv));
        log.info("data uploaded to database successfully");
    }

    private void saveToDatabase(List<PlayerEntity> playerEntities) {
        log.info("saving players list to database");
        databaseService.saveAll(playerEntities);
    }

    private List<PlayerEntity> mapPlayerObjectToEntity(List<PlayerObjectFromCSV> listOfPlayersFromCsv) {
        log.info("transforming csv data to in memory list");
        return listOfPlayersFromCsv.stream().map(player ->
                PlayerEntity.builder()
                        .firstName(player.getFirstName())
                        .lastName(player.getLastName())
                        .gender(player.getGender())
                        .position(player.getPosition())
                        .team(player.getTeam())
                        .build()).collect(Collectors.toList());
    }
}
