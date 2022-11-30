package sk.uniba.fmph.dcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Test;

public class GameIntegrationTest {
    private Game game;

    class NonShufflingPile extends DrawingAndTrashPile {
        public NonShufflingPile(List<Card> cards) {
            super(cards, new Random(0));
        }

        @Override
        protected void shuffleDrawingPile() {
            // don't shuffle
        }
    }
    
    @Test
    public void testGamePlayerCount() {
        for (int i = Game.MIN_PLAYERS; i <= Game.MAX_PLAYERS; i++) {
            game = new Game(i);
            assertEquals(game.getPlayerCount(), game.getPlayers().size());
            assertEquals(game.getPlayerCount(), i);
        }
    }

    @Test
    public void testExchangeCardAction() {
        List<Card> cardsInPile = new ArrayList<>();
        //first player gets
        cardsInPile.add(new Card(CardType.Number, 1));
        cardsInPile.add(new Card(CardType.Number, 2));
        cardsInPile.add(new Card(CardType.Number, 6));
        cardsInPile.add(new Card(CardType.Number, 5));
        cardsInPile.add(new Card(CardType.Number, 3));
        // second player gets
        cardsInPile.add(new Card(CardType.Number, 1));
        cardsInPile.add(new Card(CardType.Number, 2));
        cardsInPile.add(new Card(CardType.Number, 6));
        cardsInPile.add(new Card(CardType.Number, 5));
        cardsInPile.add(new Card(CardType.Number, 3));
        // top of the pile
        cardsInPile.add(new Card(CardType.Number, 99));

        cardsInPile.addAll(Game.getFullCardList());
        Collections.reverse(cardsInPile);

        //create game
        Game game = new Game(2, new NonShufflingPile(cardsInPile));

        // check if the cards aren't modified
        List<Card> playerCards = game.getPlayers().get(0).getPlayerState().getCards();
        assertEquals(playerCards.size(), Game.CARDS_PER_PLAYER);
        assertEquals(playerCards.get(0).getValue(), 1);
        assertEquals(playerCards.get(1).getValue(), 2);
        assertEquals(playerCards.get(2).getValue(), 6);
        assertEquals(playerCards.get(3).getValue(), 5);
        assertEquals(playerCards.get(4).getValue(), 3);
        
        // exchange cards
        game.play(new ExchangeCardAction(2));

        // check what cards the player has on hand
        playerCards = game.getPlayers().get(0).getPlayerState().getCards();
        assertEquals(playerCards.size(), Game.CARDS_PER_PLAYER);
        assertEquals(playerCards.get(0).getValue(), 1);
        assertEquals(playerCards.get(1).getValue(), 2);
        assertEquals(playerCards.get(2).getValue(), 5);
        assertEquals(playerCards.get(3).getValue(), 3);
        assertEquals(playerCards.get(4).getValue(), 99);
    }

