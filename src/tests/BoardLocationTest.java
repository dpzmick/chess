package tests;

import com.dpzmick.chess.model.board.BoardLocation;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class BoardLocationTest {
    @Test
    public void testChessLetters() {
        BoardLocation b1 = new BoardLocation('A', 1);
        BoardLocation b2 = new BoardLocation(0, 0);

        assertEquals(b1, b2);
    }

    @Test
    public void testChessLettersAnother() {
        BoardLocation b1 = new BoardLocation('D', 4);
        BoardLocation b2 = new BoardLocation(3, 3);

        assertEquals(b1, b2);
    }

    @Test
    public void testCase() {
        BoardLocation b1 = new BoardLocation('d', 4);
        BoardLocation b2 = new BoardLocation(3, 3);

        assertEquals(b1, b2);
    }
}
