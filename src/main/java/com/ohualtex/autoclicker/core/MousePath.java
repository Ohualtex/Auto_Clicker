package com.ohualtex.autoclicker.core;

import java.awt.Point;

/**
 * Iki nokta arasinda insan benzeri (ease-in-out) ara noktalar uretir.
 * Saf ve test edilebilir; gercek fare hareketi cagiran taraf bu noktalari kullanir.
 */
public final class MousePath {

    private MousePath() {}

    /**
     * (x1,y1) -> (x2,y2) arasi ara noktalar. Ilk nokta baslangic, son nokta tam hedeftir.
     * Ease-in-out (smoothstep) ile hizlanip yavaslar.
     * @param steps en az 1; mesafeye gore cagiran taraf belirleyebilir
     */
    public static Point[] interpolate(int x1, int y1, int x2, int y2, int steps) {
        if (steps < 1) steps = 1;
        Point[] pts = new Point[steps + 1];
        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            double e = t * t * (3 - 2 * t); // smoothstep ease-in-out
            int x = (int) Math.round(x1 + (x2 - x1) * e);
            int y = (int) Math.round(y1 + (y2 - y1) * e);
            pts[i] = new Point(x, y);
        }
        return pts;
    }

    /** Mesafeye gore makul adim sayisi (yakin = az adim, uzak = cok, ust sinir 60). */
    public static int stepsFor(int x1, int y1, int x2, int y2) {
        double dist = Math.hypot(x2 - x1, y2 - y1);
        int steps = (int) (dist / 12);
        if (steps < 1) steps = 1;
        if (steps > 60) steps = 60;
        return steps;
    }
}
