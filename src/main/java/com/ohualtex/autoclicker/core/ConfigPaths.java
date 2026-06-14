package com.ohualtex.autoclicker.core;

import java.io.File;

/**
 * Config dosyasinin yazilabilir kullanici dizinini secer. Saf ve test edilebilir
 * (filesystem'e dokunmaz); installer ile Program Files'a kurulunca ayarlarin
 * salt-okunur dizine yazilmaya calisilmasini onler.
 */
public final class ConfigPaths {

    private ConfigPaths() {}

    /**
     * @param osName   System.getProperty("os.name")
     * @param appData  Windows %APPDATA% (yoksa null/bos)
     * @param userHome System.getProperty("user.home")
     * @return config dizini (Windows: %APPDATA%\AutoClicker, digerleri: ~/.autoclicker)
     */
    public static String configDir(String osName, String appData, String userHome) {
        String os = (osName == null) ? "" : osName.toLowerCase();
        if (os.contains("win") && appData != null && !appData.isEmpty()) {
            return appData + File.separator + "AutoClicker";
        }
        return userHome + File.separator + ".autoclicker";
    }
}
