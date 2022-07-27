package com.intuit.players.service.impl;

import com.intuit.players.bean.PlayerObjectFromCSV;
import com.intuit.players.entity.PlayerEntity;
import com.intuit.players.service.DataLoaderService;
import com.intuit.players.service.DatabaseService;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CSVLoaderServiceImpl implements DataLoaderService {

    @Autowired
    private final DatabaseService databaseService;

    @Override
    public void loadData(InputStream inputStream) throws IOException {
        List<PlayerObjectFromCSV> listOfPlayersFromCsv;
        try(Reader reader= new InputStreamReader(inputStream)) {
            listOfPlayersFromCsv = new CsvToBeanBuilder(reader)
                    .withType(PlayerObjectFromCSV.class)
                    .build()
                    .parse();
        }

        saveToDatabase(mapPlayerObjectToEntity(listOfPlayersFromCsv));
        log.info("data uploaded to database successfully");
    }

    private void saveToDatabase(List<PlayerEntity> playerEntities) {
        databaseService.saveAll(playerEntities);
    }

    private List<PlayerEntity> mapPlayerObjectToEntity(List<PlayerObjectFromCSV> listOfPlayersFromCsv) {
        log.info("transforming csv data to in memory list");
        return listOfPlayersFromCsv.stream()
                .map(player -> PlayerEntity.builder()
                                    .id(player.getId())
                                    .firstName(player.getFirstName())
                                    .lastName(player.getLastName())
                                    .gender(player.getGender())
                                    .position(player.getPosition())
                                    .team(player.getTeam())
                                    .build()).collect(Collectors.toList());
    }

}
