package com.ohualtex.autoclicker;

import com.ohualtex.autoclicker.core.Humanizer;
import com.ohualtex.autoclicker.core.ShutdownCommand;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoClicker extends JFrame implements NativeKeyListener, NativeMouseListener {

    static class Lang {
        static int L = 0; // 0=TR, 1=EN, 2=DE, 3=FR, 4=IT, 5=RU
        static java.util.Map<String, String[]> d = new java.util.HashMap<>();
        static {
            d.put("st_idle", new String[]{"DURUM: BEKLIYOR", "STATUS: IDLE", "STATUS: BEREIT", "STATUT: EN ATTENTE", "STATO: IN ATTESA", "СТАТУС: ОЖИДАНИЕ"});
            d.put("st_run", new String[]{"DURUM: CALISIYOR", "STATUS: RUNNING", "STATUS: LÄUFT", "STATUT: EN COURS", "STATO: IN ESECUZIONE", "СТАТУС: РАБОТАЕТ"});
            d.put("st_lim", new String[]{"DURUM: LIMIT YENDI", "STATUS: LIMIT HIT", "STATUS: LIMIT ERREICHT", "STATUT: LIMITE", "STATO: LIMITE RAGGIUNTO", "СТАТУС: ЛИМИТ"});
            
            d.put("t_mouse", new String[]{"Fare", "Mouse", "Maus", "Souris", "Mouse", "Мышь"});
            d.put("t_key", new String[]{"Klavye", "Keyboard", "Tastatur", "Clavier", "Tastiera", "Клавиатура"});
            d.put("t_chain", new String[]{"Zincir", "Chain", "Kette", "Chaîne", "Catena", "Цепь"});
            d.put("t_px", new String[]{"Piksel", "Pixel", "Pixel", "Pixel", "Pixel", "Пиксель"});
            d.put("t_set", new String[]{"Ayarlar", "Settings", "Einstellungen", "Paramètres", "Impostazioni", "Настройки"});
            
            d.put("c_type", new String[]{"Tiklama Tipi: ", "Click Type: ", "Klick-Typ: ", "Type de clic: ", "Tipo di clic: ", "Тип клика: "});
            d.put("l_click", new String[]{"Sol Tik", "Left Click", "Linksklick", "Clic Gauche", "Clic Sinistro", "Левый Клик"});
            d.put("r_click", new String[]{"Sag Tik", "Right Click", "Rechtsklick", "Clic Droit", "Clic Destro", "Правый Клик"});
            d.put("m_click", new String[]{"Orta Tik", "Mid Click", "Mittelklick", "Clic Milieu", "Clic Centrale", "Средний Клик"});
            d.put("d_click", new String[]{"Cift Sol Tik", "Double Click", "Doppelklick", "Double Clic", "Doppio Clic", "Двойной Клик"});
            
            d.put("fix_loc", new String[]{"Sabit Konuma Tikla", "Click Fixed Loc", "Feste Position", "Bouton Fixe", "Pos. Fissa", "Фикс. Место"});
            d.put("pick_loc", new String[]{"Konum Sec", "Pick Loc", "Wählen", "Choisir", "Seleziona", "Выбрать"});
            d.put("wait_mid", new String[]{"Bekliyor (Orta Tik)", "Waiting (Mid Click)", "Wartet (Mittelklick)", "Attente (Milieu)", "Attesa (Centrale)", "Ожидание (Ср. клик)"});
            
            d.put("anti_ban", new String[]{"Insan Modu (Rastgele Gecikme)", "Humanizer (Random Delay)", "Menschenmodus (Zufall)", "Mode Humain (Délais Aléat.)", "Modalità Umana (Ritardo)", "Анти-Бан (случайная задержка)"});
            d.put("cps", new String[]{"Fare Hizi (CPS):", "Mouse Speed (CPS):", "Maus-Geschw. (CPS):", "Vitesse Souris (CPS):", "Velocità Mouse (CPS):", "Скорость Мыши (CPS):"});
            d.put("key_cps", new String[]{"Klavye Hizi (Saniyede):", "Key Speed (/sec):", "Tasten-Geschw. (/s):", "Vitesse Touche (/s):", "Velocità Tasto (/s):", "Скор. Клавиатуры (/с):"});
            
            d.put("key_trg", new String[]{"Basilacak Tus:", "Target Key:", "Zieltaste:", "Touche Cible:", "Tasto Obiettivo:", "Целевая Клавиша:"});
            d.put("set_k", new String[]{"Tus Ata", "Set Key", "Setzen", "Définir", "Assegna", "Назначить"});
            d.put("press_k", new String[]{"Basin...", "Press...", "Drücken...", "Appuyez...", "Premi...", "Нажмите..."});
            
            d.put("add_act", new String[]{"(+) Eylem Ekle", "(+) Add Action", "(+) Aktion", "(+) Ajouter", "(+) Aggiungi", "(+) Добавить"});
            d.put("del_act", new String[]{"(-) Secileni Sil", "(-) Remove", "(-) Löschen", "(-) Supprimer", "(-) Rimuovi", "(-) Удалить"});
            d.put("clr_act", new String[]{"(x) Temizle", "(x) Clear", "(x) Leeren", "(x) Effacer", "(x) Pulisci", "(x) Очистить"});
            
            d.put("act_typ", new String[]{"Eylem Turu:", "Action Type:", "Aktionsart:", "Type d'action:", "Tipo di Azione:", "Тип Действия:"});
            d.put("ms_delay", new String[]{"Milisaniye (Gecikme):", "Delay (ms):", "Verzögerung (ms):", "Délai (ms):", "Ritardo (ms):", "Задержка (мс):"});
            d.put("list_add", new String[]{"[v] Listeye Ekle", "[v] Add to List", "[v] Hinzufügen", "[v] Ajouter", "[v] Aggiungi", "[v] Добавить"});
            d.put("tnt_mouse", new String[]{"Fare Tiklamasi", "Mouse Click", "Mausklick", "Clic Souris", "Clic Mouse", "Клик Мыши"});
            d.put("tnt_key", new String[]{"Klavye Tusu", "Key Press", "Taste Drücken", "Appui Touche", "Pressione Tasto", "Нажатие Клавиши"});
            d.put("tnt_move", new String[]{"Fareyi Tasi", "Move Mouse", "Maus Bewegen", "Bouger Souris", "Sposta Mouse", "Сдвинуть Мышь"});
            
            d.put("px_1", new String[]{"1. Gozetlenecek Piksel", "1. Target Pixel", "1. Zielpixel", "1. Pixel Cible", "1. Pixel Obiettivo", "1. Цель Пиксель"});
            d.put("px_clr", new String[]{"Renk:", "Color:", "Farbe:", "Couleur:", "Colore:", "Цвет:"});
            d.put("px_2", new String[]{"2. Tetiklenme Sartlari", "2. Conditions", "2. Bedingungen", "2. Conditions", "2. Condizioni", "2. Условия"});
            d.put("px_cond1", new String[]{"Renk ESLESTIGINDE", "When Color MATCHES", "Farbe ÜBEREINSTIMMT", "Couleur CORRESPOND", "Colore CORRISPONDE", "Когда совпадает"});
            d.put("px_cond2", new String[]{"Renk DEGISTIGINDE", "When Color CHANGES", "Farbe WECHSELT", "Couleur CHANGE", "Colore CAMBIA", "Когда меняется"});
            d.put("px_tol", new String[]{"Tolerans Payi (%):", "Tolerance (%):", "Toleranz (%):", "Tolérance (%):", "Tolleranza (%):", "Допуск (%):"});
            d.put("px_3", new String[]{"3. Gerceklesecek Tepki", "3. Reaction Action", "3. Reaktion", "3. Réaction", "3. Reazione", "3. Реакция"});
            d.put("px_act3", new String[]{"Ozel Tusa Bas", "Press Custom Key", "Zieltaste drücken", "Appuyer Touche", "Premi Tasto", "Своя Клавиша"});
            d.put("px_act4", new String[]{"Sadece Zili Cal", "Only Beep", "Nur Piepen", "Bip Sonore", "Suona Solo", "Только Звук"});
            d.put("px_rate", new String[]{"Tarama Hizi (Ms):", "Scanner Rate (Ms):", "Scanrate (Ms):", "Vitesse Scan (Ms):", "Velocità Scan (Ms):", "Скорость (Мс):"});
            
            d.put("set_hk", new String[]{"Kisayol Tusu (Baslat/Durdur):", "Start/Stop Hotkey:", "Start/Stopp Hotkey:", "Raccourci Start/Stop:", "Tasto Avvio/Arresto:", "Горячая Клавиша:"});
            d.put("lim_title", new String[]{"Otomatik Durdurma Sinirlari", "Auto Stop Limiters", "Auto-Stopp Begrenzungen", "Limites Arrêt Auto", "Limiti Arresto Auto", "Лимиты Автоостановки"});
            d.put("lim_use", new String[]{"Limitoru Aktif Et", "Enable Limiter", "Limiter aktivieren", "Activer Limiteur", "Abilita Limitatore", "Вкл. Лимитер"});
            d.put("lim_after", new String[]{"Sinir:", "Limit:", "Limit:", "Dans:", "Dopo:", "Лимит:"});
            d.put("lim_min", new String[]{"Dakika Sonra", "Minutes Later", "Minuten", "Minutes", "Minuti", "Минут"});
            d.put("lim_iter", new String[]{"Dongu Sonra", "Iterations Later", "Iterationen", "Itérations", "Iterazioni", "Итераций"});
            d.put("lim_stop", new String[]{"Sadece Makroyu Durdur", "Just Stop Macro", "Makro stoppen", "Arrêter Macro", "Ferma Macro", "Только стоп"});
            d.put("lim_shut", new String[]{"Bilgisayari Kapat", "Shutdown PC", "PC herunterfahren", "Éteindre le PC", "Spegni PC", "Выкл ПК"});
            
            d.put("style", new String[]{"(O) Gorunum Ozellestirme", "(O) UI Customization", "(O) UI Anpassung", "(O) Apparence UI", "(O) Aspetto UI", "(O) Дизайн UI"});
            d.put("theme", new String[]{"Ana Tema:", "Main Theme:", "Hauptthema:", "Thème:", "Tema Principale:", "Тема:"});
            d.put("theme_d", new String[]{"Karanlik Mod", "Dark Mode", "Dunkler Modus", "Sombre", "Modo Scuro", "Темный"});
            d.put("theme_l", new String[]{"Aydinlik Mod", "Light Mode", "Heller Modus", "Clair", "Modo Chiaro", "Светлый"});
            d.put("txt_size", new String[]{"Yazi Boyutu:", "Text Size:", "Textgröße:", "Taille:", "Dimensione:", "Размер:"});
            d.put("txt_col", new String[]{"Metin Rengi:", "Text Color:", "Textfarbe:", "Couleur Texte:", "Colore Testo:", "Цвет Текста:"});
            d.put("col_pick", new String[]{"Renk Sec", "Pick Color", "Farbe", "Choisir", "Seleziona", "Выбрать Цвет"});
            d.put("apply_s", new String[]{"Uygula/Kaydet", "Apply/Save", "Anwenden", "Appliquer", "Applica", "Применить"});
            d.put("lang_title", new String[]{"(L) Dil (Language)", "(L) Language", "(L) Sprache", "(L) Langue", "(L) Lingua", "(L) Язык"});
            
            d.put("info_hum", new String[]{"Anti-Ban: Robotik tiklamalari saptirmak icin gecikmelere minik sapmalar ekler.", "Anti-Ban: Adds random fluctuations to delays to simulate human behavior and evade detection.", "Anti-Ban: Fügt den Verzögerungen zufällige Schwankungen hinzu.", "Anti-Ban: Ajoute des fluctuations aléatoires aux délais.", "Anti-Ban: Aggiunge fluttuazioni casuali ai ritardi.", "Анти-Бан: Добавляет случайные колебания к задержкам."});
            d.put("info_lim", new String[]{"Otomasyonu belirli bir sure sonra kapatir.", "Stops the automation automatically after a set time or cycle count. Ideal for AFK macros.", "Stoppt die Automatisierung automatisch nach einer Weile.", "Arrête automatiquement l'automatisation.", "Ferma l'automazione in base a limiti.", "Останавливает автоматизацию при достижении лимита."});
            d.put("info_px", new String[]{"Renk Toleransi: Ufak golge farkliliklarinin renk algisini bozmasini engeller.", "Color Tolerance: Prevents minor in-game shading/lighting shifts from ruining detection.", "Farbtoleranz: Verhindert kleine Schattenfehler.", "Tolérance: Empêche les petits changements de lumière de fausser la détection.", "Tolleranza: Evita che l'illuminazione rompa l'algoritmo.", "Допуск: Игнорирует изменения освещения."});
            d.put("info_cps", new String[]{"Saniyedeki tiklama hizini belirler (Click Per Second).", "Sets the click speed per second (CPS).", "Legt die Klicks pro Sekunde fest.", "Définit la vitesse de clic (CPS).", "Imposta i clic al secondo (CPS).", "Устанавливает кликов в секунду (CPS)."});
            d.put("info_px_cond", new String[]{"Eslestiginde: Renk gorundugunde tepki verir.\nDegistiginde: Renk kayboldugunda tepki verir.", "Matches: Reacts when color appears.\nChanges: Reacts when color disappears.", "Stimmt überein: Reagiert, wenn die Farbe erscheint.", "Correspond: Agit quand la couleur apparait.", "Corrisponde: Agisce quando il colore appare.", "Совпадает: Реагирует на появление цвета."});
            d.put("info_px_rate", new String[]{"Tarama Hizi: Ekranin ne siklikla kontrol edilecegini belirler (Milisaniye).", "Scan Rate: How often to check the screen (Milliseconds).", "Scanrate: Wie oft der Bildschirm überprüft wird (ms).", "Taux de scan: Fréquence de vérification (ms).", "Velocità scan: Frequenza di controllo schermo (ms).", "Скорость: Частота проверки экрана (мс)."});

            d.put("info_title", new String[]{"Bilgi", "Info", "Info", "Info", "Info", "Информация"});
            d.put("reset", new String[]{"Sifirla", "Reset", "Zurücksetzen", "Réinit.", "Reimposta", "Сброс"});
            d.put("shut_ok", new String[]{"Limit asildi! Bilgisayar %s icinde kapatilacak.", "Limit reached! PC will shut down in %s.", "Limit erreicht! PC fährt in %s herunter.", "Limite atteinte! Le PC s'eteindra dans %s.", "Limite raggiunto! Il PC si spegnera tra %s.", "Лимит достигнут! ПК выключится через %s."});
            d.put("shut_title", new String[]{"Bilgisayar Kapatiliyor", "Shutting Down", "Herunterfahren", "Arret du PC", "Spegnimento PC", "Выключение ПК"});
            d.put("shut_pending", new String[]{"Limit asildi! Bilgisayar %s icinde kapanacak.\nVazgecmek icin asagidaki butona basin.", "Limit reached! PC shuts down in %s.\nPress the button below to cancel.", "Limit erreicht! PC fährt in %s herunter.\nZum Abbrechen unten klicken.", "Limite atteinte! Arret dans %s.\nCliquez ci-dessous pour annuler.", "Limite raggiunto! Spegnimento tra %s.\nPremi sotto per annullare.", "Лимит достигнут! Выключение через %s.\nНажмите ниже для отмены."});
            d.put("shut_cancel_btn", new String[]{"Kapatmayi Iptal Et", "Cancel Shutdown", "Abbrechen", "Annuler l'arret", "Annulla spegnimento", "Отменить выключение"});
            d.put("shut_cancelled", new String[]{"Bilgisayar kapatma iptal edildi.", "Shutdown cancelled.", "Herunterfahren abgebrochen.", "Arret annule.", "Spegnimento annullato.", "Выключение отменено."});
            d.put("lim_invalid", new String[]{"DURUM: GECERSIZ LIMIT DEGERI", "STATUS: INVALID LIMIT VALUE", "STATUS: UNGULTIGER LIMITWERT", "STATUT: LIMITE INVALIDE", "STATO: LIMITE NON VALIDO", "СТАТУС: НЕВЕРНЫЙ ЛИМИТ"});
            d.put("shut_fail", new String[]{"Kapatma komutu basarisiz oldu: ", "Shutdown command failed: ", "Herunterfahren fehlgeschlagen: ", "Echec de la commande d'arret: ", "Comando di spegnimento fallito: ", "Сбой команды выключения: "});
            d.put("shut_unsup", new String[]{"Bu isletim sisteminde otomatik kapatma desteklenmiyor: ", "Automatic shutdown not supported on this OS: ", "Automatisches Herunterfahren auf diesem OS nicht unterstützt: ", "Arret automatique non supporte sur cet OS: ", "Spegnimento automatico non supportato su questo OS: ", "Автовыключение не поддерживается в этой ОС: "});
        }
        static String get(String key) { return d.containsKey(key) ? d.get(key)[L] : key; }
    }

    enum ActionType { MOUSE_CLICK, KEY_PRESS, MOUSE_MOVE, DELAY }
    
    static class MacroAction {
        ActionType type;
        int p1, p2;
        public MacroAction(ActionType type, int p1, int p2) { this.type = type; this.p1 = p1; this.p2 = p2; }
        public String toString() {
            switch(type) {
                case MOUSE_CLICK: return Lang.get("tnt_mouse") + ": " + (p1==InputEvent.BUTTON1_DOWN_MASK ? Lang.get("l_click") : p1==InputEvent.BUTTON3_DOWN_MASK ? Lang.get("r_click") : p1==999 ? Lang.get("d_click") : Lang.get("m_click"));
                case KEY_PRESS: return Lang.get("tnt_key") + ": " + KeyEvent.getKeyText(p1);
                case MOUSE_MOVE: return Lang.get("tnt_move") + ": X=" + p1 + ", Y=" + p2;
                case DELAY: return Lang.get("ms_delay") + " " + p1;
                default: return "?";
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

    private volatile int triggerKey = NativeKeyEvent.VC_F6;
    private volatile boolean listeningForHotkey = false;

    private volatile boolean isRunning = false;
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

    // Mouse
    private JComboBox<String> mouseBtnBox;
    private JSlider mouseCpsSlider;
    private JCheckBox mouseHumanizerBox;
    private JCheckBox targetCoordBox;
    private JLabel coordLabel;
    private volatile Point targetPoint = null;
    private volatile boolean listeningForCoordParams = false;

    // Keyboard
    private JTextField keyTargetField;
    private JSlider keyCpsSlider;
    private JCheckBox keyHumanizerBox;
    private int selectedNativeKeyCode = KeyEvent.VK_SPACE;

    // Chain Macro
    private DefaultListModel<MacroAction> chainModel = new DefaultListModel<>();
    private JList<MacroAction> chainList;
    private JCheckBox chainHumanizerBox;
    private volatile boolean listeningForMacroCoord = false;
    private JLabel macroCoordLblInfo;
    private volatile Point macroTempPt;

    // Pixel Trigger
    private volatile boolean listeningForPixelCoord = false;
    private volatile Point pixelPoint = null;
    private volatile Color pixelColor = null;
    private JLabel pixelCoordLbl;
    private JPanel pixelColorPreview;
    private JComboBox<String> pxConditionBox;
    private JComboBox<String> pxActionBox;
    private JSlider pxToleranceSlider;
    private JSlider pxRateSlider;
    private JTextField pxKeyField;
    private int pxSelectedKey = KeyEvent.VK_SPACE;

    // Style & Lang
    private JComboBox<String> themeBox;
    private JSlider fontSlider;
    private JPanel customColorPreview;
    private JComboBox<String> langBox;

    public AutoClicker() {
        try {
            robot = new Robot();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "java.awt.Robot baslatilamadi; uygulama calisamaz.\n" + e.getMessage(),
                "Kritik Hata", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        loadConfig();
        Lang.L = Integer.parseInt(props.getProperty("langIndex", "0"));
        applyInitialTheme();

        setTitle("AutoClicker Ultimate v" + appVersion());
        setSize(600, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        initUI();
        initJNativeHook();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveConfig();
            }
        });
    }

    private void rebuildUI() {
        JTabbedPane tabs = (JTabbedPane) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER);
        int currentTab = (tabs != null) ? tabs.getSelectedIndex() : 0;
        int scrollVal = 0;
        if (currentTab == 4 && tabs != null) {
            JScrollPane scroll = (JScrollPane) tabs.getComponentAt(4);
            scrollVal = scroll.getVerticalScrollBar().getValue();
        }

        saveConfig();
        getContentPane().removeAll();
        applyInitialTheme(); 
        initUI();

        tabs = (JTabbedPane) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (tabs != null) {
            tabs.setSelectedIndex(currentTab);
            if (currentTab == 4) {
                JScrollPane scroll = (JScrollPane) tabs.getComponentAt(4);
                final int finalScroll = scrollVal;
                SwingUtilities.invokeLater(() -> scroll.getVerticalScrollBar().setValue(finalScroll));
            }
        }
        revalidate();
        repaint();
    }

    // Native 2D Icons
    static class MouseIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.drawRoundRect(x+3, y+1, 10, 14, 6, 6); g2.drawLine(x+8, y+1, x+8, y+5); g2.drawOval(x+7, y+3, 2, 4); g2.dispose();
        }
    }
    static class KeyIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.drawRoundRect(x+1, y+4, 14, 8, 2, 2);
            g2.fillRect(x+3, y+6, 2, 2); g2.fillRect(x+7, y+6, 2, 2); g2.fillRect(x+11, y+6, 2, 2); g2.fillRect(x+5, y+9, 6, 2); g2.dispose();
        }
    }
    static class ChainIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(2)); g2.setColor(c.getForeground());
            g2.drawOval(x+1, y+5, 8, 6); g2.drawOval(x+7, y+5, 8, 6); g2.dispose();
        }
    }
    static class PixelIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.drawOval(x+2, y+2, 12, 12);
            g2.drawLine(x+8, y+0, x+8, y+4); g2.drawLine(x+8, y+12, x+8, y+16);
            g2.drawLine(x+0, y+8, x+4, y+8); g2.drawLine(x+12, y+8, x+16, y+8); g2.fillOval(x+6, y+6, 4, 4); g2.dispose();
        }
    }
    static class GearIcon implements Icon {
        public int getIconWidth() { return 16; } public int getIconHeight() { return 16; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.setStroke(new BasicStroke(2)); g2.drawOval(x+4, y+4, 8, 8);
            for(int i=0; i<8; i++) { double a = i * Math.PI/4; g2.drawLine(x+8+(int)(4*Math.cos(a)), y+8+(int)(4*Math.sin(a)), x+8+(int)(7*Math.cos(a)), y+8+(int)(7*Math.sin(a))); } g2.dispose();
        }
    }
    static class InfoIcon implements Icon {
        public int getIconWidth() { return 14; } public int getIconHeight() { return 14; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground()); g2.drawOval(x+1, y+1, 12, 12); g2.fillRect(x+6, y+4, 2, 2); g2.fillRect(x+6, y+7, 2, 4); g2.dispose();
        }
    }

    private JButton createInfoButton(String tooltipKey) {
        JButton btn = new JButton(new InfoIcon());
        btn.setMargin(new Insets(0,0,0,0));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("<html><p width=\"250\">" + Lang.get(tooltipKey) + "</p></html>");
        btn.addActionListener(e -> JOptionPane.showMessageDialog(this, Lang.get(tooltipKey), Lang.get("info_title"), JOptionPane.INFORMATION_MESSAGE));
        return btn;
    }

    private void applyColorsRecursively(Component c, Color color) {
        if (c == null || c == statusLabel) return;
        c.setForeground(color);
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                applyColorsRecursively(child, color);
            }
        }
    }

    private void applyInitialTheme() {
        boolean isDark = Boolean.parseBoolean(props.getProperty("themeDark", "true"));
        int fSize = Integer.parseInt(props.getProperty("fontSize", "14"));
        Color fColor = props.containsKey("fontColor") ? new Color(Integer.parseInt(props.getProperty("fontColor"))) : null;
        applyThemeAndFont(isDark, fSize, fColor);
    }
    
    private void applyThemeAndFont(boolean isDark, int fontSize, Color fgColor) {
        try {
            if(isDark) UIManager.setLookAndFeel(new FlatDarkLaf());
            else UIManager.setLookAndFeel(new FlatLightLaf());

            FontUIResource f = new FontUIResource(new Font("Segoe UI", Font.PLAIN, fontSize));
            java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.getDefaults().get(key);
                if (value instanceof FontUIResource || value instanceof Font) {
                    UIManager.put(key, f);
                }
            }
            if (fgColor != null) {
                UIManager.put("Label.foreground", fgColor);
                UIManager.put("CheckBox.foreground", fgColor);
                UIManager.put("RadioButton.foreground", fgColor);
                UIManager.put("TitledBorder.titleColor", fgColor);
                UIManager.put("TabbedPane.foreground", fgColor);
                UIManager.put("Button.foreground", fgColor);
                UIManager.put("ComboBox.foreground", fgColor);
                UIManager.put("TextField.foreground", fgColor);
                UIManager.put("List.foreground", fgColor);
                UIManager.put("Panel.foreground", fgColor);
            } else {
                UIManager.put("Label.foreground", null);
                UIManager.put("CheckBox.foreground", null);
                UIManager.put("RadioButton.foreground", null);
                UIManager.put("TitledBorder.titleColor", null);
                UIManager.put("TabbedPane.foreground", null);
                UIManager.put("Button.foreground", null);
                UIManager.put("ComboBox.foreground", null);
                UIManager.put("TextField.foreground", null);
                UIManager.put("List.foreground", null);
                UIManager.put("Panel.foreground", null);
            }
            SwingUtilities.updateComponentTreeUI(this);
            
            if (fgColor != null) {
                applyColorsRecursively(this, fgColor);
            }
            
            if (statusLabel != null) {
                statusLabel.setFont(new Font("Segoe UI", Font.BOLD, fontSize + 8));
                statusLabel.setForeground(isRunning ? new Color(90, 255, 90) : new Color(255, 90, 90));
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadConfig() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            triggerKey = Integer.parseInt(props.getProperty("hotkey", String.valueOf(NativeKeyEvent.VC_F6)));
        } catch (Exception e) {
            // Ilk acilista dosya yoklugu normaldir; digerlerini bildir
            if (!(e instanceof java.io.FileNotFoundException)) {
                System.err.println("[AutoClicker] Config okunamadi: " + e.getMessage());
            }
        }
    }

    private void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            props.setProperty("hotkey", String.valueOf(triggerKey));
            
            props.setProperty("mouseCps", String.valueOf(mouseCpsSlider.getValue()));
            props.setProperty("mouseBtn", String.valueOf(mouseBtnBox.getSelectedIndex()));
            props.setProperty("mouseHumanizer", String.valueOf(mouseHumanizerBox.isSelected()));
            props.setProperty("mouseUseCoord", String.valueOf(targetCoordBox.isSelected()));
            if (targetPoint != null) {
                props.setProperty("mouseTargetX", String.valueOf(targetPoint.x));
                props.setProperty("mouseTargetY", String.valueOf(targetPoint.y));
            }

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

            // Pixel config
            if (pixelPoint != null && pixelColor != null) {
                props.setProperty("pxX", String.valueOf(pixelPoint.x));
                props.setProperty("pxY", String.valueOf(pixelPoint.y));
                props.setProperty("pxR", String.valueOf(pixelColor.getRed()));
                props.setProperty("pxG", String.valueOf(pixelColor.getGreen()));
                props.setProperty("pxB", String.valueOf(pixelColor.getBlue()));
            }
            props.setProperty("pxTolerance", String.valueOf(pxToleranceSlider.getValue()));
            props.setProperty("pxRate", String.valueOf(pxRateSlider.getValue()));
            props.setProperty("pxCondition", String.valueOf(pxConditionBox.getSelectedIndex()));
            props.setProperty("pxAction", String.valueOf(pxActionBox.getSelectedIndex()));
            props.setProperty("pxSelectCode", String.valueOf(pxSelectedKey));

            props.setProperty("themeDark", String.valueOf(themeBox.getSelectedIndex() == 0));
            props.setProperty("fontSize", String.valueOf(fontSlider.getValue()));
            if (!customColorPreview.getBackground().equals(Color.WHITE)) {
                props.setProperty("fontColor", String.valueOf(customColorPreview.getBackground().getRGB()));
            } else {
                // WHITE = varsayilan: ozel renk anahtarini tamamen kaldir ki RESET gercekten varsayilana donsun
                props.remove("fontColor");
            }
            
            props.setProperty("langIndex", String.valueOf(langBox.getSelectedIndex()));

            props.store(fos, "AutoClicker Configuration");
        } catch (Exception e) {
            System.err.println("[AutoClicker] Config kaydedilemedi: " + e.getMessage());
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statusLabel = new JLabel(Lang.get("st_idle"), SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        statusLabel.setForeground(new Color(255, 90, 90));
        headerPanel.add(statusLabel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.addTab(Lang.get("t_mouse"), new MouseIcon(), buildMousePanel());
        tabbedPane.addTab(Lang.get("t_key"), new KeyIcon(), buildKeyboardPanel());
        tabbedPane.addTab(Lang.get("t_chain"), new ChainIcon(), buildChainPanel());
        tabbedPane.addTab(Lang.get("t_px"), new PixelIcon(), buildPixelPanel());
        JScrollPane setScroll = new JScrollPane(buildSettingsPanel());
        setScroll.setBorder(null);
        tabbedPane.addTab(Lang.get("t_set"), new GearIcon(), setScroll);

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        
        applyConfigToUI();
        
        Color fColor = props.containsKey("fontColor") ? new Color(Integer.parseInt(props.getProperty("fontColor"))) : null;
        if (fColor != null) applyColorsRecursively(this, fColor);
    }
    
    private void applyConfigToUI() {
        try {
            mouseCpsSlider.setValue(Integer.parseInt(props.getProperty("mouseCps", "10")));
            mouseBtnBox.setSelectedIndex(Integer.parseInt(props.getProperty("mouseBtn", "0")));
            mouseHumanizerBox.setSelected(Boolean.parseBoolean(props.getProperty("mouseHumanizer", "false")));
            if (props.containsKey("mouseTargetX") && props.containsKey("mouseTargetY")) {
                targetPoint = new Point(Integer.parseInt(props.getProperty("mouseTargetX")), Integer.parseInt(props.getProperty("mouseTargetY")));
                coordLabel.setText("(X: " + targetPoint.x + ", Y: " + targetPoint.y + ")");
            }
            targetCoordBox.setSelected(Boolean.parseBoolean(props.getProperty("mouseUseCoord", "false")));

            keyCpsSlider.setValue(Integer.parseInt(props.getProperty("keyCps", "5")));
            selectedNativeKeyCode = Integer.parseInt(props.getProperty("keyTargetCode", String.valueOf(KeyEvent.VK_SPACE)));
            keyTargetField.setText(KeyEvent.getKeyText(selectedNativeKeyCode));
            keyHumanizerBox.setSelected(Boolean.parseBoolean(props.getProperty("keyHumanizer", "false")));
            
            hotkeyBtn.setText(NativeKeyEvent.getKeyText(triggerKey));

            chainHumanizerBox.setSelected(Boolean.parseBoolean(props.getProperty("chainHumanizer", "false")));
            String chainStr = props.getProperty("chainActions", "");
            chainModel.clear();
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

            // px ui load (bozuk/eksik pixel config tum applyConfigToUI'yi kesmesin diye kendi try'inda)
            try {
                if (props.containsKey("pxX") && props.containsKey("pxY")
                        && props.containsKey("pxR") && props.containsKey("pxG") && props.containsKey("pxB")) {
                    pixelPoint = new Point(Integer.parseInt(props.getProperty("pxX")), Integer.parseInt(props.getProperty("pxY")));
                    pixelColor = new Color(Integer.parseInt(props.getProperty("pxR")), Integer.parseInt(props.getProperty("pxG")), Integer.parseInt(props.getProperty("pxB")));
                    pixelCoordLbl.setText("X: " + pixelPoint.x + " Y: " + pixelPoint.y);
                    pixelColorPreview.setBackground(pixelColor);
                    pixelColorPreview.setToolTipText("RGB: " + pixelColor.getRed() + "," + pixelColor.getGreen() + "," + pixelColor.getBlue());
                }
            } catch (Exception ex) { pixelPoint = null; pixelColor = null; }
            pxToleranceSlider.setValue(Integer.parseInt(props.getProperty("pxTolerance", "5")));
            pxRateSlider.setValue(Integer.parseInt(props.getProperty("pxRate", "100")));
            pxConditionBox.setSelectedIndex(Integer.parseInt(props.getProperty("pxCondition", "0")));
            pxActionBox.setSelectedIndex(Integer.parseInt(props.getProperty("pxAction", "0")));
            pxSelectedKey = Integer.parseInt(props.getProperty("pxSelectCode", String.valueOf(KeyEvent.VK_SPACE)));
            pxKeyField.setText(KeyEvent.getKeyText(pxSelectedKey));
            
            // theme & lang ui load
            themeBox.setSelectedIndex(Boolean.parseBoolean(props.getProperty("themeDark", "true")) ? 0 : 1);
            fontSlider.setValue(Integer.parseInt(props.getProperty("fontSize", "14")));
            if(props.containsKey("fontColor")) customColorPreview.setBackground(new Color(Integer.parseInt(props.getProperty("fontColor"))));
            langBox.setSelectedIndex(Integer.parseInt(props.getProperty("langIndex", "0")));

        } catch (Exception e) {
            System.err.println("[AutoClicker] Config UI'ye uygulanamadi: " + e.getMessage());
        }
    }

    private JPanel buildMousePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(new JLabel(Lang.get("c_type")));
        mouseBtnBox = new JComboBox<>(new String[]{Lang.get("l_click"), Lang.get("r_click"), Lang.get("m_click"), Lang.get("d_click")});
        typePanel.add(mouseBtnBox);
        panel.add(typePanel);

        JPanel targetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        targetCoordBox = new JCheckBox(Lang.get("fix_loc"));
        JButton pickCoordBtn = new JButton(Lang.get("pick_loc"));
        coordLabel = new JLabel("(X: -, Y: -)");
        
        pickCoordBtn.addActionListener(e -> {
            pickCoordBtn.setText(Lang.get("wait_mid"));
            listeningForCoordParams = true;
        });

        targetPanel.add(targetCoordBox);
        targetPanel.add(pickCoordBtn);
        targetPanel.add(coordLabel);
        panel.add(targetPanel);

        JPanel humanizerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mouseHumanizerBox = new JCheckBox(Lang.get("anti_ban"));
        humanizerPanel.add(mouseHumanizerBox);
        humanizerPanel.add(createInfoButton("info_hum"));
        panel.add(humanizerPanel);

        JPanel cpsPanel = createCpsPanel(Lang.get("cps"), "10", 100, "info_cps", slider -> mouseCpsSlider = slider);
        panel.add(cpsPanel);

        return panel;
    }

    private JPanel buildKeyboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyPanel.add(new JLabel(Lang.get("key_trg")));
        keyTargetField = new JTextField("Space", 10);
        keyTargetField.setEditable(false);
        JButton assignKeyBtn = new JButton(Lang.get("set_k"));
        
        assignKeyBtn.addActionListener(e -> {
            assignKeyBtn.setText(Lang.get("press_k"));
            keyTargetField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent ev) {
                    selectedNativeKeyCode = ev.getKeyCode();
                    keyTargetField.setText(KeyEvent.getKeyText(selectedNativeKeyCode));
                    keyTargetField.removeKeyListener(this);
                    assignKeyBtn.setText(Lang.get("set_k"));
                }
            });
            keyTargetField.requestFocus();
        });

        keyPanel.add(keyTargetField);
        keyPanel.add(assignKeyBtn);
        panel.add(keyPanel);

        JPanel humanizerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyHumanizerBox = new JCheckBox(Lang.get("anti_ban"));
        humanizerPanel.add(keyHumanizerBox);
        humanizerPanel.add(createInfoButton("info_hum"));
        panel.add(humanizerPanel);

        JPanel cpsPanel = createCpsPanel(Lang.get("key_cps"), "5", 100, "info_cps", slider -> keyCpsSlider = slider);
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
        
        JButton addBtn = new JButton(Lang.get("add_act"));
        JButton remBtn = new JButton(Lang.get("del_act"));
        JButton clrBtn = new JButton(Lang.get("clr_act"));

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
        
        JPanel pAnti = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chainHumanizerBox = new JCheckBox(Lang.get("anti_ban"));
        pAnti.add(chainHumanizerBox); pAnti.add(createInfoButton("info_hum"));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(pAnti);

        panel.add(rightPanel, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildPixelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JPanel pTarget = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pTarget.setBorder(BorderFactory.createTitledBorder(Lang.get("px_1")));
        
        pixelCoordLbl = new JLabel("X: -, Y: -");
        JButton pickBtn = new JButton(Lang.get("pick_loc"));
        pickBtn.addActionListener(e -> {
            listeningForPixelCoord = true;
            pickBtn.setText(Lang.get("wait_mid"));
            pickBtn.setForeground(Color.RED);
        });

        pixelColorPreview = new JPanel();
        pixelColorPreview.setPreferredSize(new Dimension(30, 30));
        pixelColorPreview.setBackground(Color.DARK_GRAY);
        pixelColorPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        pTarget.add(pickBtn);
        pTarget.add(pixelCoordLbl);
        pTarget.add(Box.createRigidArea(new Dimension(20, 0)));
        pTarget.add(new JLabel(Lang.get("px_clr")));
        pTarget.add(pixelColorPreview);
        panel.add(pTarget);

        JPanel pCond = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pCond.setBorder(BorderFactory.createTitledBorder(Lang.get("px_2")));
        pxConditionBox = new JComboBox<>(new String[]{Lang.get("px_cond1"), Lang.get("px_cond2")});
        
        JPanel pTol = createCpsPanel(Lang.get("px_tol"), "5", 100, "info_px", slider -> pxToleranceSlider = slider);
        JPanel innerCond = new JPanel(new FlowLayout(FlowLayout.LEFT));
        innerCond.add(pxConditionBox); innerCond.add(createInfoButton("info_px_cond"));
        pCond.add(innerCond);
        panel.add(pCond);
        panel.add(pTol);

        JPanel pAct = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pAct.setBorder(BorderFactory.createTitledBorder(Lang.get("px_3")));
        pxActionBox = new JComboBox<>(new String[]{ Lang.get("l_click"), Lang.get("r_click"), Lang.get("px_act3"), Lang.get("px_act4") });
        
        pxKeyField = new JTextField("Space", 6);
        pxKeyField.setEditable(false);
        JButton assignKeyBtn = new JButton(Lang.get("set_k"));
        assignKeyBtn.addActionListener(e -> {
            assignKeyBtn.setText(Lang.get("press_k"));
            pxKeyField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ev) {
                    pxSelectedKey = ev.getKeyCode();
                    pxKeyField.setText(KeyEvent.getKeyText(pxSelectedKey));
                    pxKeyField.removeKeyListener(this);
                    assignKeyBtn.setText(Lang.get("set_k"));
                }
            });
            pxKeyField.requestFocus();
        });

        pxActionBox.addActionListener(e -> {
            boolean isKey = pxActionBox.getSelectedIndex() == 2;
            pxKeyField.setEnabled(isKey);
            assignKeyBtn.setEnabled(isKey);
        });
        
        pAct.add(pxActionBox);
        pAct.add(Box.createRigidArea(new Dimension(10, 0)));
        pAct.add(pxKeyField);
        pAct.add(assignKeyBtn);
        pxKeyField.setEnabled(false); assignKeyBtn.setEnabled(false); 
        
        panel.add(pAct);

        JPanel pRate = createCpsPanel(Lang.get("px_rate"), "100", 2000, "info_px_rate", slider -> {
            pxRateSlider = slider;
            pxRateSlider.setMinimum(10);
        });
        panel.add(pRate);

        return panel;
    }

    private void openAddActionDialog() {
        JDialog dialog = new JDialog(this, Lang.get("add_act"), true);
        dialog.setSize(400, 260);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // Dialog kapaninca bekleyen konum-secimini iptal et; kapali dialog'a yazimi onle
        dialog.addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                listeningForMacroCoord = false;
                macroCoordLblInfo = null;
            }
        });

        JPanel configPanel = new JPanel(new CardLayout());
        configPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JComboBox<String> typeBox = new JComboBox<>(new String[]{Lang.get("tnt_mouse"), Lang.get("tnt_key"), Lang.get("tnt_move"), Lang.get("ms_delay")});
        
        JComboBox<String> mouseBox = new JComboBox<>(new String[]{Lang.get("l_click"), Lang.get("r_click"), Lang.get("m_click"), Lang.get("d_click")});
        JPanel p0 = new JPanel(); p0.add(new JLabel(Lang.get("c_type"))); p0.add(mouseBox);
        
        JTextField keyField = new JTextField("Space", 10); keyField.setEditable(false);
        JButton keyBtn = new JButton(Lang.get("set_k"));
        final int[] selectedActKey = {KeyEvent.VK_SPACE};
        keyBtn.addActionListener(e -> {
            keyBtn.setText(Lang.get("press_k"));
            keyField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ev) {
                    selectedActKey[0] = ev.getKeyCode();
                    keyField.setText(KeyEvent.getKeyText(selectedActKey[0]));
                    keyField.removeKeyListener(this);
                    keyBtn.setText(Lang.get("set_k"));
                }
            });
            keyField.requestFocus();
        });
        JPanel p1 = new JPanel(); p1.add(new JLabel(Lang.get("key_trg"))); p1.add(keyField); p1.add(keyBtn);
        
        macroCoordLblInfo = new JLabel("X: 0 Y: 0");
        macroTempPt = new Point(0,0);
        JButton pickBtn = new JButton(Lang.get("pick_loc"));
        pickBtn.addActionListener(e -> {
             listeningForMacroCoord = true;
             pickBtn.setText(Lang.get("wait_mid"));
             pickBtn.setForeground(Color.RED);
        });
        JPanel p2 = new JPanel(); p2.add(pickBtn); p2.add(macroCoordLblInfo);
        
        JTextField delayField = new JTextField("500", 6);
        JPanel p3 = new JPanel(); p3.add(new JLabel(Lang.get("ms_delay"))); p3.add(delayField);
        
        configPanel.add(p0, "0"); configPanel.add(p1, "1"); configPanel.add(p2, "2"); configPanel.add(p3, "3");
        
        typeBox.addActionListener(e -> ((CardLayout)configPanel.getLayout()).show(configPanel, String.valueOf(typeBox.getSelectedIndex())));
        
        JButton addBtn = new JButton(Lang.get("list_add"));
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
                try { chainModel.addElement(new MacroAction(ActionType.DELAY, Integer.parseInt(delayField.getText()), 0)); } catch(Exception ex) {}
            }
            dialog.dispose();
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        top.add(new JLabel(Lang.get("act_typ"))); top.add(typeBox);
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

        // Lang Select
        JPanel langPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        langPanel.setBorder(BorderFactory.createTitledBorder(Lang.get("lang_title")));
        langBox = new JComboBox<>(new String[]{"(TR) Türkçe", "(EN) English", "(DE) Deutsch", "(FR) Français", "(IT) Italiano", "(RU) Русский"});
        JButton applyLangBtn = new JButton("OK");
        applyLangBtn.addActionListener(e -> {
            Lang.L = langBox.getSelectedIndex();
            rebuildUI();
        });
        langPanel.add(langBox); langPanel.add(applyLangBtn);
        panel.add(langPanel);

        JPanel hotkeyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hotkeyPanel.add(new JLabel(Lang.get("set_hk")));
        hotkeyBtn = new JButton("F6");
        hotkeyBtn.setPreferredSize(new Dimension(150, 35));
        hotkeyBtn.addActionListener(e -> {
            listeningForHotkey = true;
            hotkeyBtn.setText(Lang.get("press_k"));
            hotkeyBtn.setForeground(Color.RED);
        });
        hotkeyPanel.add(hotkeyBtn);
        panel.add(hotkeyPanel);
        
        JPanel limitPanel = new JPanel();
        limitPanel.setLayout(new BoxLayout(limitPanel, BoxLayout.Y_AXIS));
        JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pTitle.add(new JLabel("<html><b>" + Lang.get("lim_title") + "</b></html>"));
        pTitle.add(createInfoButton("info_lim"));
        limitPanel.add(pTitle);
        
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        useLimitBox = new JCheckBox(Lang.get("lim_use"));
        p1.add(useLimitBox);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel(Lang.get("lim_after")));
        limitValField = new JTextField("60", 4);
        p2.add(limitValField);
        limitTypeBox = new JComboBox<>(new String[]{Lang.get("lim_min"), Lang.get("lim_iter")});
        p2.add(limitTypeBox);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        limitActionBox = new JComboBox<>(new String[]{Lang.get("lim_stop"), Lang.get("lim_shut")});
        p3.add(limitActionBox);

        limitPanel.add(p1); limitPanel.add(p2); limitPanel.add(p3);
        panel.add(limitPanel);

        // UI STYLE PANEL V5.1
        JPanel stylePanel = new JPanel();
        stylePanel.setLayout(new BoxLayout(stylePanel, BoxLayout.Y_AXIS));
        stylePanel.setBorder(BorderFactory.createTitledBorder(Lang.get("style")));

        JPanel pThm = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pThm.add(new JLabel(Lang.get("theme")));
        themeBox = new JComboBox<>(new String[]{Lang.get("theme_d"), Lang.get("theme_l")});
        pThm.add(themeBox);
        stylePanel.add(pThm);

        JPanel pFnt = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pFnt.add(new JLabel(Lang.get("txt_size")));
        fontSlider = new JSlider(10, 24, 14);
        fontSlider.setPaintTicks(true); fontSlider.setMajorTickSpacing(2); fontSlider.setPaintLabels(true);
        pFnt.add(fontSlider);
        stylePanel.add(pFnt);

        JPanel pCol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pCol.add(new JLabel(Lang.get("txt_col")));
        customColorPreview = new JPanel();
        customColorPreview.setPreferredSize(new Dimension(30,30));
        customColorPreview.setBackground(Color.WHITE); 
        customColorPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JButton customColorBtn = new JButton(Lang.get("col_pick"));
        customColorBtn.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(this, Lang.get("col_pick"), customColorPreview.getBackground());
            if(chosen != null) customColorPreview.setBackground(chosen);
        });
        
        JButton btnDefaultColor = new JButton(Lang.get("reset"));
        btnDefaultColor.addActionListener(e -> customColorPreview.setBackground(Color.WHITE));

        pCol.add(customColorPreview); pCol.add(customColorBtn); pCol.add(btnDefaultColor);
        stylePanel.add(pCol);

        JPanel pApply = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton applyStyleBtn = new JButton(Lang.get("apply_s"));
        applyStyleBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        applyStyleBtn.addActionListener(e -> {
            // saveConfig + rebuildUI zaten props/component'lerden okuyor; ara degiskenler gereksizdi
            saveConfig();
            rebuildUI();
        });
        pApply.add(applyStyleBtn);
        stylePanel.add(pApply);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(stylePanel);

        return panel;
    }

    private JPanel createCpsPanel(String title, String defaultVal, int max, String infoKey, java.util.function.Consumer<JSlider> setSlider) {
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
                // Bos alan notr; gecersiz/araliga sigmayan deger kirmizi cerceve
                field.putClientProperty("JComponent.outline", (ok || field.getText().trim().isEmpty()) ? null : "error");
                field.repaint();
            }
        });

        setSlider.accept(slider);

        inputPanel.add(slider);
        inputPanel.add(field);
        if (infoKey != null) {
            inputPanel.add(createInfoButton(infoKey));
        }
        panel.add(inputPanel);
        return panel;
    }

    private void initJNativeHook() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.unregisterNativeHook(); 
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            GlobalScreen.addNativeMouseListener(this);
        } catch (NativeHookException e) {}
    }

    // Limit ayarlarinin worker thread'e gecirilecek anlik kopyasi (EDT-disi Swing erisimini onler)
    static class LimitConfig {
        final boolean enabled; final int type; final int value; final int action;
        LimitConfig(boolean enabled, int type, int value, int action) {
            this.enabled = enabled; this.type = type; this.value = value; this.action = action;
        }
    }

    // EDT'de cagrilmali: makro baslamadan once limit ayarlarini snapshot'lar
    private LimitConfig snapshotLimits() {
        int val;
        try { val = Integer.parseInt(limitValField.getText()); } catch (Exception e) { val = 0; }
        return new LimitConfig(useLimitBox.isSelected(), limitTypeBox.getSelectedIndex(), val, limitActionBox.getSelectedIndex());
    }

    private boolean checkLimits(LimitConfig cfg, long startTime, int iterationCount) {
        if (cfg == null || !cfg.enabled || !isRunning) return true;

        if (cfg.type == 0) {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= (cfg.value * 60000L)) {
                executeLimitAction(cfg.action);
                return false;
            }
        } else if (cfg.type == 1) {
            // iteration kontrolden once 1-artiriliyor; tam cfg.value eylem icin > kullan (>= olunca N-1 olurdu)
            if (iterationCount > cfg.value) {
                executeLimitAction(cfg.action);
                return false;
            }
        }
        return true;
    }

    private void executeLimitAction(int action) {
        isRunning = false;
        if(action == 1) { // bilgisayari kapat
            ShutdownCommand.Result sc = ShutdownCommand.forOs(System.getProperty("os.name"));
            SwingUtilities.invokeLater(() -> executeShutdownWithCancel(sc));
        } else {
            SwingUtilities.invokeLater(() -> Toolkit.getDefaultToolkit().beep());
        }
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(Lang.get("st_lim"));
            statusLabel.setForeground(new Color(255, 90, 90));
        });
    }

    // EDT'de: OS gecikmeli kapatmayi baslatir, gecikme penceresinde iptal sunar.
    // AFK ise (kimse iptal etmezse) bilgisayar kapanir; kullanici varsa iptal edebilir.
    private void executeShutdownWithCancel(ShutdownCommand.Result sc) {
        if (sc == null || !sc.isSupported()) {
            JOptionPane.showMessageDialog(this, Lang.get("shut_unsup") + System.getProperty("os.name"));
            return;
        }
        try {
            Runtime.getRuntime().exec(sc.command); // OS gecikmeli kapatma baslar
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Lang.get("shut_fail") + e.getMessage());
            return;
        }
        int sel = JOptionPane.showOptionDialog(this,
            String.format(Lang.get("shut_pending"), sc.delayLabel),
            Lang.get("shut_title"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
            null, new Object[]{ Lang.get("shut_cancel_btn") }, Lang.get("shut_cancel_btn"));
        if (sel == 0 && sc.cancelCommand != null) {
            try {
                Runtime.getRuntime().exec(sc.cancelCommand);
                JOptionPane.showMessageDialog(this, Lang.get("shut_cancelled"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, Lang.get("shut_fail") + e.getMessage());
            }
        }
    }

    private void stopWorker() {
        isRunning = false;
        Thread t = workerThread;
        if (t != null && t.isAlive()) {
            t.interrupt(); // bekleyen Thread.sleep'i uyandir
            try { t.join(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        workerThread = null;
    }

    // Limitor aciksa deger pozitif tam sayi olmali; degilse alani kirmizi cerceveler ve false doner
    private boolean validateLimitInput() {
        if (!useLimitBox.isSelected()) {
            limitValField.putClientProperty("JComponent.outline", null);
            limitValField.repaint();
            return true;
        }
        int v;
        try { v = Integer.parseInt(limitValField.getText().trim()); } catch (Exception e) { v = -1; }
        boolean ok = v > 0;
        limitValField.putClientProperty("JComponent.outline", ok ? null : "error");
        limitValField.repaint();
        if (!ok) Toolkit.getDefaultToolkit().beep();
        return ok;
    }

    // ESC panik tusu: kosulsuz durdurma + sesli uyari (EDT'de calisir)
    private void stopFromPanic() {
        if (!isRunning) return;
        stopWorker();
        statusLabel.setText(Lang.get("st_idle"));
        statusLabel.setForeground(new Color(255, 90, 90));
        Toolkit.getDefaultToolkit().beep();
    }

    private void toggle() {
        if (isRunning) {
            stopWorker();
            statusLabel.setText(Lang.get("st_idle"));
            statusLabel.setForeground(new Color(255, 90, 90));
        } else {
            // Eski worker hala canliysa once tamamen durdur (cift makro yarisini onler)
            stopWorker();

            // Limitor aciksa gecersiz/0 deger ani durdurma/kapatma yapmasin
            if (!validateLimitInput()) {
                statusLabel.setText(Lang.get("lim_invalid"));
                statusLabel.setForeground(new Color(255, 165, 0));
                return;
            }

            JTabbedPane tabs = (JTabbedPane) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER);
            int selectedTab = tabs.getSelectedIndex();

            boolean started;
            if (selectedTab == 0) started = startMouseMacro();
            else if (selectedTab == 1) started = startKeyMacro();
            else if (selectedTab == 2) started = startChainMacro();
            else if (selectedTab == 3) started = startPixelMacro();
            else return;

            if (started) {
                statusLabel.setText(Lang.get("st_run"));
                statusLabel.setForeground(new Color(90, 255, 90));
            } else {
                statusLabel.setText(Lang.get("st_idle"));
                statusLabel.setForeground(new Color(255, 90, 90));
            }
        }
    }

    private boolean startMouseMacro() {
        int cps = mouseCpsSlider.getValue();
        int baseDelay = 1000 / Math.max(cps, 1);
        int mode = mouseBtnBox.getSelectedIndex(); 
        boolean useHumanizer = mouseHumanizerBox.isSelected();
        boolean useCoord = targetCoordBox.isSelected() && targetPoint != null;
        final LimitConfig limits = snapshotLimits();

        long startTime = System.currentTimeMillis();

        isRunning = true;
        workerThread = new Thread(() -> {
            int iteration = 0;
            while (isRunning) {
                iteration++;
                if (!checkLimits(limits, startTime, iteration)) break;

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
        return true;
    }

    private boolean startKeyMacro() {
        int cps = keyCpsSlider.getValue();
        int baseDelay = Math.max(1, 1000 / cps);
        boolean useHumanizer = keyHumanizerBox.isSelected();
        final int targetKey = selectedNativeKeyCode;
        final LimitConfig limits = snapshotLimits();

        long startTime = System.currentTimeMillis();

        isRunning = true;
        workerThread = new Thread(() -> {
            int iteration = 0;
            while (isRunning) {
                iteration++;
                if (!checkLimits(limits, startTime, iteration)) break;

                try {
                    // press->release araligini kisa tut ve CPS butcesinden dus ki gercek hiz hedefe yapissin
                    int releaseGap = Math.min(baseDelay / 2, 5 + random.nextInt(10));
                    if (releaseGap < 2) releaseGap = 2;
                    robot.keyPress(targetKey);
                    robot.delay(releaseGap);
                    robot.keyRelease(targetKey);

                    int remaining = Math.max(1, baseDelay - releaseGap);
                    int sleepTime = getHumanizedDelay(remaining, useHumanizer);
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });
        workerThread.start();
        return true;
    }

    private boolean startChainMacro() {
        if (chainModel.isEmpty()) {
            SwingUtilities.invokeLater(() -> Toolkit.getDefaultToolkit().beep());
            return false;
        }

        boolean useHumanizer = chainHumanizerBox.isSelected();
        final LimitConfig limits = snapshotLimits();
        // Worker thread'in DefaultListModel'a EDT-disi erismesini onlemek icin diziye snapshot al
        final MacroAction[] actions = new MacroAction[chainModel.getSize()];
        for (int i = 0; i < actions.length; i++) actions[i] = chainModel.get(i);
        long startTime = System.currentTimeMillis();

        isRunning = true;
        workerThread = new Thread(() -> {
            int iteration = 0;
            while (isRunning) {
                iteration++;
                if (!checkLimits(limits, startTime, iteration)) break;

                for (int i = 0; i < actions.length && isRunning; i++) {
                    MacroAction action = actions[i];
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
        return true;
    }

    private boolean startPixelMacro() {
        if (pixelPoint == null || pixelColor == null) {
            SwingUtilities.invokeLater(() -> Toolkit.getDefaultToolkit().beep());
            return false;
        }

        int scanRate = pxRateSlider.getValue();
        int tolerancePercent = pxToleranceSlider.getValue();
        int condition = pxConditionBox.getSelectedIndex();
        int action = pxActionBox.getSelectedIndex();
        int targetKey = pxSelectedKey;
        final LimitConfig limits = snapshotLimits();

        long startTime = System.currentTimeMillis();
        isRunning = true;

        workerThread = new Thread(() -> {
            int iteration = 0;
            while(isRunning) {
                iteration++;
                if (!checkLimits(limits, startTime, iteration)) break;
                
                Color current = robot.getPixelColor(pixelPoint.x, pixelPoint.y);
                boolean isMatch = colorDistance(pixelColor, current) <= (tolerancePercent * 4.4167);
                
                boolean shouldTrigger = false;
                if(condition == 0 && isMatch) shouldTrigger = true; 
                else if(condition == 1 && !isMatch) shouldTrigger = true;
                
                if (shouldTrigger) {
                     if(action == 0 || action == 1) { 
                         robot.mouseMove(pixelPoint.x, pixelPoint.y);
                         int mask = (action == 0) ? InputEvent.BUTTON1_DOWN_MASK : InputEvent.BUTTON3_DOWN_MASK;
                         doMouseClick(mask);
                     } else if(action == 2) { 
                         robot.keyPress(targetKey);
                         robot.delay(20 + random.nextInt(30));
                         robot.keyRelease(targetKey);
                     } else if(action == 3) { 
                         Toolkit.getDefaultToolkit().beep();
                     }
                     try { Thread.sleep(1000); } catch(Exception e){}
                }
                try { Thread.sleep(scanRate); } catch(Exception e) {}
            }
        });
        workerThread.start();
        return true;
    }

    private double colorDistance(Color c1, Color c2) {
        int dr = c1.getRed() - c2.getRed();
        int dg = c1.getGreen() - c2.getGreen();
        int db = c1.getBlue() - c2.getBlue();
        return Math.sqrt(dr*dr + dg*dg + db*db);
    }

    private void doMouseClick(int mask) {
        robot.mousePress(mask);
        robot.mouseRelease(mask);
    }

    private int getHumanizedDelay(int baseDelay, boolean useHumanizer) {
        return Humanizer.humanizedDelay(baseDelay, useHumanizer, random);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent ev) {
        // Her zaman acik ACIL DURDURMA (panik) tusu: ESC. Yapilandirilabilir hotkey'den bagimsizdir
        // ve degistirilemez; makro cok hizliyken garantili cikis saglar.
        if (ev.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            if (isRunning) SwingUtilities.invokeLater(this::stopFromPanic);
            return; // ESC asla hotkey olarak atanmaz / toggle tetiklemez
        }

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

        if (ev.getKeyCode() == triggerKey && !listeningForCoordParams && !listeningForMacroCoord && !listeningForPixelCoord) {
            SwingUtilities.invokeLater(this::toggle);
        }
    }
    
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        if (listeningForCoordParams && e.getButton() == NativeMouseEvent.BUTTON3) { 
            listeningForCoordParams = false;
            Point p = MouseInfo.getPointerInfo().getLocation();
            targetPoint = new Point(p.x, p.y);
            SwingUtilities.invokeLater(() -> {
                coordLabel.setText("(X: " + targetPoint.x + ", Y: " + targetPoint.y + ")");
                JButton btn = (JButton)((JPanel)targetCoordBox.getParent()).getComponent(1);
                btn.setText(Lang.get("pick_loc"));
                targetCoordBox.setSelected(true);
            });
        }
        if (listeningForMacroCoord && e.getButton() == NativeMouseEvent.BUTTON3) {
            listeningForMacroCoord = false;
            Point p = MouseInfo.getPointerInfo().getLocation();
            macroTempPt = new Point(p.x, p.y);
            SwingUtilities.invokeLater(() -> {
                if(macroCoordLblInfo != null) {
                    macroCoordLblInfo.setText("X: " + macroTempPt.x + " Y: " + macroTempPt.y);
                    macroCoordLblInfo.getParent().getComponent(0).setForeground(Color.GREEN);
                    ((JButton)macroCoordLblInfo.getParent().getComponent(0)).setText("✔");
                }
            });
        }
        if (listeningForPixelCoord && e.getButton() == NativeMouseEvent.BUTTON3) {
            listeningForPixelCoord = false;
            Point p = MouseInfo.getPointerInfo().getLocation();
            int cx = p.x, cy = p.y;
            Color c = robot.getPixelColor(cx, cy);
            pixelPoint = new Point(cx, cy);
            pixelColor = c;
            SwingUtilities.invokeLater(() -> {
                pixelCoordLbl.setText("X: " + cx + " Y: " + cy);
                pixelColorPreview.setBackground(c);
                pixelColorPreview.setToolTipText("RGB: " + c.getRed() + "," + c.getGreen() + "," + c.getBlue());
                
                JButton btn = (JButton)((JPanel)pixelCoordLbl.getParent()).getComponent(0);
                btn.setText("✔");
                btn.setForeground(UIManager.getColor("Button.foreground"));
            });
        }
    }
    @Override public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {}
    @Override public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {}

    /** Surum bilgisini JAR manifest'inden (pom version) okur; IDE/class'tan calistirinca 'dev'. */
    private static String appVersion() {
        String v = AutoClicker.class.getPackage().getImplementationVersion();
        return (v != null) ? v : "dev";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoClicker().setVisible(true));
    }
}
