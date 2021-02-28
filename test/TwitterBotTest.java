/* Tests for TwitterBot class */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TwitterBotTest {

    String simpleData = "files/simple_test_data.csv";
    String testData = "files/test_data.csv";
    String testLength = "./files/length_clarification.csv";
    String oneTweet = "files/just_one_tweet.csv";

    /*
     * This tests whether your TwitterBot class itself is written correctly
     *
     * This test operates very similarly to our MarkovChain tests in its use of
     * `fixDistribution`, so make sure you know how to test MarkovChain before testing this!
     */
    @Test
    public void simpleTwitterBotTest() {
        List<String> desiredTweet = new ArrayList<>(Arrays.asList(
                "this", "comes", "from", "data", "with", "no", "duplicate", "words", ".", "the",
                "end", "should", "come"
        ));

        TwitterBot t = new TwitterBot(simpleData, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this comes from data with no duplicate words. the end should come.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(63));
        assertEquals(expected, actual);
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    
    @Test
    public void testTwitterBotTest1() {
        List<String> expected = new ArrayList<>(Arrays.asList("i", "need", "food", "!"));
        TwitterBot tb = new TwitterBot(testLength, 2);
        tb.fixDistribution(expected);
        String tweet = tb.generateTweet(12);
        assertEquals("i need food!", tweet);
        assertEquals(tweet.length(), 12);
    }

    @Test
    public void testTwitterBotTest2() {
        List<String> expected = new ArrayList<>(Arrays.asList("i", "need", "food", "!"));
        TwitterBot tb = new TwitterBot(testLength, 2);
        tb.fixDistribution(expected);
        String tweet = tb.generateTweet(7);
        assertEquals("i need food!", tweet);
        assertNotEquals(tweet.length(), 7);
    }
    
    @Test
    public void testTwitterBotTest3() {
        List<String> desiredTweet = new ArrayList<>(Arrays.asList(
                "this", "comes", "from", "data", "with", "no", "duplicate", "words", ".", "the",
                "end", "should", "come"
        ));

        TwitterBot t = new TwitterBot(simpleData, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(3));
        assertEquals(expected, actual);
    }
    
    @Test
    public void testTwitterBotTest4() {
        List<String> desiredTweet = new ArrayList<>(Arrays.asList(
                "this", "comes", "from", "data", "with", "no", "duplicate", "words", ".", "the",
                "end", "should", "come"
        ));

        TwitterBot t = new TwitterBot(simpleData, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this comes from.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(11));
        assertEquals(expected, actual);
    }
    
    @Test
    public void testTwitterBotTest5() {
        assertThrows(IllegalArgumentException.class, () -> {
            TwitterBot t = new TwitterBot("hello", 5);
        });
    }

    
    
    
}
