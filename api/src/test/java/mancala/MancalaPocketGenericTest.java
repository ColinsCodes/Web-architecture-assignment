package nl.sogyo.javaopdrachten;
import Exceptions.UnplayablePocketException;
import org.junit.jupiter.api.*;

import java.util.InputMismatchException;

public class MancalaPocketGenericTest {
    private MancalaSharedData sharedData;
    private MancalaPocketGeneric pocket1;
    private MancalaFaçade domainInterface;
    @BeforeEach
    public void testSetup() {
        sharedData = new MancalaSharedData(6);
        domainInterface = new MancalaFaçade(6);
        pocket1 = new MancalaPocket(1, sharedData, 1);
        pocket1.getPocket(14).nextPocket = pocket1;
    }
    @Test
    public void createAllPocketsAndKalahas(){
        MancalaSharedData sharedData = new MancalaSharedData(6);
        MancalaPocketGeneric pocket1 =  new MancalaPocket(1, sharedData, 1);
        int stones = pocket1.getPocket(14).getStones();
        Assertions.assertEquals(0, stones);
    }
    @Test
    public void nextPocketInstanceofMancalaPocket() {
        Assertions.assertTrue(pocket1.nextPocket instanceof MancalaPocket);
    }
    @Test
    public void nextPocketZeroEqualsCurrentPocket() {
        Assertions.assertEquals(pocket1, pocket1.nextPocket(0));
    }
    @Test
    public void negativeNextPocketThrowsException() {
        Assertions.assertThrows(InputMismatchException.class, () -> {pocket1.nextPocket(-4);});
    }
    @Test
    public void circularizePockets(){
        int loopCount = 15;
        int stones = pocket1.nextPocket(14 * loopCount).getStones();

        Assertions.assertEquals(4, stones);
    }
    @Test
    public void stonePassing() {
        pocket1.nextPocket(2).playPocket();

        Assertions.assertEquals(0, pocket1.nextPocket(2).getStones());
    }
    @Test
    public void passingNegativeStonesDoesNothing() {
        pocket1.passStones(-1);

        Assertions.assertEquals(4, pocket1.getStones());
    }
    @Test
    public void passingNegativeStonesToKalahaDoesNothing() {
        pocket1.nextPocket(6).setStones(15);
        pocket1.nextPocket(6).passStones(-1);
        Assertions.assertEquals(15, pocket1.nextPocket(6).getStones());
    }
    @Test
    public void pocketsPlayable() {
        pocket1.playPocket();
        Assertions.assertEquals(0, pocket1.getStones());
    }
    @Test
    public void emptyPocketNotPlayable() {
        pocket1.setStones(0);
        Assertions.assertThrows(UnplayablePocketException.class, () -> {pocket1.playPocket();});
    }
    @Test
    public void kahalaNotPlayable() {
        Assertions.assertThrows(UnplayablePocketException.class, () -> pocket1.nextPocket(6).playPocket());
    }
    @Test
    public void stoneReceiving() {
        pocket1.nextPocket(2).playPocket();

        Assertions.assertEquals(5, pocket1.nextPocket(3).getStones());
    }
    @Test
    public void passedStonesNonInfinite() {
        pocket1.nextPocket(2).playPocket();

        Assertions.assertEquals(4, pocket1.nextPocket(10).getStones());
    }
    @Test
    public void playerTurnMatters(){
        Assertions.assertThrows(UnplayablePocketException.class, () -> pocket1.nextPocket(10).playPocket());
    }
    @Test
    public void playerTurnSwitches(){
        pocket1.nextPocket(3).playPocket();

        Assertions.assertEquals(2, sharedData.getPlayerTurn());
    }
    @Test
    public void personalKalahaFilledButOpponentKalahaSkipped(){
        pocket1.nextPocket(5).setStones(10);

        pocket1.nextPocket(5).playPocket();

        Assertions.assertEquals(1, pocket1.nextPocket(6).getStones());
        Assertions.assertEquals(0, pocket1.nextPocket(13).getStones());
    }
    @Test
    public void oppositeStonesClaimedWhenFinalPocketEmpty() {
        pocket1.nextPocket(4).setStones(0);

        pocket1.playPocket();

        Assertions.assertEquals(5, pocket1.nextPocket(6).getStones());
    }
    @Test
    public void oppositeStonesNotClaimedWhenFinalPocketBelongsToOpponent() {
        pocket1.nextPocket(7).setStones(0);
        pocket1.nextPocket(3).playPocket();
        Assertions.assertEquals(5, pocket1.nextPocket(5).getStones());
    }
    @Test
    public void playerNotSwitchedWhenKalahaFinalStone(){
        pocket1.nextPocket(2).playPocket();

        Assertions.assertEquals(1, sharedData.getPlayerTurn());
    }
    @Test
    public void gameCanEnd(){
        pocket1.setStones(0);
        pocket1.nextPocket(1).setStones(0);
        pocket1.nextPocket(2).setStones(0);
        pocket1.nextPocket(3).setStones(0);
        pocket1.nextPocket(4).setStones(0);

        pocket1.nextPocket(5).playPocket();

        Assertions.assertTrue(sharedData.isGameEnd());
    }
    @Test
    public void winnerDeclared(){
        pocket1.setStones(0);
        pocket1.nextPocket(1).setStones(0);
        pocket1.nextPocket(2).setStones(0);
        pocket1.nextPocket(3).setStones(0);
        pocket1.nextPocket(4).setStones(0);
        pocket1.nextPocket(6).setStones(3);

        pocket1.nextPocket(5).playPocket();

        Assertions.assertEquals(2, sharedData.getWinner());
    }
    @Test
    public void tieDeclared() {
        pocket1.setStones(0);
        pocket1.nextPocket(1).setStones(0);
        pocket1.nextPocket(2).setStones(0);
        pocket1.nextPocket(3).setStones(0);
        pocket1.nextPocket(4).setStones(0);
        pocket1.nextPocket(5).setStones(1);
        pocket1.nextPocket(6).setStones(23);

        pocket1.nextPocket(5).playPocket();

        Assertions.assertEquals(3, sharedData.getWinner());
    }
    @Test
    public void errorDeclaredWhenNoWinnerSet() {
        Assertions.assertEquals(0, sharedData.getWinner());
    }
    @Test
    public void simulatedGameLosesNoStones() {
        int[] moves = {3, 6, 9, 10, 1, 11, 1, 12, 1, 13};
        for (int move : moves) {
            domainInterface.playPocket(move);
        }
        Assertions.assertEquals(48, domainInterface.getScore(1) + domainInterface.getScore(2));
        Assertions.assertTrue(domainInterface.isGameEnd());
    }
    @Test
    public void simulatedGameDeclaresWinner() {
        int[] moves = {3, 6, 9, 10, 1, 11, 1, 12, 1, 13};
        for (int move : moves) {
            domainInterface.playPocket(move);
        }
        Assertions.assertEquals(1, domainInterface.getWinner());
    }
    @Test
    public void facadePrintsGameStateAsIntArray() {
        int[] game = domainInterface.gameState();
        Assertions.assertEquals(14, game.length);
    }
    @Test
    public void onlyTwoPlayersExist() {
        Assertions.assertThrows(InputMismatchException.class, () -> domainInterface.getScore(3));
    }
}
