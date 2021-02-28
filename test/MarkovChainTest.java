/* Tests for MarkovChain */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarkovChainTest {

    /*
     * Writing tests for Markov Chain can be a little tricky. We provide a few tests
     * below to help you out, but you still need to write your own.
     */

    @Test
    public void testAddBigram() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("1", "2");
        assertTrue(mc.chain.containsKey("1"));
        ProbabilityDistribution<String> pd = mc.chain.get("1");
        assertTrue(pd.getRecords().containsKey("2"));
        assertEquals(1, pd.count("2"));
    }

    @Test
    public void testTrain() {
        MarkovChain mc = new MarkovChain();
        String sentence = "1 2 3";
        mc.train(Arrays.stream(sentence.split(" ")).iterator());
        assertEquals(3, mc.chain.size());
        ProbabilityDistribution<String> pd1 = mc.chain.get("1");
        assertTrue(pd1.getRecords().containsKey("2"));
        assertEquals(1, pd1.count("2"));
        ProbabilityDistribution<String> pd2 = mc.chain.get("2");
        assertTrue(pd2.getRecords().containsKey("3"));
        assertEquals(1, pd2.count("3"));
        ProbabilityDistribution<String> pd3 = mc.chain.get("3");
        assertTrue(pd3.getRecords().containsKey(null));
        assertEquals(1, pd3.count(null));
    }

    @Test
    public void testWalk() {
        /*
         * Using the sentences "CIS 120 rocks" and "CIS 120 beats CIS 160", we're going
         * to put some bigrams into the Markov Chain.
         *
         * While in the real world, we want the sentence we output to be random, we
         * don't want this in testing. For testing, we want to modify our
         * ProbabilityDistribution such that it will output a predictable chain of
         * words.
         *
         * Luckily, we've provided a `fixDistribution` method that will do this for you!
         * By calling `fixDistribution` with a list of words that you expect to be
         * output, the ProbabilityDistributions will be modified to output your words in
         * that order.
         *
         * See our below test for an example of how to use this.
         */

        String[] expectedWords = { "CIS", "120", "beats", "CIS", "120", "rocks" };
        MarkovChain mc = new MarkovChain();

        String sentence1 = "CIS 120 rocks";
        String sentence2 = "CIS 120 beats CIS 160";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());

        mc.reset("CIS"); // we start with "CIS" since that's the word our desired walk starts with
        mc.fixDistribution(new ArrayList<>(Arrays.asList(expectedWords)));

        for (int i = 0; i < expectedWords.length; i++) {
            assertTrue(mc.hasNext());
            assertEquals(expectedWords[i], mc.next());
        }

    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    @Test
    public void testAddBigram1() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("key", "value1");
        assertTrue(mc.chain.containsKey("key"));
        ProbabilityDistribution<String> pd = mc.chain.get("key");
        assertTrue(pd.getRecords().containsKey("value1"));
        assertEquals(1, pd.count("value1"));
        mc.addBigram("key", "value2");
        assertTrue(pd.getRecords().containsKey("value2"));
        assertEquals(1, pd.count("value2"));
        mc.addBigram("key", "value2");
        assertEquals(2, pd.count("value2"));
    }

    @Test
    public void testAddBigram2() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("key", null);
        mc.addBigram("key", null);
        mc.addBigram("key", "null");
        assertTrue(mc.chain.containsKey("key"));
        ProbabilityDistribution<String> pd = mc.chain.get("key");
        assertTrue(pd.getRecords().containsKey(null));
        assertEquals(2, pd.count(null));
        assertEquals(1, pd.count("null"));
    }

    @Test
    public void testAddBigramIllArEx() {
        MarkovChain mc = new MarkovChain();
        assertThrows(IllegalArgumentException.class, () -> {
            mc.addBigram(null, "value");
        });
    }

    @Test
    public void testTrainIllArEx() {
        MarkovChain mc = new MarkovChain();
        assertThrows(IllegalArgumentException.class, () -> {
            mc.train(null);
        });
    }

    @Test
    public void testTrain1() {
        MarkovChain mc = new MarkovChain();
        String sentence1 = "my name is anh";
        String sentence2 = "hi anh how are you anh";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());
        assertEquals(8, mc.chain.size());
        ProbabilityDistribution<String> pd1 = mc.chain.get("my");
        assertTrue(pd1.getRecords().containsKey("name"));
        assertEquals(1, pd1.count("name"));
        ProbabilityDistribution<String> pd2 = mc.chain.get("how");
        assertTrue(pd2.getRecords().containsKey("are"));
        assertEquals(1, pd2.count("are"));
        ProbabilityDistribution<String> pd3 = mc.chain.get("anh");
        assertTrue(pd3.getRecords().containsKey(null));
        assertTrue(pd3.getRecords().containsKey("how"));
        assertEquals(2, pd3.count(null));
        assertEquals(1, pd3.count("how"));
    }

    @Test
    public void testTrain2() {
        MarkovChain mc = new MarkovChain();
        String sentence1 = "1 2 3";
        String sentence2 = "1 2 3";
        String sentence3 = "1 2 3";
        String sentence4 = "1 2 4";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());
        mc.train(Arrays.stream(sentence3.split(" ")).iterator());
        mc.train(Arrays.stream(sentence4.split(" ")).iterator());
        assertEquals(4, mc.chain.size());
        ProbabilityDistribution<String> pd1 = mc.chain.get("1");
        assertTrue(pd1.getRecords().containsKey("2"));
        assertEquals(4, pd1.count("2"));
        ProbabilityDistribution<String> pd2 = mc.chain.get("2");
        assertTrue(pd2.getRecords().containsKey("3"));
        assertEquals(3, pd2.count("3"));
        assertEquals(1, pd2.count("4"));
        ProbabilityDistribution<String> pd3 = mc.chain.get("3");
        assertTrue(pd3.getRecords().containsKey(null));
        assertEquals(3, pd3.count(null));
    }

    @Test
    public void testTrainEmptySentence() {
        MarkovChain mc = new MarkovChain();
        String sentence = " ";
        mc.train(Arrays.stream(sentence.split(" ")).iterator());
        assertEquals(0, mc.chain.size());
    }

    @Test
    public void testWalk1() {
        MarkovChain mc = new MarkovChain();
        String sentence1 = "A";
        String sentence2 = "B";
        String sentence3 = "C";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());
        mc.train(Arrays.stream(sentence3.split(" ")).iterator());
        mc.reset("Z");
        assertTrue(mc.hasNext());
        assertEquals("Z", mc.next());
        assertFalse(mc.hasNext());
    }

    @Test
    public void testWalk2() {

        MarkovChain mc = new MarkovChain();

        String sentence1 = "CIS 120 rocks";
        String sentence2 = "CIS 120 beats CIS 160";
        String sentence3 = "CIS 120 is fun";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());
        mc.train(Arrays.stream(sentence3.split(" ")).iterator());

        mc.reset("CIS");

        String[] expectedWords1 = { "CIS", "120", "beats", "CIS", "120", "is", "fun" };
        mc.fixDistribution(new ArrayList<>(Arrays.asList(expectedWords1)));
        for (int i = 0; i < expectedWords1.length; i++) {
            assertTrue(mc.hasNext());
            assertEquals(expectedWords1[i], mc.next());
        }
    }

    @Test
    public void testWalk3() {

        MarkovChain mc = new MarkovChain();

        String sentence1 = "CIS 120 rocks";
        String sentence2 = "CIS 120 beats CIS 160";
        String sentence3 = "CIS 120 is fun";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());
        mc.train(Arrays.stream(sentence3.split(" ")).iterator());

        mc.reset("CIS");

        String[] expectedWords2 = { "CIS", "120", "beats", "CIS", "120", "rocks" };
        mc.fixDistribution(new ArrayList<>(Arrays.asList(expectedWords2)));
        for (int i = 0; i < expectedWords2.length; i++) {
            assertTrue(mc.hasNext());
            assertEquals(expectedWords2[i], mc.next());
        }
    }

    @Test
    public void testWalk4() {

        MarkovChain mc = new MarkovChain();

        String sentence1 = "CIS 120 rocks";
        String sentence2 = "CIS 120 beats CIS 160";
        String sentence3 = "CIS 120 is fun";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());
        mc.train(Arrays.stream(sentence3.split(" ")).iterator());

        mc.reset("CIS");
        String[] expectedWords3 = { "CIS", "120", "is", "fun" };
        mc.fixDistribution(new ArrayList<>(Arrays.asList(expectedWords3)));
        for (int i = 0; i < expectedWords3.length; i++) {
            assertTrue(mc.hasNext());
            assertEquals(expectedWords3[i], mc.next());
        }

    }

}