    @Test
    public void testEquationExchangeAction() {
        List<Card> cardsInPile = new ArrayList<>();
        //first player gets
        cardsInPile.add(new Card(CardType.Number, 1));
        cardsInPile.add(new Card(CardType.Number, 2));
        cardsInPile.add(new Card(CardType.Number, 6));
        cardsInPile.add(new Card(CardType.Number, 5));
        cardsInPile.add(new Card(CardType.Number, 3));
        // second player gets
        cardsInPile.add(new Card(CardType.Number, 1));
        cardsInPile.add(new Card(CardType.Number, 2));
        cardsInPile.add(new Card(CardType.Number, 6));
        cardsInPile.add(new Card(CardType.Number, 5));
        cardsInPile.add(new Card(CardType.Number, 3));
        // top of the pile
        cardsInPile.add(new Card(CardType.Number, 99));

        cardsInPile.addAll(Game.getFullCardList());
        Collections.reverse(cardsInPile);

        Game game = new Game(2, new NonShufflingPile(cardsInPile));

        // try correct action with cards 1, 2 and 5        
        List<Integer> goodPositions = new ArrayList<>();
        goodPositions.add(0);
        goodPositions.add(1);
        goodPositions.add(4);

        assertTrue(game.play(new EquationExchangeAction(goodPositions)).isPresent());

        // check what cards the player has on hand
        List<Card> playerCards = game.getPlayers().get(0).getPlayerState().getCards();
        assertEquals(playerCards.size(), Game.CARDS_PER_PLAYER);
        assertEquals(playerCards.get(0).getValue(), 6);
        assertEquals(playerCards.get(1).getValue(), 5);
        assertEquals(playerCards.get(2).getValue(), 99);

        // try with 
        List<Integer> badPositions = new ArrayList<>();
        badPositions.add(0);
        badPositions.add(1);
        badPositions.add(2);

        assertFalse(game.play(new EquationExchangeAction(badPositions)).isPresent());

        playerCards = game.getPlayers().get(1).getPlayerState().getCards();
        assertEquals(playerCards.size(), Game.CARDS_PER_PLAYER);
        assertEquals(playerCards.get(0).getValue(), 1);
        assertEquals(playerCards.get(1).getValue(), 2);
        assertEquals(playerCards.get(2).getValue(), 6);
        assertEquals(playerCards.get(3).getValue(), 5);
        assertEquals(playerCards.get(4).getValue(), 3);
    }

    @Test
    public void testQueenWakeupAction() {
        List<Card> cardsInPile = new ArrayList<>();
        //first player gets
        cardsInPile.add(new Card(CardType.Number, 1));
        cardsInPile.add(new Card(CardType.Number, 2));
        cardsInPile.add(new Card(CardType.Number, 6));
        cardsInPile.add(new Card(CardType.King, 98));
        cardsInPile.add(new Card(CardType.Number, 3));
        // second player gets
        cardsInPile.add(new Card(CardType.Number, 1));
        cardsInPile.add(new Card(CardType.Number, 2));
        cardsInPile.add(new Card(CardType.Number, 6));
        cardsInPile.add(new Card(CardType.Number, 5));
        cardsInPile.add(new Card(CardType.Number, 3));
        // top of the pile
        cardsInPile.add(new Card(CardType.Number, 99));

        cardsInPile.addAll(Game.getFullCardList());

        Collections.reverse(cardsInPile);

        Game game = new Game(2, new NonShufflingPile(cardsInPile));

        List<Card> playerCards = game.getPlayers().get(0).getPlayerState().getCards();
        assertEquals(playerCards.size(), Game.CARDS_PER_PLAYER);
        assertEquals(playerCards.get(0).getValue(), 1);
        assertEquals(playerCards.get(1).getValue(), 2);
        assertEquals(playerCards.get(2).getValue(), 6);
        assertEquals(playerCards.get(3).getValue(), 98);
        assertEquals(playerCards.get(4).getValue(), 3);

        assertTrue(game.play(new QueenWakeupAction(3, 0)).isPresent());

        playerCards = game.getPlayers().get(0).getPlayerState().getCards();
        assertEquals(playerCards.size(), Game.CARDS_PER_PLAYER);
        assertEquals(playerCards.get(0).getValue(), 1);
        assertEquals(playerCards.get(1).getValue(), 2);
        assertEquals(playerCards.get(2).getValue(), 6);
        assertEquals(playerCards.get(3).getValue(), 3);
        assertEquals(playerCards.get(4).getValue(), 99);

        assertFalse(game.play(new QueenWakeupAction(2, 0)).isPresent());

        playerCards = game.getPlayers().get(1).getPlayerState().getCards();
        assertEquals(playerCards.size(), Game.CARDS_PER_PLAYER);
        assertEquals(playerCards.get(0).getValue(), 1);
        assertEquals(playerCards.get(1).getValue(), 2);
        assertEquals(playerCards.get(2).getValue(), 6);
        assertEquals(playerCards.get(3).getValue(), 5);
        assertEquals(playerCards.get(4).getValue(), 3);
    }

