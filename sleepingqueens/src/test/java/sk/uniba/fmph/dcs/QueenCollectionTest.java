package sk.uniba.fmph.dcs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueenCollectionTest  {
    private QueenCollection queenCollection;

    void setUp() {
        queenCollection = new QueenCollection();
    }
    
    @Test
    public void testQueenAddition() {
    	setUp();
        for (int i = 0; i < 12; i++) {
            queenCollection.addQueen(new Queen(i));
        }
        for (int i = 0; i < 12; i++) {
            assertEquals(queenCollection.getQueens().get(i).getPoints(), i);
        }
    }
 }
        
