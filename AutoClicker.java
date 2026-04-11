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
            
            d.put("t_mouse", new String[]{"[ Fare ]", "[ Mouse ]", "[ Maus ]", "[ Souris ]", "[ Mouse ]", "[ Мышь ]"});
            d.put("t_key", new String[]{"[ Klavye ]", "[ Keyboard ]", "[ Tastatur ]", "[ Clavier ]", "[ Tastiera ]", "[ Клавиатура ]"});
            d.put("t_chain", new String[]{"[ Zincir ]", "[ Chain ]", "[ Kette ]", "[ Chaîne ]", "[ Catena ]", "[ Цепь ]"});
            d.put("t_px", new String[]{"[ Piksel ]", "[ Pixel ]", "[ Pixel ]", "[ Pixel ]", "[ Pixel ]", "[ Пиксель ]"});
            d.put("t_set", new String[]{"[ Ayarlar ]", "[ Settings ]", "[ Einstellungen ]", "[ Paramètres ]", "[ Impostazioni ]", "[ Настройки ]"});
            
            d.put("c_type", new String[]{"Tiklama Tipi: ", "Click Type: ", "Klick-Typ: ", "Type de clic: ", "Tipo di clic: ", "Тип клика: "});
            d.put("l_click", new String[]{"Sol Tik", "Left Click", "Linksklick", "Clic Gauche", "Clic Sinistro", "Левый Клик"});
            d.put("r_click", new String[]{"Sag Tik", "Right Click", "Rechtsklick", "Clic Droit", "Clic Destro", "Правый Клик"});
            d.put("m_click", new String[]{"Orta Tik", "Mid Click", "Mittelklick", "Clic Milieu", "Clic Centrale", "Средний Клик"});
            d.put("d_click", new String[]{"Cift Sol Tik", "Double Click", "Doppelklick", "Double Clic", "Doppio Clic", "Двойной Клик"});
            
            d.put("fix_loc", new String[]{"Sabit Konuma Tikla", "Click Fixed Loc", "Feste Position", "Bouton Fixe", "Pos. Fissa", "Фикс. Место"});
            d.put("pick_loc", new String[]{"Konum Sec", "Pick Loc", "Wählen", "Choisir", "Seleziona", "Выбрать"});
            d.put("wait_mid", new String[]{"Bekliyor (Orta Tik)", "Waiting (Mid Click)", "Wartet (Mittelklick)", "Attente (Milieu)", "Attesa (Centrale)", "Ожидание (Ср. клик)"});
            
            d.put("anti_ban", new String[]{"Insan Modu (Rastgelemsi Gecikme)", "Humanizer (Random Delay)", "Menschenmodus (Zufall)", "Mode Humain (Délais Aléat.)", "Modalità Umana (Ritardo)", "Анти-Бан"});
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
            d.put("info_px", new String[]{"Renk Toleransi: Ufak golge farkliliklarinin renk algisini bozmasini engeller.", "Color Tolerance: Prevents minor in-game shading/lighting shifts from ruining detection.", "Farbtoleranz: Verhindert kleine Schattenfehler.", "Tolérance: Empêche les petits changements de lumière...", "Tolleranza: Evita che l'illuminazione rompa l'algoritmo.", "Допуск: Игнорирует изменения освещения."});
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

    // Mouse
    private JComboBox<String> mouseBtnBox;
    private JSlider mouseCpsSlider;
    private JTextField mouseCpsField;
    private JCheckBox mouseHumanizerBox;
    private JCheckBox targetCoordBox;
    private JLabel coordLabel;
    private Point targetPoint = null;
    private boolean listeningForCoordParams = false;

    // Keyboard
    private JTextField keyTargetField;
    private JSlider keyCpsSlider;
    private JTextField keyCpsField;
    private JCheckBox keyHumanizerBox;
    private int selectedNativeKeyCode = KeyEvent.VK_SPACE;

    // Chain Macro
    private DefaultListModel<MacroAction> chainModel = new DefaultListModel<>();
    private JList<MacroAction> chainList;
    private JCheckBox chainHumanizerBox;
    private boolean listeningForMacroCoord = false;
    private JLabel macroCoordLblInfo;
    private Point macroTempPt;

    // Pixel Trigger
    private boolean listeningForPixelCoord = false;
    private Point pixelPoint = null;
    private Color pixelColor = null;
    private JLabel pixelCoordLbl;
    private JPanel pixelColorPreview;
    private JComboBox<String> pxConditionBox;
    private JComboBox<String> pxActionBox;
    private JSlider pxToleranceSlider;
    private JTextField pxToleranceField;
    private JSlider pxRateSlider;
    private JTextField pxRateField;
    private JTextField pxKeyField;
    private int pxSelectedKey = KeyEvent.VK_SPACE;

    // Style & Lang
    private JComboBox<String> themeBox;
    private JSlider fontSlider;
    private JPanel customColorPreview;
    private JComboBox<String> langBox;

    public AutoClicker() {
        try { robot = new Robot(); } catch (Exception e) {}
        loadConfig();
        Lang.L = Integer.parseInt(props.getProperty("langIndex", "0"));
        applyInitialTheme();

        setTitle("AutoClicker Ultimate v5.3");
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

    private JButton createInfoButton(String tooltipKey) {
        JButton btn = new JButton("[ ? ]");
        btn.setMargin(new Insets(0,0,0,0));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("<html><p width=\"250\">" + Lang.get(tooltipKey) + "</p></html>");
        btn.addActionListener(e -> JOptionPane.showMessageDialog(this, Lang.get(tooltipKey), "Bilgi / Info", JOptionPane.INFORMATION_MESSAGE));
        return btn;
    }

    private void applyColorsRecursively(Component c, Color color) {
        if (c == null) return;
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
                props.remove("fontColor"); 
                props.setProperty("fontColor", String.valueOf(customColorPreview.getBackground().getRGB()));
            }
            
            props.setProperty("langIndex", String.valueOf(langBox.getSelectedIndex()));

            props.store(fos, "AutoClicker Configuration");
        } catch (Exception e) {}
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
        tabbedPane.addTab(Lang.get("t_mouse"), buildMousePanel());
        tabbedPane.addTab(Lang.get("t_key"), buildKeyboardPanel());
        tabbedPane.addTab(Lang.get("t_chain"), buildChainPanel());
        tabbedPane.addTab(Lang.get("t_px"), buildPixelPanel());
        JScrollPane setScroll = new JScrollPane(buildSettingsPanel());
        setScroll.setBorder(null);
        tabbedPane.addTab(Lang.get("t_set"), setScroll);

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
            
            keyCpsSlider.setValue(Integer.parseInt(props.getProperty("keyCps", "10")));
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

            // px ui load
            if(props.containsKey("pxX")) {
                pixelPoint = new Point(Integer.parseInt(props.getProperty("pxX")), Integer.parseInt(props.getProperty("pxY")));
                pixelColor = new Color(Integer.parseInt(props.getProperty("pxR")), Integer.parseInt(props.getProperty("pxG")), Integer.parseInt(props.getProperty("pxB")));
                pixelCoordLbl.setText("X: " + pixelPoint.x + " Y: " + pixelPoint.y);
                pixelColorPreview.setBackground(pixelColor);
                pixelColorPreview.setToolTipText("RGB: " + pixelColor.getRed() + "," + pixelColor.getGreen() + "," + pixelColor.getBlue());
            }
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

        } catch (Exception e) {}
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

        JPanel cpsPanel = createCpsPanel(Lang.get("cps"), "10", 100, slider -> mouseCpsSlider = slider, field -> mouseCpsField = field);
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

        JPanel cpsPanel = createCpsPanel(Lang.get("key_cps"), "5", 100, slider -> keyCpsSlider = slider, field -> keyCpsField = field);
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
        
        JPanel pTol = createCpsPanel(Lang.get("px_tol"), "5", 100, slider -> pxToleranceSlider = slider, field -> pxToleranceField = field);
        JPanel innerCond = new JPanel(new FlowLayout(FlowLayout.LEFT));
        innerCond.add(pxConditionBox); innerCond.add(createInfoButton("info_px"));
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

        JPanel pRate = createCpsPanel(Lang.get("px_rate"), "100", 2000, slider -> {
            pxRateSlider = slider;
            pxRateSlider.setMinimum(10);
        }, field -> {
            pxRateField = field;
        });
        panel.add(pRate);

        return panel;
    }

    private void openAddActionDialog() {
        JDialog dialog = new JDialog(this, Lang.get("add_act"), true);
        dialog.setSize(400, 260);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

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
        
        JButton btnDefaultColor = new JButton("RESET");
        btnDefaultColor.addActionListener(e -> customColorPreview.setBackground(Color.WHITE));

        pCol.add(customColorPreview); pCol.add(customColorBtn); pCol.add(btnDefaultColor);
        stylePanel.add(pCol);

        JPanel pApply = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton applyStyleBtn = new JButton(Lang.get("apply_s"));
        applyStyleBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        applyStyleBtn.addActionListener(e -> {
            boolean isDark = themeBox.getSelectedIndex() == 0;
            int fSize = fontSlider.getValue();
            Color fColor = null;
            if(!customColorPreview.getBackground().equals(Color.WHITE)) {
                fColor = customColorPreview.getBackground(); 
            }
            applyThemeAndFont(isDark, fSize, fColor);
            saveConfig(); 
        });
        pApply.add(applyStyleBtn);
        stylePanel.add(pApply);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(stylePanel);

        return panel;
    }

    private JPanel createCpsPanel(String title, String defaultVal, int max, java.util.function.Consumer<JSlider> setSlider, java.util.function.Consumer<JTextField> setField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JSlider slider = new JSlider(1, max, Integer.parseInt(defaultVal));
        JTextField field = new JTextField(defaultVal, 5);
        
        slider.addChangeListener(e -> field.setText(String.valueOf(slider.getValue())));
        field.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    int val = Integer.parseInt(field.getText());
                    if (val >= 1 && val <= max) slider.setValue(val);
                } catch (Exception ex){}
            }
        });

        setSlider.accept(slider);
        setField.accept(field);
        
        inputPanel.add(slider);
        inputPanel.add(field);
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

    private boolean checkLimits(long startTime, int iterationCount) {
        if(!useLimitBox.isSelected() || !isRunning) return true; 
        
        try {
            int limitVal = Integer.parseInt(limitValField.getText());
            int type = limitTypeBox.getSelectedIndex();
            
            if (type == 0) { 
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed >= (limitVal * 60000L)) {
                    executeLimitAction();
                    return false;
                }
            } else if (type == 1) { 
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
        if(action == 1) { 
            try {
                Runtime.getRuntime().exec("shutdown -s -t 15");
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "SHUTDOWN IN 15 SECONDS!"));
            } catch(Exception e) {}
        } else {
            SwingUtilities.invokeLater(() -> Toolkit.getDefaultToolkit().beep());
        }
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(Lang.get("st_lim"));
            statusLabel.setForeground(new Color(255, 90, 90));
        });
    }

    private void toggle() {
        if (isRunning) {
            isRunning = false;
            statusLabel.setText(Lang.get("st_idle"));
            statusLabel.setForeground(new Color(255, 90, 90));
        } else {
            JTabbedPane tabs = (JTabbedPane) ((BorderLayout) getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER);
            int selectedTab = tabs.getSelectedIndex();
            
            if (selectedTab == 0) startMouseMacro();
            else if (selectedTab == 1) startKeyMacro();
            else if (selectedTab == 2) startChainMacro();
            else if (selectedTab == 3) startPixelMacro();
            else return;

            statusLabel.setText(Lang.get("st_run"));
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

    private void startPixelMacro() {
        if (pixelPoint == null || pixelColor == null) {
            isRunning = false;
            return;
        }

        int scanRate = pxRateSlider.getValue();
        int tolerancePercent = pxToleranceSlider.getValue();
        int condition = pxConditionBox.getSelectedIndex();
        int action = pxActionBox.getSelectedIndex();
        int targetKey = pxSelectedKey;

        long startTime = System.currentTimeMillis();
        isRunning = true;
        
        workerThread = new Thread(() -> {
            int iteration = 0;
            while(isRunning) {
                iteration++;
                if (!checkLimits(startTime, iteration)) break;
                
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
        if (!useHumanizer || baseDelay < 5) return Math.max(1, baseDelay);
        int variance = (int)(baseDelay * 0.15); 
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
                    ((JButton)macroCoordLblInfo.getParent().getComponent(0)).setText("V");
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
                btn.setText("V");
                btn.setForeground(UIManager.getColor("Button.foreground"));
            });
        }
    }
    @Override public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {}
    @Override public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoClicker().setVisible(true));
    }
}
