package com.ohualtex.autoclicker.ui;

import com.ohualtex.autoclicker.i18n.Lang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/** Durum-bagimsiz, yeniden kullanilabilir Swing widget kuruculari (bilgi butonu, CPS/sayisal panel). */
public final class Widgets {

    private Widgets() {}

    /** Yaninda info ikonu olan, tiklayinca aciklama gosteren buton. */
    public static JButton infoButton(Component parent, String tooltipKey) {
        JButton btn = new JButton(new Icons.InfoIcon());
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("<html><p width=\"250\">" + Lang.get(tooltipKey) + "</p></html>");
        btn.addActionListener(e -> JOptionPane.showMessageDialog(parent, Lang.get(tooltipKey), Lang.get("info_title"), JOptionPane.INFORMATION_MESSAGE));
        return btn;
    }

    /** Baslikli kenarlik + slider + senkron metin alani (gecersiz girdide kirmizi cerceve) + opsiyonel info butonu. */
    public static JPanel cpsPanel(Component parent, String title, String defaultVal, int max, String infoKey,
                                  Consumer<JSlider> setSlider) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JSlider slider = new JSlider(1, max, Integer.parseInt(defaultVal));
        JTextField field = new JTextField(defaultVal, 5);

        slider.addChangeListener(e -> field.setText(String.valueOf(slider.getValue())));
        field.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                boolean ok = false;
                try {
                    int val = Integer.parseInt(field.getText().trim());
                    if (val >= slider.getMinimum() && val <= max) { slider.setValue(val); ok = true; }
                } catch (Exception ex) { }
                field.putClientProperty("JComponent.outline", (ok || field.getText().trim().isEmpty()) ? null : "error");
                field.repaint();
            }
        });

        setSlider.accept(slider);
        inputPanel.add(slider);
        inputPanel.add(field);
        if (infoKey != null) inputPanel.add(infoButton(parent, infoKey));
        panel.add(inputPanel);
        return panel;
    }
}
