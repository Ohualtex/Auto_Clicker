package com.ohualtex.autoclicker.ui;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/** Sekme ve bilgi butonlari icin kodla cizilen vektor ikonlar (FlatLaf foreground rengini kullanir). */
public final class Icons {

    private Icons() {}

    public static class MouseIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.drawRoundRect(x+3, y+1, 10, 14, 6, 6); g2.drawLine(x+8, y+1, x+8, y+5); g2.drawOval(x+7, y+3, 2, 4); g2.dispose();
        }
    }
    public static class KeyIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.drawRoundRect(x+1, y+4, 14, 8, 2, 2);
            g2.fillRect(x+3, y+6, 2, 2); g2.fillRect(x+7, y+6, 2, 2); g2.fillRect(x+11, y+6, 2, 2); g2.fillRect(x+5, y+9, 6, 2); g2.dispose();
        }
    }
    public static class ChainIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(2)); g2.setColor(c.getForeground());
            g2.drawOval(x+1, y+5, 8, 6); g2.drawOval(x+7, y+5, 8, 6); g2.dispose();
        }
    }
    public static class PixelIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.drawOval(x+2, y+2, 12, 12);
            g2.drawLine(x+8, y+0, x+8, y+4); g2.drawLine(x+8, y+12, x+8, y+16);
            g2.drawLine(x+0, y+8, x+4, y+8); g2.drawLine(x+12, y+8, x+16, y+8); g2.fillOval(x+6, y+6, 4, 4); g2.dispose();
        }
    }
    public static class GearIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.setStroke(new BasicStroke(2)); g2.drawOval(x+4, y+4, 8, 8);
            for(int i=0; i<8; i++) { double a = i * Math.PI/4; g2.drawLine(x+8+(int)(4*Math.cos(a)), y+8+(int)(4*Math.sin(a)), x+8+(int)(7*Math.cos(a)), y+8+(int)(7*Math.sin(a))); } g2.dispose();
        }
    }
    public static class InfoIcon implements Icon {
        public int getIconWidth() { return 14; } public int getIconHeight() { return 14; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.drawOval(x+1, y+1, 12, 12); g2.fillRect(x+6, y+4, 2, 2); g2.fillRect(x+6, y+7, 2, 4); g2.dispose();
        }
    }
}
