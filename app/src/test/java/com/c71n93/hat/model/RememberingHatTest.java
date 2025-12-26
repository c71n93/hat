package com.c71n93.hat.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class RememberingHatTest {
    private static final String WORD_ONE = "one";
    private static final String WORD_TWO = "two";

    @Test
    public void returnsLastPulledAfterUndo() {
        final Hat hat = new Hat(
            Arrays.asList(new Word(WORD_ONE), new Word(WORD_TWO)),
            new Random(3L)
        );
        final RememberingHat remembering = new RememberingHat(hat);
        Assert.assertTrue(remembering.pull().isPresent());
        final Word last = remembering.pull().orElseThrow();
        remembering.undoPull();
        final Word restored = hat.pull().orElseThrow();
        Assert.assertEquals(last.value(), restored.value());
        Assert.assertTrue(hat.pull().isEmpty());
    }

    @Test
    public void undoPullDoesNotDuplicateWord() {
        final Hat hat = new Hat(List.of(new Word(WORD_ONE)), new Random(1L));
        final RememberingHat remembering = new RememberingHat(hat);
        Assert.assertTrue(remembering.pull().isPresent());
        remembering.undoPull();
        remembering.undoPull();
        Assert.assertEquals(1, hat.wordsLeft());
    }

    @Test
    public void undoPullDoesNothingWhenEmpty() {
        final Hat hat = new Hat(new ArrayList<>(), new Random(2L));
        final RememberingHat remembering = new RememberingHat(hat);
        remembering.undoPull();
        Assert.assertTrue(remembering.pull().isEmpty());
        Assert.assertEquals(0, hat.wordsLeft());
    }
}
