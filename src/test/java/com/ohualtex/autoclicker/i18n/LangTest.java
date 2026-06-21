package com.ohualtex.autoclicker.i18n;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LangTest {

    @AfterEach
    void reset() { Lang.L = 0; }

    @Test
    void returnsTurkishByDefault() {
        Lang.L = 0;
        assertEquals("Fare", Lang.get("t_mouse"));
    }

    @Test
    void switchesLanguagePerIndex() {
        Lang.L = 1; assertEquals("Mouse", Lang.get("t_mouse"));   // EN
        Lang.L = 5; assertEquals("Мышь", Lang.get("t_mouse"));    // RU (UTF-8 dogrulama)
    }

    @Test
    void unknownKeyReturnsKeyItself() {
        Lang.L = 0;
        assertEquals("nonexistent_key_xyz", Lang.get("nonexistent_key_xyz"));
    }

    @Test
    void outOfRangeIndexFallsBackToFirst() {
        Lang.L = 99;
        assertEquals("Fare", Lang.get("t_mouse"));
    }
}
