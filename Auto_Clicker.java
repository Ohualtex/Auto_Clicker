import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AutoClicker extends JFrame {

    private JTextField cpsField; // Saniyedeki Tıklama Sayısı (CPS)
    private JButton toggleButton; // Başlat/Durdur tek buton
    private boolean isRunning = false;
    private Thread clickerThread;

    public AutoClicker() {
        // --- Arayüz Ayarları ---
        setTitle("AutoClicker");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setAlwaysOnTop(true); // Pencere hep üstte
        setFocusable(true);   // Klavye dinlemesi için odaklanabilir yap

        // --- Bileşenler ---
        JLabel label = new JLabel("Saniyede Kaç Tık (CPS):");
        cpsField = new JTextField("10", 5); // Varsayılan 10 CPS
        toggleButton = new JButton("BAŞLAT (Space)");

        // --- Klavye Kısayolu (Space Tuşu) ---
        // Pencere seçiliyken Boşluk tuşuna basınca tetiklenir
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    toggleClicker();
                }
            }
        });

        // --- Buton Aksiyonu ---
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleClicker();
            }
        });

        // Bileşenleri ekle
        add(label);
        add(cpsField);
        add(toggleButton);
    }

    // --- Başlat / Durdur Mantığı ---
    private void toggleClicker() {
        if (isRunning) {
            stopClicking();
        } else {
            startClicking();
        }
        // Tuşa basıldıktan sonra odak pencerede kalsın ki Space çalışmaya devam etsin
        this.requestFocus();
    }

    private void startClicking() {
        try {
            // CPS Hesabı: Saniyede X tık demek, 1000ms / X kadar beklemek demektir.
            int cps = Integer.parseInt(cpsField.getText());
            if (cps <= 0) {
                JOptionPane.showMessageDialog(this, "0'dan büyük bir değer girin!");
                return;
            }

            // Eğer CPS çok yüksekse bilgisayar donabilir, sınır koyalım (Opsiyonel)
            if (cps > 500) {
                JOptionPane.showMessageDialog(this, "500 CPS üzeri bilgisayarı dondurabilir!");
                return;
            }

            int delay = 1000 / cps; // Bekleme süresini hesapla

            isRunning = true;
            toggleButton.setText("DURDUR (Space)");
            toggleButton.setBackground(Color.RED); // Görsel uyarı
            cpsField.setEnabled(false); // Çalışırken değiştirmeyi engelle

            clickerThread = new Thread(() -> {
                try {
                    Robot robot = new Robot();
                    // Kullanıcıya hazırlanması için 2 saniye süre ver
                    Thread.sleep(2000);

                    while (isRunning) {
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        Thread.sleep(delay);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            clickerThread.start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayı girin!");
        }
    }

    private void stopClicking() {
        isRunning = false;
        toggleButton.setText("BAŞLAT (Space)");
        toggleButton.setBackground(null); // Rengi normale döndür
        cpsField.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AutoClicker().setVisible(true);
        });
    }
}
