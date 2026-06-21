package com.ohualtex.autoclicker.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 6 dilli yerellestirme. Metinler src/main/resources/i18n/messages_&lt;lang&gt;.properties
 * dosyalarinda tutulur (UTF-8; Java 9+ ResourceBundle bunlari UTF-8 okur).
 */
public final class Lang {

    private Lang() {}

    // 0=TR 1=EN 2=DE 3=FR 4=IT 5=RU
    private static final String[] LOCALES = {"tr", "en", "de", "fr", "it", "ru"};

    /** Aktif dil indeksi (langBox tarafindan ayarlanir). */
    public static int L = 0;

    public static String get(String key) {
        try {
            int idx = (L >= 0 && L < LOCALES.length) ? L : 0;
            return ResourceBundle.getBundle("i18n.messages", new Locale(LOCALES[idx])).getString(key);
        } catch (Exception e) {
            return key; // anahtar/dosya bulunamazsa anahtarin kendisi (eski davranis)
        }
    }
}
