package com.ohualtex.autoclicker.config;

import com.ohualtex.autoclicker.core.ConfigPaths;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Config ve profil dosyalarinin disk islemlerini yonetir (yazilabilir kullanici dizininde).
 * UI'dan bagimsiz: Properties alir/dondurur; anahtar-deger toplama cagiran tarafta kalir.
 */
public final class ConfigStore {

    private final File dir;
    private final File configFile;

    public ConfigStore() {
        dir = new File(ConfigPaths.configDir(
            System.getProperty("os.name"), System.getenv("APPDATA"), System.getProperty("user.home")));
        try { dir.mkdirs(); } catch (Exception ignore) {}
        configFile = new File(dir, "config.properties");
        migrateLegacy();
    }

    // Eski CWD config.properties varsa bir kez tasi (geriye uyum)
    private void migrateLegacy() {
        File legacy = new File("config.properties");
        if (!configFile.exists() && legacy.exists()) {
            try { java.nio.file.Files.copy(legacy.toPath(), configFile.toPath()); } catch (Exception ignore) {}
        }
    }

    public Properties load() {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            p.load(fis);
        } catch (FileNotFoundException e) {
            // ilk acilis - normal
        } catch (Exception e) {
            System.err.println("[AutoClicker] Config okunamadi: " + e.getMessage());
        }
        return p;
    }

    public void store(Properties p) { storeTo(p, configFile); }

    private void storeTo(Properties p, File f) {
        try (FileOutputStream fos = new FileOutputStream(f)) {
            p.store(fos, "AutoClicker Configuration");
        } catch (Exception e) {
            System.err.println("[AutoClicker] Config kaydedilemedi: " + e.getMessage());
        }
    }

    // ---- Profiller ----
    private String sanitize(String n) { return n.replaceAll("[^A-Za-z0-9_-]", "_"); }

    public File profileFile(String name) { return new File(dir, "profile-" + sanitize(name) + ".properties"); }

    public void saveProfile(Properties p, String name) { storeTo(p, profileFile(name)); }

    public void deleteProfile(String name) {
        File f = profileFile(name);
        if (f.exists()) f.delete();
    }

    /** Profili yukler; yoksa veya bozuksa null. */
    public Properties loadProfile(String name) {
        File f = profileFile(name);
        if (!f.exists()) return null;
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream(f)) { p.load(fis); }
        catch (Exception e) { return null; }
        return p;
    }

    public String[] listProfiles() {
        File[] fs = dir.listFiles((d, n) -> n.startsWith("profile-") && n.endsWith(".properties"));
        if (fs == null) return new String[0];
        List<String> names = new ArrayList<>();
        for (File f : fs) {
            String n = f.getName();
            names.add(n.substring("profile-".length(), n.length() - ".properties".length()));
        }
        Collections.sort(names);
        return names.toArray(new String[0]);
    }
}
