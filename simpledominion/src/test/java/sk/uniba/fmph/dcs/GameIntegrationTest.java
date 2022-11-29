package sk.uniba.fmph.dcs;

import static org.junit.Assert.assertEquals;

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

        Game game = new Game(2, new NonShufflingPile(cardsInPile));

        List<Card> playerCards = game.getPlayers().get(0).getPlayerState().getCards();
        assertEquals(playerCards.size(), Game.CARDS_PER_PLAYER);
        assertEquals(playerCards.get(0).getValue(), 1);
        assertEquals(playerCards.get(1).getValue(), 2);
        assertEquals(playerCards.get(2).getValue(), 6);
        assertEquals(playerCards.get(3).getValue(), 5);
        assertEquals(playerCards.get(4).getValue(), 3);
        
        game.play(new ExchangeCardAction(2));

        playerCards = game.getPlayers().get(0).getPlayerState().getCards();
        assertEquals(playerCards.size(), Game.CARDS_PER_PLAYER);
        assertEquals(playerCards.get(0).getValue(), 1);
        assertEquals(playerCards.get(1).getValue(), 2);
        assertEquals(playerCards.get(2).getValue(), 5);
        assertEquals(playerCards.get(3).getValue(), 3);
        assertEquals(playerCards.get(4).getValue(), 99);


    }
    
}
