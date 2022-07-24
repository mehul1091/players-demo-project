package com.intuit.players.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private String team;
    private String position;
    private String message;
}
