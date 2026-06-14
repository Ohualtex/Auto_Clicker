package com.ohualtex.autoclicker.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShutdownCommandTest {

    @Test
    void windows_usesShutdownDashS() {
        ShutdownCommand.Result r = ShutdownCommand.forOs("Windows 11");
        assertTrue(r.isSupported());
        assertArrayEquals(new String[]{"shutdown", "-s", "-t", "15"}, r.command);
        assertEquals("15 sn", r.delayLabel);
    }

    @Test
    void mac_usesShutdownDashH() {
        assertArrayEquals(new String[]{"shutdown", "-h", "+1"}, ShutdownCommand.forOs("Mac OS X").command);
    }

    @Test
    void linux_usesShutdownDashH() {
        assertArrayEquals(new String[]{"shutdown", "-h", "+1"}, ShutdownCommand.forOs("Linux").command);
    }

    @Test
    void unknownOs_isUnsupported() {
        ShutdownCommand.Result r = ShutdownCommand.forOs("SomeExoticOS");
        assertFalse(r.isSupported());
        assertNull(r.command);
    }

    @Test
    void nullOsName_isUnsupported() {
        assertFalse(ShutdownCommand.forOs(null).isSupported());
    }
}
