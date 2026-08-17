package nl.sogyo.javaopdrachten;

import java.util.InputMismatchException;

public class MancalaFaçade {
    private final MancalaSharedData sharedData;
    private final MancalaPocketGeneric pocket1;
    public MancalaFaçade(int pocketsPerSide) {
        sharedData = new MancalaSharedData(pocketsPerSide);
        pocket1 = new MancalaPocket(1, sharedData, 1);
        pocket1.getPocket(pocketsPerSide*2+2).nextPocket = pocket1;
    }
    public int gameTurn() {
        return sharedData.getPlayerTurn();
    }
    public boolean isGameEnd() {
        return sharedData.isGameEnd();
    }
    public int getWinner() {
        return sharedData.getWinner();
    }
    public int[] gameState() {
        int[] gameState = new int[sharedData.pocketsPerSide * 2 + 2];
        for (int i = 0; i < sharedData.pocketsPerSide * 2 + 2; i++) {
            gameState[i] = pocket1.getPocket(i+1).getStones();
        }
        return gameState;
    }
    public void playPocket(int pocketNr) {
        pocket1.getPocket(pocketNr).playPocket();
    }
    public int getScore(int playerNr) {
        switch (playerNr) {
            case 1 -> { return sharedData.getScorePlayer1(); }
            case 2 -> { return sharedData.getScorePlayer2(); }
            default -> throw new InputMismatchException("Invalid player: only 2 players can exist.");
        }
    }
}
