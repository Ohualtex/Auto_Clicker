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
    // Config
    private final String CONFIG_FILE = "config.properties";
    private Properties props = new Properties();

    private int triggerKey = NativeKeyEvent.VC_F6; // Default F6
    private boolean listeningForHotkey = false;
    
    // Core states
    private boolean isRunning = false;
    private Thread workerThread;
    private Robot robot;
    private Random random = new Random();

    // UI Variables
    private JLabel statusLabel;
    private JButton hotkeyBtn;
    
    // Mouse specific
    private JComboBox<String> mouseBtnBox;
    private JSlider mouseCpsSlider;
    private JTextField mouseCpsField;
    private JCheckBox mouseHumanizerBox;
    
    // Coordinate targeted clicking
    private JCheckBox targetCoordBox;
    private JLabel coordLabel;
    private Point targetPoint = null;
    private boolean listeningForCoord = false;

    // Keyboard specific
    private JTextField keyTargetField;
    private JSlider keyCpsSlider;
    private JTextField keyCpsField;
    private JCheckBox keyHumanizerBox;
    private int selectedNativeKeyCode = KeyEvent.VK_SPACE;

    public AutoClicker() {
        try {
            robot = new Robot();
        } catch (Exception e) { e.printStackTrace(); }

        loadConfig();
        
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch(Exception e) { }

        setTitle("AutoClicker Ultimate");
        setSize(520, 500);
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
        } catch (Exception e) {
            System.out.println("Config bulunamadı, varsayılanlar kullanılacak.");
        }
    }

    private void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            props.setProperty("hotkey", String.valueOf(triggerKey));
            
            // Mouse
            props.setProperty("mouseCps", String.valueOf(mouseCpsSlider.getValue()));
            props.setProperty("mouseBtn", String.valueOf(mouseBtnBox.getSelectedIndex()));
            props.setProperty("mouseHumanizer", String.valueOf(mouseHumanizerBox.isSelected()));
            
            // Keyboard
            props.setProperty("keyCps", String.valueOf(keyCpsSlider.getValue()));
            props.setProperty("keyTargetCode", String.valueOf(selectedNativeKeyCode));
            props.setProperty("keyHumanizer", String.valueOf(keyHumanizerBox.isSelected()));
            
            props.store(fos, "AutoClicker Configuration");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        statusLabel = new JLabel("DURUM: BEKLİYOR", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        statusLabel.setForeground(new Color(255, 90, 90));
        headerPanel.add(statusLabel);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.addTab("🖱️ Fare Makrosu", buildMousePanel());
        tabbedPane.addTab("⌨️ Klavye Makrosu", buildKeyboardPanel());
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
        } catch (Exception e) {}
    }

    private JPanel buildMousePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Click Type
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(new JLabel("Tıklama Tipi: "));
        String[] types = {"Sol Tık", "Sağ Tık", "Orta Tık", "Çift Sol Tık"};
        mouseBtnBox = new JComboBox<>(types);
        typePanel.add(mouseBtnBox);
        panel.add(typePanel);

        // Targeted Clicking
        JPanel targetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        targetCoordBox = new JCheckBox("Sabit Konuma Tıkla");
        JButton pickCoordBtn = new JButton("Konum Seç");
        coordLabel = new JLabel("(X: -, Y: -)");
        
        pickCoordBtn.addActionListener(e -> {
            pickCoordBtn.setText("Şimdi Seç (Orta Tıkla)");
            listeningForCoord = true;
        });

        targetPanel.add(targetCoordBox);
        targetPanel.add(pickCoordBtn);
        targetPanel.add(coordLabel);
        panel.add(targetPanel);

        // Anti-Ban
        JPanel humanizerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mouseHumanizerBox = new JCheckBox("İnsan Modu (Rastgelemsi Gecikme - Ban Koruması)");
        humanizerPanel.add(mouseHumanizerBox);
        panel.add(humanizerPanel);

        // CPS Slider
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

        // Key to press
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyPanel.add(new JLabel("Aralıklarla Basılacak Tuş:"));
        keyTargetField = new JTextField("Space", 10);
        keyTargetField.setEditable(false);
        JButton assignKeyBtn = new JButton("Tuş Ata");
        
        assignKeyBtn.addActionListener(e -> {
            assignKeyBtn.setText("Bir tuşa basın...");
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

        // Anti-Ban
        JPanel humanizerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyHumanizerBox = new JCheckBox("İnsan Modu (Tuşlara rastgele gecikmeyle basılır)");
        humanizerPanel.add(keyHumanizerBox);
        panel.add(humanizerPanel);

        // CPS Slider
        JPanel cpsPanel = createCpsPanel("Klavye Hızı (Saniyede Basış):", "5", slider -> {
            keyCpsSlider = slider;
        }, field -> {
            keyCpsField = field;
        });
        panel.add(cpsPanel);

        return panel;
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
        
        JTextArea infoArea = new JTextArea(
            "==========================================================\n" +
            "  BİLGİLENDİRME\n" +
            "==========================================================\n\n" +
            "1. Bu panelde ayarlanan özel Kısayol Tuşu uygulamayı başlatıp durdurur.\n" +
            "2. Klavye ve Fare sekmelerinden HANGISI AÇIKSA o çalıştırılır.\n" +
            "3. Konum Seçimi için 'Konum Seç' dedikten sonra ekranın istediğiniz \n   bir yerine gidip farenin Orta Titreşim (Scroll) tuşuna tıklayın.\n" +
            "4. Ayarlarınız siz uygulamayı kapattığınızda otomatik kaydedilir."
        );
        infoArea.setEditable(false);
        infoArea.setOpaque(false);
        infoArea.setBorder(BorderFactory.createEmptyBorder(20, 0,0,0));
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
            else {
                JOptionPane.showMessageDialog(this, "Lütfen Fare veya Klavye sekmesine geçip kısayolu kullanın.");
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
        
        isRunning = true;
        workerThread = new Thread(() -> {
            while (isRunning) {
                try {
                    if (useCoord) {
                        robot.mouseMove(targetPoint.x, targetPoint.y);
                    }
                    
                    switch (mode) {
                        case 0: // Left
                            doMouseClick(InputEvent.BUTTON1_DOWN_MASK); break;
                        case 1: // Right
                            doMouseClick(InputEvent.BUTTON3_DOWN_MASK); break;
                        case 2: // Middle
                            doMouseClick(InputEvent.BUTTON2_DOWN_MASK); break;
                        case 3: // Double Left
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
    
    private void doMouseClick(int mask) {
        robot.mousePress(mask);
        robot.mouseRelease(mask);
    }

    private void startKeyMacro() {
        int cps = keyCpsSlider.getValue();
        int baseDelay = Math.max(1, 1000 / cps);
        boolean useHumanizer = keyHumanizerBox.isSelected();
        final int targetKey = selectedNativeKeyCode;

        isRunning = true;
        workerThread = new Thread(() -> {
            while (isRunning) {
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

    private int getHumanizedDelay(int baseDelay, boolean useHumanizer) {
        if (!useHumanizer) return Math.max(1, baseDelay);
        int variance = (int)(baseDelay * 0.20);
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

        if (ev.getKeyCode() == triggerKey && !listeningForCoord) {
            SwingUtilities.invokeLater(this::toggle);
        }
    }
    
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    // Mouse listener through JNativeHook for setting target coord
    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        if (listeningForCoord && e.getButton() == NativeMouseEvent.BUTTON2) { // Changed to Middle Click (Button 2) for safety
            listeningForCoord = false;
            targetPoint = new Point(e.getX(), e.getY());
            SwingUtilities.invokeLater(() -> {
                coordLabel.setText("(X: " + targetPoint.x + ", Y: " + targetPoint.y + ")");
                JButton btn = (JButton)((JPanel)targetCoordBox.getParent()).getComponent(1);
                btn.setText("Konum Yeniden Seç");
                targetCoordBox.setSelected(true);
            });
        }
    }
    @Override public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {}
    @Override public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoClicker().setVisible(true));
    }
}
