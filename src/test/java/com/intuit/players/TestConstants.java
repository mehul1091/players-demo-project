package com.intuit.players;

import com.intuit.players.entity.PlayerEntity;

public class TestConstants {
    public static PlayerEntity getPlayer1Data(){
        return PlayerEntity.builder()
                .firstName("test")
                .lastName("dummy")
                .gender("male")
                .position("front")
                .team("ABC")
                .id(1).build();
    }

    public static PlayerEntity getPlayer2Data(){
        return PlayerEntity.builder()
                .firstName("test2")
                .lastName("dummy")
                .gender("female")
                .position("front")
                .team("Z")
                .id(10).build();
    }
}
