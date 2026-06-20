package com.ohualtex.autoclicker.core;

import java.util.Random;

/**
 * Anti-ban "insan modu" gecikme hesabi. Saf (Swing'den bagimsiz) ve test edilebilir;
 * rastgelelik disaridan enjekte edilir (seedlenebilir test icin).
 */
public final class Humanizer {

    private Humanizer() {}

    /**
     * Temel gecikmeye +-%15 organik sapma ekler.
     * @param baseDelay temel gecikme (ms)
     * @param useHumanizer kapaliysa baseDelay aynen (min 1) doner
     * @param rng rastgelelik kaynagi (uretimde paylasimli, testte seedli)
     * @return en az 1 ms olan gecikme
     */
    public static int humanizedDelay(int baseDelay, boolean useHumanizer, Random rng) {
        if (!useHumanizer || baseDelay < 5) return Math.max(1, baseDelay);
        int variance = (int) (baseDelay * 0.15);
        if (variance == 0) variance = 1;
        int offset = rng.nextInt(variance * 2) - variance;
        return Math.max(1, baseDelay + offset);
    }
}
