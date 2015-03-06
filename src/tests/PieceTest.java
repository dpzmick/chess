package tests;

import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.pieces.Pawn;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PieceTest {
    @Test
    public void testClone() {
        Player play = new Player(Player.PlayerColor.BLACK);
        Piece p = new Pawn(play);

        try {
            Piece clone = p.clone();
            assertTrue(clone instanceof Pawn);
            assertFalse(play == clone.getOwner());
            assertFalse(p == clone);
        } catch (CloneNotSupportedException e) {
            throw new Error("this shouldn't happen");
        }

    }
}
