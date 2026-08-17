package nl.sogyo.javaopdrachten;

import Exceptions.UnplayablePocketException;

class MancalaPocket extends MancalaPocketGeneric {
    private final int stepsFromKalaha;
    MancalaPocket(int owner, MancalaSharedData sharedData, int pocketnr) {
        super(owner, sharedData, 4, pocketnr);
        this.stepsFromKalaha = kalahaStepCounter();
    }
    int kalahaStepCounter(){
        if (nextPocket(1) instanceof MancalaKalaha) {
            return 1;
        } else {
            return nextPocket.kalahaStepCounter() + 1;
        }
    }
    private MancalaPocketGeneric oppositePocket(){
        return nextPocket(stepsFromKalaha*2);
    }
    private void claimOpposite () {
        addStones(oppositePocket().getStones());
        oppositePocket().setStones(0);
    }
    void passStones(int stonesAmount) {
        if (stonesAmount > 0) {
            addStones(1);
            stonesAmount -= 1;
        }
        if (oppositePocket().getStones() > 0 && getStones() == 1 && stonesAmount == 0 && getPocketOwner() == sharedData.getPlayerTurn()) {
            claimOpposite();
            getKalaha(getPocketOwner()).addStones(getStones());
            setStones(0);
        }
        if (stonesAmount > 0) {
            nextPocket.passStones(stonesAmount);
        } else {
            checkGameEnd();
            sharedData.switchTurn();
        }
    }
    void playPocket() {
        if (sharedData.getPlayerTurn() == getPocketOwner() && getStones() > 0) {
            int stonesAmount = getStones();
            setStones(0);
            nextPocket.passStones(stonesAmount);
            return;
        }
        if (sharedData.getPlayerTurn() != getPocketOwner()) {
            throw new UnplayablePocketException("This pocket is not yours. Current player: " + sharedData.getPlayerTurn());
        }
        if (getStones() == 0 && sharedData.getPlayerTurn() == getPocketOwner()) {
            throw new UnplayablePocketException("This pocket is empty. Please select another.");
        }
    }
}
