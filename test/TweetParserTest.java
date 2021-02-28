/* Tests for TweetParser */

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TweetParserTest {

    // A helper function to create a singleton list from a word
    private static List<String> singleton(String word) {
        List<String> l = new LinkedList<String>();
        l.add(word);
        return l;
    }

    // A helper function for creating lists of strings
    private static List<String> listOfArray(String[] words) {
        List<String> l = new LinkedList<String>();
        for (String s : words) {
            l.add(s);
        }
        return l;
    }

    // Cleaning and filtering tests -------------------------------------------
    @Test
    public void removeURLsTest() {
        assertEquals("abc", TweetParser.removeURLs("abc"));
        assertEquals("abc ", TweetParser.removeURLs("abc http://www.cis.upenn.edu"));
        assertEquals(" abc ", TweetParser.removeURLs("http:// abc http:ala34?#?"));
        assertEquals(" abc  def", TweetParser.removeURLs("http:// abc http:ala34?#? def"));
        assertEquals(" abc  def", TweetParser.removeURLs("https:// abc https``\":ala34?#? def"));
        assertEquals("abchttp", TweetParser.removeURLs("abchttp"));
    }

    @Test
    public void testCleanWord() {
        assertEquals("abc", TweetParser.cleanWord("abc"));
        assertEquals("abc", TweetParser.cleanWord("ABC"));
        assertNull(TweetParser.cleanWord("@abc"));
        assertEquals("ab'c", TweetParser.cleanWord("ab'c"));
    }

    @Test
    public void testExtractColumnGetsCorrectColumn() {
        assertEquals(" This is a tweet.",
                TweetParser.extractColumn(
                "wrongColumn, wrong column, wrong column!, This is a tweet.", 3));
    }

    @Test
    public void parseAndCleanSentenceNonEmptyFiltered() {
        List<String> sentence = TweetParser.parseAndCleanSentence("abc #@#F");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        assertEquals(expected, sentence);
    }

    @Test
    public void testParseAndCleanTweetRemovesURLS1() {
        List<List<String>> sentences = TweetParser.parseAndCleanTweet(
                "abc http://www.cis.upenn.edu");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }

    @Test
    public void testCsvFileToTrainingDataSimpleCSV() {
        List<List<String>> tweets = TweetParser.csvFileToTrainingData(
                "files/simple_test_data.csv", 1);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("the end should come here".split(" ")));
        expected.add(listOfArray("this comes from data with no duplicate words".split(" ")));
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvFileToTweetsSimpleCSV() {
        List<String> tweets = TweetParser.csvFileToTweets("files/simple_test_data.csv", 1);
        List<String> expected = new LinkedList<String>();
        expected.add(" The end should come here.");
        expected.add(" This comes from data with no duplicate words!");
        assertEquals(expected, tweets);
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    @Test
    public void testNullCSVFIlePathThrowsIllArgEx() {
        assertThrows(IllegalArgumentException.class, () -> {
            TweetParser.csvFileToTweets(null, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            TweetParser.csvFileToTrainingData(null, 3);
        });
    }

    @Test
    public void testNonExistentCSVFIlePathThrowsIllArgEx() {
        assertThrows(IllegalArgumentException.class, () -> {
            TweetParser.csvFileToTweets("hello", 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            TweetParser.csvFileToTrainingData("hi", 3);
        });
    }

    @Test
    public void testExtractColumn() {
        assertEquals("1", TweetParser.extractColumn("1,2,3", 0));
        assertEquals("2", TweetParser.extractColumn("1,2,3", 1));
        assertEquals("3", TweetParser.extractColumn("1,2,3", 2));
        assertNull(TweetParser.extractColumn("1,2,3", 3));
        assertNull(TweetParser.extractColumn("1,2,3", -1));
    }

    @Test
    public void testcsvFileToTweetsSimple() {
        List<String> tweets = TweetParser.csvFileToTweets("files/just_one_tweets.csv", 2);
        List<String> expected = new LinkedList<String>();
        expected.add("this is a test");
        assertEquals(expected, tweets);
    }

    @Test
    public void testcsvFileToTweetsOutOfBounds() {
        List<String> tweets = TweetParser.csvFileToTweets("files/just_one_tweets.csv", 3);
        List<String> expected = new LinkedList<String>();
        assertEquals(expected, tweets);
    }

    @Test
    public void parseAndCleanSentence1() {
        List<String> sentence = TweetParser.parseAndCleanSentence("Hello my name is Anh");
        List<String> expected = new LinkedList<String>();
        expected.add("hello");
        expected.add("my");
        expected.add("name");
        expected.add("is");
        expected.add("anh");
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentence2() {
        List<String> sentence = TweetParser.parseAndCleanSentence(" ");
        List<String> expected = new LinkedList<String>();
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentence3() {
        List<String> sentence = TweetParser.parseAndCleanSentence("http://www.cis.upenn.edu");
        List<String> expected = new LinkedList<String>();
        assertEquals(expected, sentence);
    }

    @Test
    public void testParseAndCleanTweet1() {
        List<List<String>> sentences = TweetParser.parseAndCleanTweet(
                "http://www.cis.upenn.edu Hello @anh how are #u");
        List<List<String>> expected = new LinkedList<List<String>>();
        String[] arr = { "hello", "how", "are" };
        expected.add(listOfArray(arr));
        assertEquals(expected, sentences);
    }

    @Test
    public void testParseAndCleanTweet2() {
        List<List<String>> sentences = TweetParser.parseAndCleanTweet("Hello. My name is Anh.");
        List<List<String>> expected = new LinkedList<List<String>>();
        String[] arr = { "my", "name", "is", "anh" };
        expected.add(singleton("hello"));
        expected.add(listOfArray(arr));
        assertEquals(expected, sentences);
    }

    @Test
    public void testParseAndCleanTweet3() {
        List<List<String>> sentences = TweetParser.parseAndCleanTweet("http://www.cis.upenn.edu");
        List<String> expected = new LinkedList<String>();
        assertEquals(expected, sentences);
    }

    @Test
    public void testCsvFileToTrainingData1() {
        List<List<String>> tweets = TweetParser.csvFileToTrainingData(
                "files/just_one_tweets.csv", 2);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("this is a test".split(" ")));
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvFileToTrainingData2() {
        List<List<String>> tweets = TweetParser.csvFileToTrainingData(
                "files/just_one_tweets.csv", 3);
        List<String> expected = new LinkedList<String>();
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvFileToTrainingData3() {
        List<List<String>> tweets = TweetParser.csvFileToTrainingData(
                "files/just_one_tweets.csv", -1);
        List<String> expected = new LinkedList<String>();
        assertEquals(expected, tweets);
    }

}
