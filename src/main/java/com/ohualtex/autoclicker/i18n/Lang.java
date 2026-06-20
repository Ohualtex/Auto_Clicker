package com.ohualtex.autoclicker.i18n;

public final class Lang {
    public static int L = 0; // 0=TR, 1=EN, 2=DE, 3=FR, 4=IT, 5=RU
    static java.util.Map<String, String[]> d = new java.util.HashMap<>();
    static {
        d.put("st_idle", new String[]{"DURUM: BEKLIYOR", "STATUS: IDLE", "STATUS: BEREIT", "STATUT: EN ATTENTE", "STATO: IN ATTESA", "СТАТУС: ОЖИДАНИЕ"});
        d.put("st_run", new String[]{"DURUM: CALISIYOR", "STATUS: RUNNING", "STATUS: LÄUFT", "STATUT: EN COURS", "STATO: IN ESECUZIONE", "СТАТУС: РАБОТАЕТ"});
        d.put("st_lim", new String[]{"DURUM: LIMIT YENDI", "STATUS: LIMIT HIT", "STATUS: LIMIT ERREICHT", "STATUT: LIMITE", "STATO: LIMITE RAGGIUNTO", "СТАТУС: ЛИМИТ"});
        d.put("hook_fail", new String[]{"DURUM: KISAYOL DINLENEMIYOR", "STATUS: HOTKEY UNAVAILABLE", "STATUS: HOTKEY NICHT VERFUGBAR", "STATUT: RACCOURCI INDISPONIBLE", "STATO: HOTKEY NON DISPONIBILE", "СТАТУС: ГОРЯЧАЯ КЛАВИША НЕДОСТУПНА"});
        
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
        d.put("sched_title", new String[]{"(Z) Zamanlayici", "(Z) Scheduler", "(Z) Zeitplaner", "(Z) Planificateur", "(Z) Pianificatore", "(Z) Планировщик"});
        d.put("sched_use", new String[]{"Gecikmeli baslat", "Delayed start", "Verzögerter Start", "Démarrage différé", "Avvio ritardato", "Запуск с задержкой"});
        d.put("sched_sec", new String[]{"saniye sonra", "seconds later", "Sekunden später", "secondes après", "secondi dopo", "секунд спустя"});
        d.put("sched_countdown", new String[]{"DURUM: BASLIYOR (%d sn)", "STATUS: STARTING (%d s)", "STATUS: START IN (%d s)", "STATUT: DEBUT (%d s)", "STATO: AVVIO (%d s)", "СТАТУС: СТАРТ (%d с)"});
        d.put("tray_show", new String[]{"Goster", "Show", "Anzeigen", "Afficher", "Mostra", "Показать"});
        d.put("tray_toggle", new String[]{"Baslat / Durdur", "Start / Stop", "Start / Stopp", "Demarrer / Arreter", "Avvia / Ferma", "Старт / Стоп"});
        d.put("tray_exit", new String[]{"Cikis", "Exit", "Beenden", "Quitter", "Esci", "Выход"});
        d.put("tabhk_title", new String[]{"(K) Sekme Kisayollari", "(K) Per-Tab Hotkeys", "(K) Tab-Hotkeys", "(K) Raccourcis par onglet", "(K) Tasti per scheda", "(K) Горячие клавиши вкладок"});
        d.put("io_export", new String[]{"Disa Aktar (JSON)", "Export (JSON)", "Export (JSON)", "Exporter (JSON)", "Esporta (JSON)", "Экспорт (JSON)"});
        d.put("io_import", new String[]{"Ice Aktar (JSON)", "Import (JSON)", "Import (JSON)", "Importer (JSON)", "Importa (JSON)", "Импорт (JSON)"});
        d.put("io_fail", new String[]{"Dosya islemi basarisiz: ", "File operation failed: ", "Dateioperation fehlgeschlagen: ", "Echec du fichier: ", "Operazione file fallita: ", "Сбой работы с файлом: "});
        d.put("prof_title", new String[]{"(P) Profiller", "(P) Profiles", "(P) Profile", "(P) Profils", "(P) Profili", "(P) Профили"});
        d.put("prof_save", new String[]{"Kaydet", "Save", "Speichern", "Enregistrer", "Salva", "Сохранить"});
        d.put("prof_load", new String[]{"Yukle", "Load", "Laden", "Charger", "Carica", "Загрузить"});
        d.put("prof_del", new String[]{"Sil", "Delete", "Löschen", "Supprimer", "Elimina", "Удалить"});
        d.put("rec_start", new String[]{"● Kaydet", "● Record", "● Aufnehmen", "● Enregistrer", "● Registra", "● Запись"});
        d.put("rec_stop", new String[]{"■ Durdur (ESC)", "■ Stop (ESC)", "■ Stopp (ESC)", "■ Arreter (ESC)", "■ Ferma (ESC)", "■ Стоп (ESC)"});
        d.put("rec_status", new String[]{"DURUM: KAYDEDILIYOR (ESC=dur)", "STATUS: RECORDING (ESC=stop)", "STATUS: AUFNAHME (ESC=stopp)", "STATUT: ENREGISTREMENT (ESC)", "STATO: REGISTRAZIONE (ESC)", "СТАТУС: ЗАПИСЬ (ESC=стоп)"});
        d.put("shut_fail", new String[]{"Kapatma komutu basarisiz oldu: ", "Shutdown command failed: ", "Herunterfahren fehlgeschlagen: ", "Echec de la commande d'arret: ", "Comando di spegnimento fallito: ", "Сбой команды выключения: "});
        d.put("shut_unsup", new String[]{"Bu isletim sisteminde otomatik kapatma desteklenmiyor: ", "Automatic shutdown not supported on this OS: ", "Automatisches Herunterfahren auf diesem OS nicht unterstützt: ", "Arret automatique non supporte sur cet OS: ", "Spegnimento automatico non supportato su questo OS: ", "Автовыключение не поддерживается в этой ОС: "});
    }
    public static String get(String key) { return d.containsKey(key) ? d.get(key)[L] : key; }
}
