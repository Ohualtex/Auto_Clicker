import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoClicker extends JFrame implements NativeKeyListener {
    private JSlider cpsSlider;
    private JTextField cpsField;
    private JComboBox<String> clickTypeBox;
    private JLabel statusLabel;
    
    private boolean isRunning = false;
    private Thread clickerThread;
    private Robot robot;

    public AutoClicker() {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Setup Window
        setTitle("AutoClicker Pro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {}

        initUI();
        initJNativeHook();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Status Label
        statusLabel = new JLabel("DURUM: BEKLİYOR", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(Color.RED);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoLabel = new JLabel("Başlatmak/Durdurmak için 'F6' Tuşuna Basın");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Click Type
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        typePanel.add(new JLabel("Tıklama Tipi:"));
        String[] clickTypes = {"Sol Tık", "Sağ Tık", "Orta Tık", "Çift Sol Tık"};
        clickTypeBox = new JComboBox<>(clickTypes);
        typePanel.add(clickTypeBox);

        // CPS Settings
        JPanel cpsPanel = new JPanel();
        cpsPanel.setLayout(new BoxLayout(cpsPanel, BoxLayout.Y_AXIS));
        cpsPanel.setBorder(BorderFactory.createTitledBorder("Hız (CPS - Saniyede Tıklama)"));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cpsSlider = new JSlider(1, 100, 10);
        cpsField = new JTextField("10", 4);
        
        // Sync Slider and TextBox
        cpsSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cpsField.setText(String.valueOf(cpsSlider.getValue()));
            }
        });

        cpsField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int val = Integer.parseInt(cpsField.getText());
                    if(val >= 1 && val <= 100) {
                        cpsSlider.setValue(val);
                    }
                } catch (NumberFormatException ex) {
                    // Ignore non-numbers
                }
            }
        });

        inputPanel.add(cpsSlider);
        inputPanel.add(cpsField);
        cpsPanel.add(inputPanel);

        // Assembly
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(typePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(cpsPanel);

        add(mainPanel);
    }

    private void initJNativeHook() {
        // Disable JNativeHook logging
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException e) {
            System.err.println("Global kısayol tuşu dinleyicisi başlatılamadı.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void toggleClicking() {
        if (isRunning) {
            stopClicking();
        } else {
            startClicking();
        }
    }

    private void startClicking() {
        int cps = cpsSlider.getValue();
        int delay = Math.max(1, 1000 / cps);
        int mode = clickTypeBox.getSelectedIndex(); 
        // 0: Left, 1: Right, 2: Middle, 3: Double Left

        isRunning = true;
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("DURUM: ÇALIŞIYOR");
            statusLabel.setForeground(new Color(0, 150, 0));
            cpsSlider.setEnabled(false);
            cpsField.setEnabled(false);
            clickTypeBox.setEnabled(false);
        });

        clickerThread = new Thread(() -> {
            while (isRunning) {
                try {
                    switch (mode) {
                        case 0: // Left
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            break;
                        case 1: // Right
                            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                            break;
                        case 2: // Middle
                            robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                            break;
                        case 3: // Double
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(50);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            break;
                    }
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        clickerThread.start();
    }

    private void stopClicking() {
        isRunning = false;
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("DURUM: BEKLİYOR");
            statusLabel.setForeground(Color.RED);
            cpsSlider.setEnabled(true);
            cpsField.setEnabled(true);
            clickTypeBox.setEnabled(true);
        });
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_F6) {
            toggleClicking();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) { }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AutoClicker().setVisible(true);
        });
    }
}
