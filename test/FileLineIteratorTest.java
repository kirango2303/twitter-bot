/* Tests for FileLineIterator */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

public class FileLineIteratorTest {

    /*
     * Here's a test to help you out, but you still need to write your own.
     *
     * You don't need to create any files for your tests though. (Our submission
     * infrastructure actually won't accept any files you make for testing)
     */

    @Test
    public void testHasNextAndNext() {
        FileLineIterator li = new FileLineIterator("files/simple_test_data.csv");
        assertTrue(li.hasNext());
        assertEquals("0, The end should come here.", li.next());
        assertTrue(li.hasNext());
        assertEquals("1, This comes from data with no duplicate words!", li.next());
        assertFalse(li.hasNext());
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    @Test
    public void testHasNextAndNext2() {
        FileLineIterator li = new FileLineIterator("files/captain_markov_tweets.csv");
        assertTrue(li.hasNext());
        assertEquals(
                "captain_markov,2019-10-13 19:52:02,Captain's log supplemental. "
                + "We will cease to function.", li.next());
        assertTrue(li.hasNext());
    }

    @Test
    public void testHasNextAndNext3() {
        FileLineIterator li = new FileLineIterator("files/just_one_tweets.csv");
        assertTrue(li.hasNext());
        assertEquals("twitterbot,2019-11-08 02:03:04,this is a test", li.next());
        assertFalse(li.hasNext());
        assertThrows(NoSuchElementException.class, () -> {
            li.next();
        });
    }

    @Test
    public void testNullCSVFIlePathThrowsIllArgEx() {
        assertThrows(IllegalArgumentException.class, () -> {
            FileLineIterator li = new FileLineIterator(null);
        });
    }

    @Test
    public void testNonExistentCSVFIlePathThrowsIllArgEx() {
        assertThrows(IllegalArgumentException.class, () -> {
            FileLineIterator li = new FileLineIterator("files/hello.csv");
        });
    }

}
