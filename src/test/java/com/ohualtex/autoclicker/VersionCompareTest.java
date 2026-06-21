package com.ohualtex.autoclicker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** AutoClicker.isNewer surum karsilastirmasi (oto-guncelleme kontrolu icin). */
class VersionCompareTest {

    @Test
    void newerMajorAndMinor() {
        assertTrue(AutoClicker.isNewer("9.0", "8.5"));
        assertTrue(AutoClicker.isNewer("8.6", "8.5"));
    }

    @Test
    void numericNotLexical() {
        // "8.10" lexical olarak "8.9"dan kucuk gorunur ama sayisal olarak buyuktur
        assertTrue(AutoClicker.isNewer("8.10", "8.9"));
    }

    @Test
    void sameOrOlderIsNotNewer() {
        assertFalse(AutoClicker.isNewer("8.5", "8.5"));
        assertFalse(AutoClicker.isNewer("8.4", "8.5"));
    }

    @Test
    void differingSegmentCounts() {
        assertTrue(AutoClicker.isNewer("8.5.1", "8.5"));
        assertFalse(AutoClicker.isNewer("8.5", "8.5.1"));
    }

    @Test
    void malformedIsNotNewer() {
        assertFalse(AutoClicker.isNewer("dev", "8.5"));
    }
}
