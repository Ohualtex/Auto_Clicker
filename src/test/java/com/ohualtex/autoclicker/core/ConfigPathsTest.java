package com.ohualtex.autoclicker.core;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConfigPathsTest {

    @Test
    void windowsWithAppData_usesAppData() {
        String dir = ConfigPaths.configDir("Windows 11", "C:\\Users\\u\\AppData\\Roaming", "C:\\Users\\u");
        assertEquals("C:\\Users\\u\\AppData\\Roaming" + File.separator + "AutoClicker", dir);
    }

    @Test
    void windowsWithoutAppData_fallsBackToHome() {
        String dir = ConfigPaths.configDir("Windows 11", null, "C:\\Users\\u");
        assertEquals("C:\\Users\\u" + File.separator + ".autoclicker", dir);
    }

    @Test
    void linux_usesHomeDotDir() {
        String dir = ConfigPaths.configDir("Linux", null, "/home/u");
        assertEquals("/home/u" + File.separator + ".autoclicker", dir);
    }

    @Test
    void mac_usesHomeDotDir() {
        String dir = ConfigPaths.configDir("Mac OS X", null, "/Users/u");
        assertEquals("/Users/u" + File.separator + ".autoclicker", dir);
    }
}
