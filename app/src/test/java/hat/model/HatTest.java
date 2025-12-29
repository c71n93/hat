package hat.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public final class HatTest {
    private static final String WORD_ONE = "one";
    private static final String WORD_TWO = "two";

    @Test
    public void returnsEmptyWhenBagIsEmpty() {
        final Hat hat = new Hat(new ArrayList<>(), new Random(0L));
        Assert.assertTrue(hat.pull().isEmpty());
    }

    @Test
    public void pullsAllWordsExactlyOnce() {
        final Hat hat = new Hat(new ArrayList<>(), new Random(7L));
        final List<Word> words = Arrays.asList(
            new Word("alpha"),
            new Word("beta"),
            new Word("gamma")
        );
        words.forEach(hat::put);
        final List<String> pulled = new ArrayList<>();
        for (final Word ignored : words) {
            pulled.add(hat.pull().orElseThrow().value());
        }
        Assert.assertTrue(hat.pull().isEmpty());
        Assert.assertEquals(words.size(), new HashSet<>(pulled).size());
        Assert.assertTrue(pulled.containsAll(Arrays.asList("alpha", "beta", "gamma")));
    }

    @Test
    public void copiesWordsFromIterable() {
        final Words source = new Words(2);
        source.with(new Word(WORD_ONE));
        final Hat hat = new Hat(source);
        source.with(new Word(WORD_TWO));
        Assert.assertEquals(WORD_ONE, hat.pull().orElseThrow().value());
        Assert.assertTrue(hat.pull().isEmpty());
    }

    @Test
    public void doesNotChangeSourceCollection() {
        final List<Word> source = new ArrayList<>();
        source.add(new Word(WORD_ONE));
        source.add(new Word(WORD_TWO));
        final Hat hat = new Hat(source, new Random(3L));
        hat.pull();
        Assert.assertEquals(2, source.size());
        Assert.assertEquals(WORD_ONE, source.get(0).value());
        Assert.assertEquals(WORD_TWO, source.get(1).value());
    }
}