    @Test
    public void testQueenTheftAction() {
        List<Card> cardsInPile = new ArrayList<>();
        //first player gets
        cardsInPile.add(new Card(CardType.Number, 1));
        cardsInPile.add(new Card(CardType.Number, 2));
        cardsInPile.add(new Card(CardType.Dragon, 95));
        cardsInPile.add(new Card(CardType.Knight, 98));
        cardsInPile.add(new Card(CardType.SleepingPotion, 97));
        // second player gets
        cardsInPile.add(new Card(CardType.Number, 1));
        cardsInPile.add(new Card(CardType.Number, 2));
        cardsInPile.add(new Card(CardType.Number, 6));
        cardsInPile.add(new Card(CardType.Knight, 96));
        cardsInPile.add(new Card(CardType.Number, 3));
        // top of the pile
        cardsInPile.add(new Card(CardType.Number, 99));
        cardsInPile.add(new Card(CardType.Number, 99));
        cardsInPile.add(new Card(CardType.Number, 99));
        cardsInPile.add(new Card(CardType.Number, 99));
        cardsInPile.add(new Card(CardType.Number, 99));
        cardsInPile.add(new Card(CardType.Number, 99));

        cardsInPile.addAll(Game.getFullCardList());

        Collections.reverse(cardsInPile);

        Game game = new Game(2, new NonShufflingPile(cardsInPile));
        game.getPlayers().get(1).getAwokenQueens().addQueen(new Queen(15));
        game.getPlayers().get(1).getAwokenQueens().addQueen(new Queen(25));

        assertTrue(game.play(new AttackAction(3, 1, 0)).isPresent());
        assertEquals(game.getOnTurn(), 1);

        assertEquals(game.getPlayers().get(0).getAwokenQueens().getQueens().get(0).getPoints(), 15);
        assertEquals(game.getPlayers().get(0).getAwokenQueens().getQueens().size(), 1);
        assertEquals(game.getPlayers().get(1).getAwokenQueens().getQueens().get(0).getPoints(), 25);
        assertEquals(game.getPlayers().get(1).getAwokenQueens().getQueens().size(), 1);

        assertFalse(game.play(new AttackAction(3, 1, 0)).isPresent());
        assertEquals(game.getOnTurn(), 1);
        assertFalse(game.play(new AttackAction(4, 0, 0)).isPresent());
        assertEquals(game.getOnTurn(), 1);
        assertTrue(game.play(new AttackAction(3, 0, 0)).isPresent());
        assertEquals(game.getOnTurn(), 0);

        assertEquals(game.getPlayers().get(0).getAwokenQueens().getQueens().get(0).getPoints(), 15);
        assertEquals(game.getPlayers().get(0).getAwokenQueens().getQueens().size(), 1);
        assertEquals(game.getPlayers().get(1).getAwokenQueens().getQueens().get(0).getPoints(), 25);
        assertEquals(game.getPlayers().get(1).getAwokenQueens().getQueens().size(), 1);

        assertFalse(game.play(new AttackAction(2, 0, 0)).isPresent());
        assertEquals(game.getOnTurn(), 0);
        assertFalse(game.play(new AttackAction(1, 1, 0)).isPresent());
        assertEquals(game.getOnTurn(), 0);
        assertTrue(game.play(new AttackAction(2, 1, 0)).isPresent());
        assertEquals(game.getOnTurn(), 1);

        assertEquals(game.getPlayers().get(0).getAwokenQueens().getQueens().size(), 1);
        assertEquals(game.getPlayers().get(1).getAwokenQueens().getQueens().size(), 0);
        assertEquals(game.getPlayers().get(0).getAwokenQueens().getQueens().get(0).getPoints(), 15);
    }
    
}
