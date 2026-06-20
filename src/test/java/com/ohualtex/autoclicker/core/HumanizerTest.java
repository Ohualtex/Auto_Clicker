package com.ohualtex.autoclicker.core;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HumanizerTest {

    @Test
    void humanizerOff_returnsBaseClampedToOne() {
        assertEquals(100, Humanizer.humanizedDelay(100, false, new Random(1)));
        assertEquals(1, Humanizer.humanizedDelay(0, false, new Random(1)));
        assertEquals(1, Humanizer.humanizedDelay(-5, false, new Random(1)));
    }

    @Test
    void smallBaseDelay_isReturnedUnchanged() {
        // baseDelay < 5 -> sapma uygulanmaz, aynen (min 1) doner
        for (int b = 1; b <= 4; b++) {
            assertEquals(b, Humanizer.humanizedDelay(b, true, new Random(7)));
        }
    }

    @Test
    void withinFifteenPercentWindow() {
        Random rng = new Random(42);
        int base = 100;
        int variance = (int) (base * 0.15); // 15
        for (int i = 0; i < 10_000; i++) {
            int d = Humanizer.humanizedDelay(base, true, rng);
            // offset = nextInt(30) - 15  ->  [-15, 14]
            assertTrue(d >= base - variance && d <= base + variance - 1, "menzil disi: " + d);
        }
    }

    @Test
    void neverZeroOrNegative() {
        Random rng = new Random(3);
        for (int i = 0; i < 10_000; i++) {
            assertTrue(Humanizer.humanizedDelay(5, true, rng) >= 1);
        }
    }

    @Test
    void deterministicWithSameSeed() {
        assertEquals(
            Humanizer.humanizedDelay(50, true, new Random(99)),
            Humanizer.humanizedDelay(50, true, new Random(99)));
    }
}
