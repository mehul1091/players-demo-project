package com.intuit.players.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "players_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private String team;
    private String position;
    @CreationTimestamp
    private Timestamp created;
}
