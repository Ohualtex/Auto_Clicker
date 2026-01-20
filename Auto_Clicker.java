import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class AutoClicker extends JFrame {

    private JTextField cpsField;
    private JButton toggleButton;
    private JLabel statusLabel;
    private boolean isRunning = false;
    private Thread clickerThread;

    public AutoClicker() {
        // --- Arayüz Ayarları ---
        setTitle("Akıllı AutoClicker");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setAlwaysOnTop(true);

        // --- Bileşenler ---
        JLabel label = new JLabel("Saniyede Tıklama (CPS):");
        cpsField = new JTextField("10", 5);
        
        toggleButton = new JButton("BAŞLAT");
        toggleButton.setPreferredSize(new Dimension(350, 40));
        
        // Bilgilendirme Etiketi
        statusLabel = new JLabel("<html><center>Mouse HAREKET ederken tıklama durur.<br>Kapatmak için SOL ÜST KÖŞEYE götürün.</center></html>");
        statusLabel.setForeground(Color.BLUE);
        statusLabel.setPreferredSize(new Dimension(350, 60));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // --- Buton Aksiyonu ---
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    stopClicking();
                } else {
                    startClicking();
                }
            }
        });

        add(label);
        add(cpsField);
        add(toggleButton);
        add(statusLabel);
    }

    private void startClicking() {
        try {
            int cps = Integer.parseInt(cpsField.getText());
            int targetDelay = 1000 / cps;

            isRunning = true;
            toggleButton.setText("ÇALIŞIYOR... (Mouse'u Sabit Tut)");
            toggleButton.setBackground(Color.GREEN);
            cpsField.setEnabled(false);

            clickerThread = new Thread(() -> {
                try {
                    Robot robot = new Robot();
                    Thread.sleep(2000); // 2 saniye hazırlanma süresi

                    while (isRunning) {
                        // 1. Mouse'un ilk konumunu al
                        Point p1 = MouseInfo.getPointerInfo().getLocation();
                        
                        // ACİL ÇIKIŞ: Sol Üst Köşe
                        if (p1.x < 5 && p1.y < 5) {
                            stopClickingFromThread();
                            break;
                        }

                        // 2. Çok kısa bekle 
                        // Tıklama hızını etkilememesi için gecikmeyi bölüyoruz
                        int checkDuration = Math.min(targetDelay / 2, 50); 
                        Thread.sleep(checkDuration);

                        // 3. Mouse'un son konumunu al
                        Point p2 = MouseInfo.getPointerInfo().getLocation();

                        // 4. HAREKET KONTROLÜ
                        // Eğer mouse 5 pikselden fazla oynamışsa TIKLAMA YAPMA!
                        if (Math.abs(p1.x - p2.x) > 5 || Math.abs(p1.y - p2.y) > 5) {
                            // Kullanıcı mouse'u kaydırıyor, tıklamayı pas geç
                            continue; 
                        }

                        // Mouse sabitse tıkla
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        
                        // Geriye kalan süreyi bekle
                        int remainingDelay = targetDelay - checkDuration;
                        if (remainingDelay > 0) Thread.sleep(remainingDelay);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            clickerThread.start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçerli bir sayı girin!");
        }
    }

    private void stopClicking() {
        isRunning = false;
        resetUI();
    }

    private void stopClickingFromThread() {
        isRunning = false;
        SwingUtilities.invokeLater(() -> {
            resetUI();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Güvenli çıkış yapıldı!");
        });
    }

    private void resetUI() {
        toggleButton.setText("BAŞLAT");
        toggleButton.setBackground(null);
        cpsField.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoClicker().setVisible(true));
    }
}
