<div align="center">

# 🖱️ AutoClicker Ultimate

**Java tabanlı · modern · çok dilli · insan benzeri davranış üreten profesyonel makro motoru.**

[![build](https://github.com/Ohualtex/Auto_Clicker/actions/workflows/build.yml/badge.svg)](https://github.com/Ohualtex/Auto_Clicker/actions/workflows/build.yml)
[![Java](https://img.shields.io/badge/Java-21%2B-orange?logo=oracle&logoColor=white)](https://www.oracle.com/java/)
[![Build](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![FlatLaf](https://img.shields.io/badge/UI-FlatLaf%203.4.1-blueviolet)](https://www.formdev.com/flatlaf/)
[![JNativeHook](https://img.shields.io/badge/Hook-JNativeHook%202.2.2-success)](https://github.com/kwhat/jnativehook)
[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-lightgrey)]()
[![i18n](https://img.shields.io/badge/Dil-TR%20%7C%20EN%20%7C%20DE%20%7C%20FR%20%7C%20IT%20%7C%20RU-informational)]()

Sürekli tıklamaktan ve aynı tuş kombinasyonlarını girmekten usandınız mı?
Oyunları kurallarına, hayatı akışına bırakan profesyonel araca hoş geldiniz. ^-^

</div>

---

## ⬇️ İndir (en kolay yol — Java gerekmez)

[**Releases**](https://github.com/Ohualtex/Auto_Clicker/releases/latest) sayfasından işletim sisteminize uygun paketi indirin — **JRE gömülüdür, ayrıca Java kurmanıza gerek yoktur**:

| Platform | Dosya | Çalıştırma |
|---|---|---|
| Windows | `AutoClicker-Windows.zip` | Aç → `AutoClicker\AutoClicker.exe` |
| macOS | `AutoClicker-macOS.zip` | Aç → `AutoClicker.app` |
| Linux | `AutoClicker-Linux.zip` | Aç → `AutoClicker/bin/AutoClicker` |
| Hepsi | `autoclicker.jar` | `java -jar autoclicker.jar` *(Java 21+ gerekir)* |

---

## 🌟 Öne Çıkan Özellikler

### Otomasyon Çekirdeği
- **👁️ Piksel Tarayıcı (Reaction Bot)** — Bir (X,Y) noktasındaki renkte **ayarlanabilir tolerans (%1–100)** ile eşleşme/değişim yakalandığında: fare tıklar, özel tuşa basar veya zil çalar.
- **🔗 Zincir Makro (Combo) Motoru** — Fare hareketi, tuş ve bekleme adımlarını sıralayıp sonsuz döngülü kombinasyonlar kurun.
- **⏺️ Makro Kaydet/Oynat (Record)** — Zincir sekmesinde **● Kaydet** ile gerçek tıklama/tuş/gecikmelerinizi yakalayıp makroya dönüştürün (ESC ile durdurun).
- **🖱️ Klasik Auto-Clicker & Auto-Typer** — Sol/Sağ/Orta/Çift tıklama, tuşa periyodik basma (1–100 CPS), kilitli hedef koordinat.

### Akıllı Davranış
- **🛡️ İnsan Modu (Anti-Ban)** — Gecikmelere ±%15 organik dalgalanma ekler; **yumuşak (eğrisel) fare hareketi** ile ışınlanma yerine insan benzeri geçiş.
- **🛑 Akıllı Limitör + Güvenli Kapatma** — `Dakika`/`Döngü` dolunca durur; isteğe bağlı **PC kapatma** (Win/macOS/Linux'a uygun komut) — kapanış öncesi **iptal penceresi** ile geri alınabilir.
- **⏱️ Zamanlayıcı** — Makroyu N saniye gecikmeyle başlatın (geri sayımlı).

### Güvenlik & Kullanıcı Deneyimi
- **🆘 ESC Panik Tuşu** — Her zaman açık, değiştirilemez acil durdurma.
- **👤 Profiller** — Birden çok kayıtlı ayar seti (Kaydet/Yükle/Sil).
- **📤 JSON Dışa/İçe Aktarma** — Zincir makrolarını paylaşılabilir dosyaya aktarın.
- **🗂️ Sistem Tepsisi (Tray)** — Simge durumuna küçültünce tepsiye gizlenir; tepsiden başlat/durdur/çıkış.
- **⌨️ Global + Per-Sekme Hotkey** — Tek global tetik (varsayılan **F6**) veya her makro sekmesine ayrı kısayol.
- **🌐 6 Dilde Anlık Çeviri · 🎨 Canlı Tema/Renk/Font · ⓘ Bağlam Bilgi Butonları**

---

## 🧭 Sekmeler

| Sekme | Açıklama |
|---|---|
| 🖱️ **Fare** | Klasik auto-clicker. CPS, tıklama tipi, kilitli koordinat. |
| ⌨️ **Klavye** | Belirli bir tuşa periyodik basma. |
| 🔗 **Zincir** | Combo macro + JSON içe/dışa aktar + **kayıt (record)**. |
| 👁️ **Piksel** | Renk algılama tabanlı tepki botu (eşleşme/değişim + tolerans). |
| ⚙️ **Ayarlar** | Hotkey, per-sekme hotkey, limitör, zamanlayıcı, profil, dil, tema. |

---

## 🚀 Kaynaktan Çalıştırma / Derleme

Proje **Maven** ile derlenir; **Maven kurmanıza gerek yok** — depoyla gelen wrapper (`mvnw`) yeterli. Yalnızca **JDK 21+** gerekir.

```bash
# Derle + test + tek-dosya (fat) jar üret
./mvnw clean package        # Windows: mvnw.cmd clean package

# Çalıştır
java -jar target/autoclicker.jar
```

**Windows kısayolu:** `run.bat` çift tıklayın — `mvnw` ile derleyip başlatır.

**Native installer (JRE gömülü) üretmek:**
```bash
scripts/make-installer.sh      # macOS/Linux  ->  dist/AutoClicker
scripts\make-installer.bat     # Windows      ->  dist\AutoClicker\AutoClicker.exe
```

---

## ⌨️ Kısayollar

| Tuş | Eylem |
|---|---|
| **F6** *(varsayılan)* | Aktif sekmenin makrosunu **Başlat / Durdur** |
| **ESC** | **Acil durdurma** (panik) — her zaman açık, değiştirilemez |
| **Orta Tık** | Konum seçme modunda hedefi kilitler |
| Ayarlar → **Sekme Kısayolları** | Her makroya ayrı hotkey ata |

---

## 🛠️ Mimari & Teknolojiler

Tek dosyadan paket-katmanlı yapıya geçildi (v7):

```
com.ohualtex.autoclicker
├── AutoClicker        Swing UI + orkestrasyon (MacroEngine/Strategy)
├── config/            ConfigStore  (config + profil dosya I/O)
├── core/              Humanizer · ShutdownCommand · ConfigPaths · MousePath  (saf, JUnit testli)
├── i18n/              Lang  (6 dilli sözlük)
├── model/             ActionType · MacroAction
└── ui/                Icons  (vektör ikonlar)
```

| Katman | Bileşen | Görev |
|---|---|---|
| Build | Maven (+ wrapper) · shade · jpackage | fat-jar, native installer |
| UI | Java Swing + FlatLaf 3.4.1 | sekmeler, ikonlar, canlı tema |
| Donanım | `java.awt.Robot` + `MouseInfo` | koordinat, tıklama/tuş simülasyonu |
| Global Hook | JNativeHook 2.2.2 | OS-seviyesi tuş & fare dinleme + kayıt |
| Veri | Gson · `java.util.Properties` | JSON makro · config/profil |
| CI/CD | GitHub Actions | her push'ta derle+test; tag'de çok-OS release |

---

## 🔒 Güvenlik & Doğrulama

Release dosyaları **kod imzalı değildir** (ücretsiz dağıtım); bunun yerine her dosya için **SHA-256 checksum** (`*.sha256`) yayınlanır.

**Bütünlüğü doğrula:**
```bash
# Linux/macOS
shasum -a 256 -c AutoClicker-Linux.zip.sha256
# Windows (PowerShell)
(Get-FileHash AutoClicker-Windows.zip -Algorithm SHA256).Hash
```

**İşletim sistemi uyarısını aşma** (imzasız olduğu için normaldir):
- **Windows SmartScreen:** *"Daha fazla bilgi" → "Yine de çalıştır"*
- **macOS Gatekeeper:** *Sistem Ayarları → Gizlilik ve Güvenlik → "Yine de Aç"*, veya `xattr -dr com.apple.quarantine AutoClicker.app`

İlk çalıştırmada uygulama bir **sorumlu-kullanım onayı** ister ve **GitHub'dan yeni sürüm** olup olmadığını arka planda kontrol eder (config'de `checkUpdates=false` ile kapatılabilir).

---

## 📌 Notlar & Uyumluluk

- **Ayar konumu:** Ayarlar `%APPDATA%\AutoClicker\config.properties` (Windows) veya `~/.autoclicker/` (macOS/Linux) altında saklanır; installer ile kurulumda bile yazılabilir.
- **Otomatik kapatma:** Limitör → *Kapat* seçimi OS'a uygun komutu çalıştırır ve **iptal penceresi** sunar; AFK iseniz kapanır, başınızdaysanız iptal edebilirsiniz. Desteklenmeyen OS'ta uyarı verir.
- **macOS izni:** JNativeHook için *Erişilebilirlik* izni gerekir (`System Settings → Privacy & Security → Accessibility`).
- **Anti-cheat oyunlar:** Bu araç öğrenme ve verimlilik amacıyladır. Anti-cheat korumalı oyunlarda kullanmak ban riski taşır — sorumluluk kullanıcıya aittir.

---

<div align="center">

**AutoClicker Ultimate** — V7 sürüm hattı
Made with ☕ and Java Swing

</div>
