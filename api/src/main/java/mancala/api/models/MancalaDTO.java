package mancala.api.models;

import mancala.domain.Playable;

public class MancalaDTO {

    public GameStatusDTO gameStatus;
    public PlayerDTO[] players;

    public MancalaDTO(
            Playable mancala) {
        players = new PlayerDTO[2];
        players[0] = new PlayerDTO(mancala, mancala.getNameOfPlayerOne());
        players[1] = new PlayerDTO(mancala, mancala.getNameOfPlayerTwo());
        gameStatus = new GameStatusDTO(mancala);
    }

    public PlayerDTO[] getPlayers() {
        return players;
    }

    public GameStatusDTO getGameStatus() {
        return gameStatus;
    }
}