import com.formdev.flatlaf.FlatDarkLaf;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoClicker extends JFrame implements NativeKeyListener, NativeMouseListener {

    enum ActionType { MOUSE_CLICK, KEY_PRESS, MOUSE_MOVE, DELAY }
    
    static class MacroAction {
        ActionType type;
        int p1, p2;
        public MacroAction(ActionType type, int p1, int p2) { this.type = type; this.p1 = p1; this.p2 = p2; }
        public String toString() {
            switch(type) {
                case MOUSE_CLICK: return " Fare Tıklaması: " + (p1==InputEvent.BUTTON1_DOWN_MASK ? "Sol Tık" : p1==InputEvent.BUTTON3_DOWN_MASK ? "Sağ Tık" : p1==999 ? "Çift Sol Tık" : "Orta Tık");
                case KEY_PRESS: return " Klavye Harfi: " + KeyEvent.getKeyText(p1);
                case MOUSE_MOVE: return " Fareyi Işınla: X=" + p1 + ", Y=" + p2;
                case DELAY: return " Bekle (Gecikme): " + p1 + " ms";
                default: return "Bilinmeyen";
            }
        }
        public String serialize() { return type.name()+":"+p1+":"+p2; }
        public static MacroAction deserialize(String s) {
            String[] parts = s.split(":");
            return new MacroAction(ActionType.valueOf(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        }
    }

    private final String CONFIG_FILE = "config.properties";
    private Properties props = new Properties();

    private int triggerKey = NativeKeyEvent.VC_F6; 
    private boolean listeningForHotkey = false;
    
    private boolean isRunning = false;
    private Thread workerThread;
    private Robot robot;
    private Random random = new Random();

    // UI
    private JLabel statusLabel;
    private JButton hotkeyBtn;
    
    // Limits
    private JCheckBox useLimitBox;
    private JComboBox<String> limitTypeBox; 
    private JTextField limitValField;
    private JComboBox<String> limitActionBox;

    // Mouse specific
    private JComboBox<String> mouseBtnBox;
    private JSlider mouseCpsSlider;
    private JTextField mouseCpsField;
    private JCheckBox mouseHumanizerBox;
    private JCheckBox targetCoordBox;
    private JLabel coordLabel;
    private Point targetPoint = null;
    private boolean listeningForCoordParams = false;

    // Keyboard specific
    private JTextField keyTargetField;
    private JSlider keyCpsSlider;
    private JTextField keyCpsField;
    private JCheckBox keyHumanizerBox;
    private int selectedNativeKeyCode = KeyEvent.VK_SPACE;

    // Chain Macro Specific
    private DefaultListModel<MacroAction> chainModel = new DefaultListModel<>();
    private JList<MacroAction> chainList;
    private JCheckBox chainHumanizerBox;
    
    private boolean listeningForMacroCoord = false;
    private JLabel macroCoordLblInfo;
    private Point macroTempPt;

    public AutoClicker() {
        try {
            robot = new Robot();
        } catch (Exception e) { e.printStackTrace(); }

        loadConfig();
        
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch(Exception e) { }

        setTitle("AutoClicker Ultimate v4");
        setSize(560, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        initUI();
        initJNativeHook();
        
        // Save config on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveConfig();
            }
        });
    }

    private void loadConfig() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            triggerKey = Integer.parseInt(props.getProperty("hotkey", String.valueOf(NativeKeyEvent.VC_F6)));
        } catch (Exception e) {}
    }

    private void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            props.setProperty("hotkey", String.valueOf(triggerKey));
            
            props.setProperty("mouseCps", String.valueOf(mouseCpsSlider.getValue()));
            props.setProperty("mouseBtn", String.valueOf(mouseBtnBox.getSelectedIndex()));
            props.setProperty("mouseHumanizer", String.valueOf(mouseHumanizerBox.isSelected()));
            
            props.setProperty("keyCps", String.valueOf(keyCpsSlider.getValue()));
            props.setProperty("keyTargetCode", String.valueOf(selectedNativeKeyCode));
            props.setProperty("keyHumanizer", String.valueOf(keyHumanizerBox.isSelected()));
            
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<chainModel.getSize(); i++){
                sb.append(chainModel.get(i).serialize());
                if(i < chainModel.getSize()-1) sb.append(";");
            }
            props.setProperty("chainActions", sb.toString());
            props.setProperty("chainHumanizer", String.valueOf(chainHumanizerBox.isSelected()));

            props.setProperty("useLimit", String.valueOf(useLimitBox.isSelected()));
            props.setProperty("limitType", String.valueOf(limitTypeBox.getSelectedIndex()));
            props.setProperty("limitVal", limitValField.getText());
            props.setProperty("limitAction", String.valueOf(limitActionBox.getSelectedIndex()));

            props.store(fos, "AutoClicker Configuration");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        statusLabel = new JLabel("DURUM: BEKLİYOR", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        statusLabel.setForeground(new Color(255, 90, 90));
        headerPanel.add(statusLabel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.addTab("🖱️ Fare", buildMousePanel());
        tabbedPane.addTab("⌨️ Klavye", buildKeyboardPanel());
        tabbedPane.addTab("🔗 Kombinasyon", buildChainPanel());
        tabbedPane.addTab("⚙️ Ayarlar", buildSettingsPanel());

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        
        applyConfigToUI();
    }
    
    private void applyConfigToUI() {
        try {
            mouseCpsSlider.setValue(Integer.parseInt(props.getProperty("mouseCps", "10")));
            mouseBtnBox.setSelectedIndex(Integer.parseInt(props.getProperty("mouseBtn", "0")));
            mouseHumanizerBox.setSelected(Boolean.parseBoolean(props.getProperty("mouseHumanizer", "false")));
            
            keyCpsSlider.setValue(Integer.parseInt(props.getProperty("keyCps", "10")));
            selectedNativeKeyCode = Integer.parseInt(props.getProperty("keyTargetCode", String.valueOf(KeyEvent.VK_SPACE)));
            keyTargetField.setText(KeyEvent.getKeyText(selectedNativeKeyCode));
            keyHumanizerBox.setSelected(Boolean.parseBoolean(props.getProperty("keyHumanizer", "false")));
            
            hotkeyBtn.setText(NativeKeyEvent.getKeyText(triggerKey));

            chainHumanizerBox.setSelected(Boolean.parseBoolean(props.getProperty("chainHumanizer", "false")));
            String chainStr = props.getProperty("chainActions", "");
            if(!chainStr.isEmpty()) {
                String[] parts = chainStr.split(";");
                for(String p : parts) {
                    try { chainModel.addElement(MacroAction.deserialize(p)); } catch(Exception e){}
                }
            }

            useLimitBox.setSelected(Boolean.parseBoolean(props.getProperty("useLimit", "false")));
            limitTypeBox.setSelectedIndex(Integer.parseInt(props.getProperty("limitType", "0")));
            limitValField.setText(props.getProperty("limitVal", "60"));
            limitActionBox.setSelectedIndex(Integer.parseInt(props.getProperty("limitAction", "0")));
        } catch (Exception e) {}
    }

    private JPanel buildMousePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(new JLabel("Tıklama Tipi: "));
        String[] types = {"Sol Tık", "Sağ Tık", "Orta Tık", "Çift Sol Tık"};
        mouseBtnBox = new JComboBox<>(types);
        typePanel.add(mouseBtnBox);
        panel.add(typePanel);

        JPanel targetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        targetCoordBox = new JCheckBox("Sabit Konuma Tıkla");
        JButton pickCoordBtn = new JButton("Konum Seç");
        coordLabel = new JLabel("(X: -, Y: -)");
        
        pickCoordBtn.addActionListener(e -> {
            pickCoordBtn.setText("Şimdi Seç (Orta Tıkla)");
            listeningForCoordParams = true;
        });

        targetPanel.add(targetCoordBox);
        targetPanel.add(pickCoordBtn);
        targetPanel.add(coordLabel);
        panel.add(targetPanel);

        JPanel humanizerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mouseHumanizerBox = new JCheckBox("İnsan Modu (Rastgelemsi Gecikme - Ban Koruması)");
        humanizerPanel.add(mouseHumanizerBox);
        panel.add(humanizerPanel);

        JPanel cpsPanel = createCpsPanel("Fare Hızı (CPS):", "10", slider -> {
            mouseCpsSlider = slider;
        }, field -> {
            mouseCpsField = field;
        });
        panel.add(cpsPanel);

        return panel;
    }

    private JPanel buildKeyboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyPanel.add(new JLabel("Aralıklarla Basılacak Tuş:"));
        keyTargetField = new JTextField("Space", 10);
        keyTargetField.setEditable(false);
        JButton assignKeyBtn = new JButton("Tuş Ata");
        
        assignKeyBtn.addActionListener(e -> {
            assignKeyBtn.setText("Basın...");
            keyTargetField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent ev) {
                    selectedNativeKeyCode = ev.getKeyCode();
                    keyTargetField.setText(KeyEvent.getKeyText(selectedNativeKeyCode));
                    keyTargetField.removeKeyListener(this);
                    assignKeyBtn.setText("Tuş Ata");
                }
            });
            keyTargetField.requestFocus();
        });

        keyPanel.add(keyTargetField);
        keyPanel.add(assignKeyBtn);
        panel.add(keyPanel);

        JPanel humanizerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyHumanizerBox = new JCheckBox("İnsan Modu (Tuşlara rastgele gecikmeyle basılır)");
        humanizerPanel.add(keyHumanizerBox);
        panel.add(humanizerPanel);

        JPanel cpsPanel = createCpsPanel("Klavye Hızı (Saniyede Basış):", "5", slider -> {
            keyCpsSlider = slider;
        }, field -> {
            keyCpsField = field;
        });
        panel.add(cpsPanel);

        return panel;
    }

    private JPanel buildChainPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        chainList = new JList<>(chainModel);
        chainList.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chainList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
        JButton addBtn = new JButton("➕ Eylem Ekle");
        JButton remBtn = new JButton("➖ Seçileni Sil");
        JButton clrBtn = new JButton("🗑️ Temizle");

        addBtn.setMaximumSize(new Dimension(150, 35));
        remBtn.setMaximumSize(new Dimension(150, 35));
        clrBtn.setMaximumSize(new Dimension(150, 35));

        addBtn.addActionListener(e -> openAddActionDialog());
        remBtn.addActionListener(e -> {
            int idx = chainList.getSelectedIndex();
            if(idx != -1) chainModel.remove(idx);
        });
        clrBtn.addActionListener(e -> chainModel.clear());

        rightPanel.add(addBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(remBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(clrBtn);
        
        chainHumanizerBox = new JCheckBox("Anti-Ban");
        chainHumanizerBox.setToolTipText("Gecikmelerin üstüne %15 rastgele sapma katar.");
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(chainHumanizerBox);

        panel.add(rightPanel, BorderLayout.EAST);
        
        JLabel hintLabel = new JLabel("İpucu: Bu sekme açıkken F6'ya bastığınızda liste sonsuz döngüde oynatılır.");
        hintLabel.setForeground(Color.GRAY);
        panel.add(hintLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void openAddActionDialog() {
        JDialog dialog = new JDialog(this, "Yeni Eylem Ekle", true);
        dialog.setSize(400, 260);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel configPanel = new JPanel(new CardLayout());
        configPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Fare Tıklaması", "Klavye Tuşu", "Fareyi Taşı", "Bekleme (Gecikme)"});
        
        JComboBox<String> mouseBox = new JComboBox<>(new String[]{"Sol Tık", "Sağ Tık", "Orta Tık", "Çift Sol Tık"});
        JPanel p0 = new JPanel(); p0.add(new JLabel("Tıklama Tipi:")); p0.add(mouseBox);
        
        JTextField keyField = new JTextField("Space", 10); keyField.setEditable(false);
        JButton keyBtn = new JButton("Tuş Ata");
        final int[] selectedActKey = {KeyEvent.VK_SPACE};
        keyBtn.addActionListener(e -> {
            keyBtn.setText("Basın...");
            keyField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ev) {
                    selectedActKey[0] = ev.getKeyCode();
                    keyField.setText(KeyEvent.getKeyText(selectedActKey[0]));
                    keyField.removeKeyListener(this);
                    keyBtn.setText("Tuş Ata");
                }
            });
            keyField.requestFocus();
        });
        JPanel p1 = new JPanel(); p1.add(new JLabel("Basılacak Tuş:")); p1.add(keyField); p1.add(keyBtn);
        
        macroCoordLblInfo = new JLabel("X: 0 Y: 0");
        macroTempPt = new Point(0,0);
        JButton pickBtn = new JButton("Hedef Seç (Orta Tıkla)");
        pickBtn.addActionListener(e -> {
             listeningForMacroCoord = true;
             pickBtn.setText("Bekleniyor...");
             pickBtn.setForeground(Color.RED);
        });
        JPanel p2 = new JPanel(); p2.add(pickBtn); p2.add(macroCoordLblInfo);
        
        JTextField delayField = new JTextField("500", 6);
        JPanel p3 = new JPanel(); p3.add(new JLabel("Beklenecek Süre (Milisaniye):")); p3.add(delayField);
        
        configPanel.add(p0, "0"); configPanel.add(p1, "1"); configPanel.add(p2, "2"); configPanel.add(p3, "3");
        
        typeBox.addActionListener(e -> ((CardLayout)configPanel.getLayout()).show(configPanel, String.valueOf(typeBox.getSelectedIndex())));
        
        JButton addBtn = new JButton("✔ Listeye Ekle");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.addActionListener(e -> {
            int t = typeBox.getSelectedIndex();
            if(t==0) {
                int mIdx = mouseBox.getSelectedIndex();
                int mask = InputEvent.BUTTON1_DOWN_MASK;
                if(mIdx==1) mask = InputEvent.BUTTON3_DOWN_MASK;
                else if(mIdx==2) mask = InputEvent.BUTTON2_DOWN_MASK;
                else if(mIdx==3) mask = 999;
                chainModel.addElement(new MacroAction(ActionType.MOUSE_CLICK, mask, 0));
            } else if(t==1) {
                chainModel.addElement(new MacroAction(ActionType.KEY_PRESS, selectedActKey[0], 0));
            } else if(t==2) {
                chainModel.addElement(new MacroAction(ActionType.MOUSE_MOVE, macroTempPt.x, macroTempPt.y));
            } else if(t==3) {
                try { 
                    chainModel.addElement(new MacroAction(ActionType.DELAY, Integer.parseInt(delayField.getText()), 0)); 
                } catch(Exception ex) {}
            }
            dialog.dispose();
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        top.add(new JLabel("Oluşturulacak Eylem Türü:")); top.add(typeBox);
        dialog.add(top, BorderLayout.NORTH);
        dialog.add(configPanel, BorderLayout.CENTER);
        
        JPanel bot = new JPanel(); bot.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        bot.add(addBtn);
        dialog.add(bot, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }

    private JPanel buildSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel hotkeyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hotkeyPanel.add(new JLabel("Başlat/Durdur Kısayol Tuşu: "));
        
        hotkeyBtn = new JButton("F6");
        hotkeyBtn.setPreferredSize(new Dimension(150, 35));
        hotkeyBtn.addActionListener(e -> {
            listeningForHotkey = true;
            hotkeyBtn.setText("Dinliyor... Basın.");
            hotkeyBtn.setForeground(Color.RED);
        });
        
        hotkeyPanel.add(hotkeyBtn);
        panel.add(hotkeyPanel);
        
        // Limiters Setup
        JPanel limitPanel = new JPanel();
        limitPanel.setLayout(new BoxLayout(limitPanel, BoxLayout.Y_AXIS));
        limitPanel.setBorder(BorderFactory.createTitledBorder("Otomatik Durdurma Sınırları (Limiters)"));
        
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        useLimitBox = new JCheckBox("Sınırlandırıcıyı (Limiter) Aktif Et");
        p1.add(useLimitBox);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Otomasyon "));
        limitValField = new JTextField("60", 4);
        p2.add(limitValField);
        limitTypeBox = new JComboBox<>(new String[]{"Dakika Sonra", "Döngü Sonra (Adet)"});
        p2.add(limitTypeBox);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Eylem: "));
        limitActionBox = new JComboBox<>(new String[]{"Sadece Makroyu Durdur", "Bilgisayarı Kapat (Güvenli Kapanış)"});
        p3.add(limitActionBox);

        limitPanel.add(p1); limitPanel.add(p2); limitPanel.add(p3);
        panel.add(limitPanel);

        JTextArea infoArea = new JTextArea(
            "==========================================================\n" +
            "  BİLGİLENDİRME\n" +
            "==========================================================\n\n" +
            "1. Klavye ve Fare sekmelerinden HANGISI AÇIKSA o çalıştırılır.\n" +
            "2. Ayarlarınız uygulamayı kapattığınızda otomatik kaydedilir."
        );
        infoArea.setEditable(false);
        infoArea.setOpaque(false);
        infoArea.setBorder(BorderFactory.createEmptyBorder(10, 0,0,0));
        panel.add(infoArea);

        return panel;
    }

    private JPanel createCpsPanel(String title, String defaultVal, java.util.function.Consumer<JSlider> setSlider, java.util.function.Consumer<JTextField> setField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JSlider slider = new JSlider(1, 100, Integer.parseInt(defaultVal));
        JTextField field = new JTextField(defaultVal, 5);
        
        slider.addChangeListener(e -> field.setText(String.valueOf(slider.getValue())));
        field.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    int val = Integer.parseInt(field.getText());
                    if (val >= 1 && val <= 100) slider.setValue(val);
                } catch (Exception ex){}
            }
        });

        setSlider.accept(slider);
        setField.accept(field);
        
        inputPanel.add(slider);
        inputPanel.add(new JLabel(" Değer: "));
        inputPanel.add(field);
        panel.add(inputPanel);
        return panel;
    }

    private void initJNativeHook() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            GlobalScreen.addNativeMouseListener(this);
        } catch (NativeHookException e) {
            System.err.println("JNativeHook Kurulum Hatası.");
        }
    }

    private boolean checkLimits(long startTime, int iterationCount) {
        if(!useLimitBox.isSelected() || !isRunning) return true; // continue
        
        try {
            int limitVal = Integer.parseInt(limitValField.getText());
            int type = limitTypeBox.getSelectedIndex();
            
            if (type == 0) { // Minutes
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed >= (limitVal * 60000L)) {
                    executeLimitAction();
                    return false;
                }
            } else if (type == 1) { // Iteration
                if (iterationCount >= limitVal) {
                    executeLimitAction();
                    return false;
                }
            }
        } catch(Exception e) {}
        return true; 
    }

    private void executeLimitAction() {
        isRunning = false;
        int action = limitActionBox.getSelectedIndex();
        if(action == 1) { // Shutdown
            try {
                Runtime.getRuntime().exec("shutdown -s -t 15");
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Limit aşıldı! Bilgisayar 15 saniye içinde kapatılacak!!"));
            } catch(Exception e) {}
        } else { // Stop
            SwingUtilities.invokeLater(() -> {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "Otomasyon Sınırı (Limiti) Doldu. Makro Durduruldu.");
            });
        }
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("DURUM: BEKLİYOR (LİMİT)");
            statusLabel.setForeground(new Color(255, 90, 90));
        });
    }

    private void toggle() {
        if (isRunning) {
            isRunning = false;
            statusLabel.setText("DURUM: BEKLİYOR");
            statusLabel.setForeground(new Color(255, 90, 90));
        } else {
            JTabbedPane tabs = (JTabbedPane) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER);
            int selectedTab = tabs.getSelectedIndex();
            
            if (selectedTab == 0) startMouseMacro();
            else if (selectedTab == 1) startKeyMacro();
            else if (selectedTab == 2) startChainMacro();
            else {
                JOptionPane.showMessageDialog(this, "Lütfen Fare, Klavye veya Zincir sekmesine geçip kısayolu kullanın.");
                return;
            }

            statusLabel.setText("DURUM: ÇALIŞIYOR");
            statusLabel.setForeground(new Color(90, 255, 90));
        }
    }

    private void startMouseMacro() {
        int cps = mouseCpsSlider.getValue();
        int baseDelay = 1000 / Math.max(cps, 1);
        int mode = mouseBtnBox.getSelectedIndex(); 
        boolean useHumanizer = mouseHumanizerBox.isSelected();
        boolean useCoord = targetCoordBox.isSelected() && targetPoint != null;
        
        long startTime = System.currentTimeMillis();
        
        isRunning = true;
        workerThread = new Thread(() -> {
            int iteration = 0;
            while (isRunning) {
                iteration++;
                if (!checkLimits(startTime, iteration)) break;
                
                try {
                    if (useCoord) {
                        robot.mouseMove(targetPoint.x, targetPoint.y);
                    }
                    
                    switch (mode) {
                        case 0: doMouseClick(InputEvent.BUTTON1_DOWN_MASK); break;
                        case 1: doMouseClick(InputEvent.BUTTON3_DOWN_MASK); break;
                        case 2: doMouseClick(InputEvent.BUTTON2_DOWN_MASK); break;
                        case 3: 
                            doMouseClick(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(40);
                            doMouseClick(InputEvent.BUTTON1_DOWN_MASK);
                            break;
                    }
                    
                    int sleepTime = getHumanizedDelay(baseDelay, useHumanizer);
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });
        workerThread.start();
    }
    
    private void startKeyMacro() {
        int cps = keyCpsSlider.getValue();
        int baseDelay = Math.max(1, 1000 / cps);
        boolean useHumanizer = keyHumanizerBox.isSelected();
        final int targetKey = selectedNativeKeyCode;

        long startTime = System.currentTimeMillis();
        
        isRunning = true;
        workerThread = new Thread(() -> {
            int iteration = 0;
            while (isRunning) {
                iteration++;
                if (!checkLimits(startTime, iteration)) break;
                
                try {
                    robot.keyPress(targetKey);
                    robot.delay(20 + random.nextInt(30)); 
                    robot.keyRelease(targetKey);
                    
                    int sleepTime = getHumanizedDelay(baseDelay, useHumanizer);
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });
        workerThread.start();
    }

    private void startChainMacro() {
        if (chainModel.isEmpty()) {
            isRunning = false;
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Önce listeye bir şeyler ekleyin!"));
            return;
        }

        boolean useHumanizer = chainHumanizerBox.isSelected();
        long startTime = System.currentTimeMillis();
        
        isRunning = true;
        workerThread = new Thread(() -> {
            int iteration = 0;
            while (isRunning) {
                iteration++;
                if (!checkLimits(startTime, iteration)) break;
                
                for (int i = 0; i < chainModel.getSize() && isRunning; i++) {
                    MacroAction action = chainModel.get(i);
                    final int idx = i;
                    SwingUtilities.invokeLater(() -> chainList.setSelectedIndex(idx)); 

                    try {
                        switch (action.type) {
                            case MOUSE_CLICK:
                                if (action.p1 == 999) {
                                    doMouseClick(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(40);
                                    doMouseClick(InputEvent.BUTTON1_DOWN_MASK);
                                } else {
                                    doMouseClick(action.p1);
                                }
                                break;
                            case KEY_PRESS:
                                robot.keyPress(action.p1);
                                robot.delay(20 + random.nextInt(30));
                                robot.keyRelease(action.p1);
                                break;
                            case MOUSE_MOVE:
                                robot.mouseMove(action.p1, action.p2);
                                break;
                            case DELAY:
                                int sleepTime = getHumanizedDelay(action.p1, useHumanizer);
                                Thread.sleep(sleepTime);
                                break;
                        }
                        
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            if (!isRunning) SwingUtilities.invokeLater(() -> chainList.clearSelection());
        });
        workerThread.start();
    }

    private void doMouseClick(int mask) {
        robot.mousePress(mask);
        robot.mouseRelease(mask);
    }

    private int getHumanizedDelay(int baseDelay, boolean useHumanizer) {
        if (!useHumanizer || baseDelay < 5) return Math.max(1, baseDelay);
        int variance = (int)(baseDelay * 0.15); // +- 15%
        if (variance == 0) variance = 1;
        int offset = random.nextInt(variance * 2) - variance;
        return Math.max(1, baseDelay + offset);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent ev) {
        if (listeningForHotkey) {
            triggerKey = ev.getKeyCode();
            listeningForHotkey = false;
            SwingUtilities.invokeLater(() -> {
                hotkeyBtn.setText(NativeKeyEvent.getKeyText(triggerKey));
                hotkeyBtn.setForeground(UIManager.getColor("Button.foreground"));
                saveConfig();
            });
            return;
        }

        if (ev.getKeyCode() == triggerKey && !listeningForCoordParams && !listeningForMacroCoord) {
            SwingUtilities.invokeLater(this::toggle);
        }
    }
    
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        if (listeningForCoordParams && e.getButton() == NativeMouseEvent.BUTTON2) { 
            listeningForCoordParams = false;
            targetPoint = new Point(e.getX(), e.getY());
            SwingUtilities.invokeLater(() -> {
                coordLabel.setText("(X: " + targetPoint.x + ", Y: " + targetPoint.y + ")");
                JButton btn = (JButton)((JPanel)targetCoordBox.getParent()).getComponent(1);
                btn.setText("Konum Yeniden Seç");
                targetCoordBox.setSelected(true);
            });
        }
        if (listeningForMacroCoord && e.getButton() == NativeMouseEvent.BUTTON2) {
            listeningForMacroCoord = false;
            macroTempPt = new Point(e.getX(), e.getY());
            SwingUtilities.invokeLater(() -> {
                if(macroCoordLblInfo != null) {
                    macroCoordLblInfo.setText("X: " + macroTempPt.x + " Y: " + macroTempPt.y);
                    macroCoordLblInfo.getParent().getComponent(0).setForeground(Color.GREEN);
                    ((JButton)macroCoordLblInfo.getParent().getComponent(0)).setText("Kilitlendi ✔");
                }
            });
        }
    }
    @Override public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {}
    @Override public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoClicker().setVisible(true));
    }
}
