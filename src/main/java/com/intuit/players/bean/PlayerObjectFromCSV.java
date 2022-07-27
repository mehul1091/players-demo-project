package com.intuit.players.bean;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class PlayerObjectFromCSV {
    @CsvBindByName(column = "id")
    private Integer id;
    @CsvBindByName(column = "first_name")
    private String firstName;
    @CsvBindByName(column = "last_name")
    private String lastName;
    @CsvBindByName(column = "gender")
    private String gender;
    @CsvBindByName(column = "team")
    private String team;
    @CsvBindByName(column = "position")
    private String position;
}
