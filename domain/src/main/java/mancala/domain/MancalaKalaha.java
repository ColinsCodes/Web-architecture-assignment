package nl.sogyo.javaopdrachten;

class MancalaKalaha extends MancalaPocketGeneric {
    MancalaKalaha(int owner, MancalaSharedData sharedData, int pocketnr) {
        super(owner, sharedData, 0, pocketnr);
    }
    int kalahaStepCounter() {
        return 0;
    }
    void passStones(int stonesAmount) {
        if (sharedData.getPlayerTurn() == getPocketOwner() && stonesAmount > 0) {
            stonesAmount -= 1;
            addStones(1);
        }
        if (stonesAmount > 0) {
            nextPocket.passStones(stonesAmount);
        } else {
            checkGameEnd();
        }
    }
}
