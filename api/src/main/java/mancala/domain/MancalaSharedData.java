package nl.sogyo.javaopdrachten;

class MancalaSharedData {
    private int playerTurn = 1;
    private int scorePlayer1;
    private int scorePlayer2;
    private int winner;
    private boolean gameEnd;
    final int pocketsPerSide;
    MancalaSharedData(int pocketsPerSide) {
        this.pocketsPerSide = pocketsPerSide;
    }
    int getPlayerTurn() {
        return playerTurn;
    }
    int getScorePlayer1() {
        return scorePlayer1;
    }
    int getScorePlayer2() {
        return scorePlayer2;
    }
    int getWinner() {
        return winner;
    }
    boolean isGameEnd() {
        return gameEnd;
    }
    void setScorePlayer1(int score) {
        scorePlayer1 = score;
    }
    void setScorePlayer2(int score) {
        scorePlayer2 = score;
    }
    void endGame() {
        gameEnd = true;
    }
    void setWinner() {
        switch (Integer.compare(scorePlayer1, scorePlayer2)) {
            case 0 -> winner = 3;
            case 1 -> winner = 1;
            case -1 -> winner = 2;
            default -> winner = 0;
        }
    }
    void switchTurn() {
        if (!gameEnd) {
            playerTurn = 3 - playerTurn;
        }
    }
}
