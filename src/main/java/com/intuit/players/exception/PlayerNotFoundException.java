package com.intuit.players.exception;

public class PlayerNotFoundException extends RuntimeException{
    private static final String message = "player with id %s not found";

    public PlayerNotFoundException(int playerId){
        super(String.format(message, playerId));
    }
}
