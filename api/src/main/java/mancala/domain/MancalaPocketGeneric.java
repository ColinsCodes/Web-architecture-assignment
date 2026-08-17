package nl.sogyo.javaopdrachten;

import Exceptions.UnplayablePocketException;

import java.util.InputMismatchException;

abstract class MancalaPocketGeneric {
    private int stones;
    private final int pocketOwner;
    MancalaPocketGeneric nextPocket;
    MancalaSharedData sharedData;
    private final int pocketnr;
    abstract void passStones(int stonesAmount);
    abstract int kalahaStepCounter();

    MancalaPocketGeneric(int owner, MancalaSharedData sharedData, int stones, int pocketnr){
        this.sharedData = sharedData;
        this.pocketOwner = owner;
        this.stones = stones;
        this.pocketnr = pocketnr;
        this.nextPocket = createNextPocket();
    }

    int getStones() {
        return this.stones;
    }
    void setStones(int count) {
        stones = count;
    }
    void addStones(int count){
        stones += count;
    }
    int getPocketOwner() {
        return pocketOwner;
    }

    void playPocket() {
        if (this instanceof MancalaPocket pocket) {
            pocket.playPocket();
        } else {
            throw new UnplayablePocketException("This pocket is a Kalaha and cannot be played.");
        }
    }
    MancalaPocketGeneric nextPocket(int nr){
        if (nr == 0) {
            return this;
        }
        if (nr < 0) {
            throw new InputMismatchException("Invalid input. Please select a positive pocket number (0+)");
        }
        return nextPocket.nextPocket(nr - 1);
    }
    MancalaPocketGeneric getPocket(int pocketnr) {
        if (pocketnr == this.pocketnr) {
            return this;
        }
        if (pocketnr < 1) {
            throw new InputMismatchException("Invalid input. Please select a positive pocket number (0+)");
        }
        return nextPocket.getPocket(pocketnr);
    }
    MancalaPocketGeneric getKalaha(int player) {
        if (this instanceof MancalaKalaha && pocketOwner == player) {
            return this;
        }
        return nextPocket.getKalaha(player);
    }
    private MancalaPocketGeneric getFirstPocketBelongToPlayer(int playerNumber) {
        return getKalaha(3 - playerNumber).nextPocket;
    }
    void checkGameEnd() {
        if (emptyChecker(getFirstPocketBelongToPlayer(1)) || emptyChecker(getFirstPocketBelongToPlayer(2))) {
            tallyScores();
            sharedData.setWinner();
            sharedData.endGame();
        }
    }
    private boolean emptyChecker(MancalaPocketGeneric pocket) {
        if (pocket.getStones() == 0 && !(pocket instanceof MancalaKalaha)) {
            return emptyChecker(pocket.nextPocket);
        }
        return pocket instanceof MancalaKalaha;
    }
    private int scoreAdder(MancalaPocketGeneric firstPocketOfPlayerX){
        return scoreAdder(firstPocketOfPlayerX.nextPocket, firstPocketOfPlayerX.getStones());
    }
    private int scoreAdder(MancalaPocketGeneric pocket, int num){
        if (pocket instanceof MancalaKalaha) {
               return (num + pocket.getStones());
        } else {
            return scoreAdder(pocket.nextPocket, num + pocket.getStones());
        }
    }
    private void tallyScores(){
        sharedData.setScorePlayer1(scoreAdder(getFirstPocketBelongToPlayer(1)));
        sharedData.setScorePlayer2(scoreAdder(getFirstPocketBelongToPlayer(2)));
    }
    private MancalaPocketGeneric createNextPocket() {
        int owner = (pocketnr) / (sharedData.pocketsPerSide+1) + 1;
        if ((pocketnr + 1) % (sharedData.pocketsPerSide+1) == 0) {
            return new MancalaKalaha(owner, sharedData, pocketnr+1);
        }
        if ((pocketnr + 1) < (sharedData.pocketsPerSide + 1) * 2) {
            return new MancalaPocket(owner, sharedData, pocketnr + 1);
        } else {
            return null;
        }
    }
}
