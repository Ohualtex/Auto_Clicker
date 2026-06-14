package com.ohualtex.autoclicker.core;

import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

class MousePathTest {

    @Test
    void startsAtSourceEndsAtTarget() {
        Point[] p = MousePath.interpolate(10, 20, 110, 220, 10);
        assertEquals(new Point(10, 20), p[0]);
        assertEquals(new Point(110, 220), p[p.length - 1]);
    }

    @Test
    void hasStepsPlusOnePoints() {
        assertEquals(11, MousePath.interpolate(0, 0, 100, 0, 10).length);
    }

    @Test
    void progressIsMonotonicOnAxis() {
        Point[] p = MousePath.interpolate(0, 0, 100, 0, 20);
        for (int i = 1; i < p.length; i++) {
            assertTrue(p[i].x >= p[i - 1].x, "x geriye gitti: " + p[i - 1].x + " -> " + p[i].x);
        }
    }

    @Test
    void stepsAtLeastOneEvenForZeroDistance() {
        assertEquals(1, MousePath.stepsFor(50, 50, 50, 50));
        Point[] p = MousePath.interpolate(50, 50, 50, 50, MousePath.stepsFor(50, 50, 50, 50));
        assertEquals(new Point(50, 50), p[0]);
        assertEquals(new Point(50, 50), p[p.length - 1]);
    }

    @Test
    void stepsAreCappedForLongDistance() {
        assertTrue(MousePath.stepsFor(0, 0, 5000, 5000) <= 60);
    }
}
