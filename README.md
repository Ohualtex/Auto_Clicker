<div align="center">

# 🖱️ AutoClicker Ultimate

**Java tabanlı · modern · çok dilli · insan benzeri davranış üreten profesyonel makro motoru.**

[![Java](https://img.shields.io/badge/Java-8%2B-orange?logo=oracle&logoColor=white)](https://www.oracle.com/java/)
[![FlatLaf](https://img.shields.io/badge/UI-FlatLaf%203.4.1-blueviolet)](https://www.formdev.com/flatlaf/)
[![JNativeHook](https://img.shields.io/badge/Hook-JNativeHook%202.2.2-success)](https://github.com/kwhat/jnativehook)
[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-lightgrey)]()
[![i18n](https://img.shields.io/badge/Dil-TR%20%7C%20EN%20%7C%20DE%20%7C%20FR%20%7C%20IT%20%7C%20RU-informational)]()

Sürekli tıklamaktan ve aynı tuş kombinasyonlarını girmekten usandınız mı?
Oyunları kurallarına, hayatı akışına bırakan profesyonel araca hoş geldiniz. ^-^

</div>

---

## 🌟 Öne Çıkan Özellikler

### Otomasyon Çekirdeği
- **👁️ Piksel Tarayıcı (Reaction Bot)** — Belirlediğiniz (X,Y) noktasındaki renkte **%5–10 tolerans** ile eşleşme veya değişim yakalandığında anında: fare tıklayabilir, özel bir tuşa basabilir, uyarı zili çalabilir. Envanter yönetiminden alarm sistemlerine kadar geniş kullanım alanı.
- **🔗 Zincir Makro (Combo) Motoru** — Fare hareketi (X,Y), klavye vuruşları ve bekleme (ms) adımlarını sıralayarak sonsuz döngülü dev kombinasyonlar inşa edin.
- **🖱️ Klasik Auto-Clicker & Auto-Typer** — Sol/Sağ/Orta/Çift tıklama ve istediğiniz tuşa periyodik basma (1–100 CPS), kilitli hedef koordinat opsiyonu.

### Akıllı Davranış
- **🛡️ İnsan Modu (Humanizer / Anti-Ban)** — Saniye/milisaniye komutlarına ±%15 rastgele, **organik dalgalanma** ekleyerek bot korumalarını insan olduğunuza ikna eder.
- **🛑 Akıllı Limitör (Otomatik Kapanış)** — `Dakika` veya `Döngü Sayısı` dolduğunda makroyu durdurur; isterseniz **bilgisayarı güvenli kapatma**. Windows / macOS / Linux için uygun komutu çağırır, bilinmeyen sistemde sessiz kalmaz uyarı verir.

### Kullanıcı Deneyimi
- **🌐 6 Dilde Anlık Çeviri (i18n)** — TR · EN · DE · FR · IT · RU. Dil geçişi gerçek zamanlı, yeniden başlatma yok.
- **🎨 Canlı Tema & Renk Sistemi** — FlatLaf Dark/Light tonu, metin rengi ve font boyutu çalışma esnasında değiştirilebilir.
- **ⓘ Bağlam Bilgi Butonları** — Her karmaşık ayarın yanındaki bilgi ikonu özelliği anlık açıklar (CPS, piksel koşulu, tarama hızı, anti-ban, limitör...).
- **🗃️ Global Hotkey & Otomatik Kayıt** — Tetik tuşunu serbestçe atayın (varsayılan **F6**), pencere arka planda olsa da çalışır. Tüm ayarlar, zincirler ve renk profili `config.properties` dosyasında kalıcı saklanır.

---

## 🧭 Sekmeler

| Sekme | Açıklama |
|---|---|
| 🖱️ **Fare** | Klasik auto-clicker. CPS slider, tıklama tipi, kilitli koordinat seçeneği. |
| ⌨️ **Klavye** | Belirli bir tuşa periyodik basma. |
| 🔗 **Zincir** | Combo macro: tıklama + tuş + ışınlama + bekleme adımlarından oluşan sıralı senaryo. |
| 👁️ **Piksel** | Renk algılama tabanlı tepki botu. Eşleşme/Değişim koşulu + tolerans. |
| ⚙️ **Ayarlar** | Hotkey, dil, tema, renk, limitör konfigürasyonu. |

---

## 🚀 Kurulum

### Gereksinimler
- **Java JDK veya JRE 8+** (`java -version` ile kontrol)
- Windows için `run.bat` her şeyi halleder; diğer platformlarda aşağıdaki manuel adımlar.

### Hızlı Başlangıç (Windows)
1. **`run.bat`** dosyasına çift tıklayın.
2. İlk çalıştırmada FlatLaf ve JNativeHook jar'ları PowerShell ile `lib/` altına otomatik indirilir.
3. Açılan arayüzde sekmenizi seçin; her ayarın yanındaki **ⓘ** butonuna tıklayarak işlevini öğrenin.
4. Oyun/uygulama içindeyken hotkey'inizle (varsayılan **F6**) başlatın/durdurun.

### Manuel Derleme (macOS / Linux)
```bash
mkdir -p lib
curl -sL https://repo1.maven.org/maven2/com/github/kwhat/jnativehook/2.2.2/jnativehook-2.2.2.jar -o lib/jnativehook.jar
curl -sL https://repo1.maven.org/maven2/com/formdev/flatlaf/3.4.1/flatlaf-3.4.1.jar    -o lib/flatlaf.jar
javac -encoding UTF-8 -cp ".:lib/*" AutoClicker.java
java  -Dfile.encoding=UTF-8 -cp ".:lib/*" AutoClicker
```

---

## ⌨️ Kısayollar

| Tuş | Eylem |
|---|---|
| **F6** *(varsayılan)* | Aktif sekmenin makrosunu **Başlat / Durdur** |
| **Orta Tık** | Konum seçme modunda hedefi kilitler |
| **Ayarlar → Hotkey** | İstediğiniz herhangi bir tuşa kısayol atama |

---

## 🛠️ Mimari & Teknolojiler

| Katman | Bileşen | Sürüm | Görev |
|---|---|---|---|
| UI | Java Swing + FormDev FlatLaf | 3.4.1 | Sekmeler, ikonlar, canlı tema |
| Donanım | `java.awt.Robot` + `MouseInfo` | JDK | DPI uyumlu koordinat, tıklama/tuş simülasyonu |
| Global Hook | JNativeHook | 2.2.2 | OS-seviyesi (kernel) tuş & fare dinleme |
| i18n | In-memory `String[][]` mapping | — | 6 dil hot-reload, restart yok |
| Persistence | `java.util.Properties` | JDK | `config.properties` — ayarlar, zincirler, renkler |

---

## 📌 Notlar & Uyumluluk

- **Karakter kodlaması:** `run.bat`, `javaw` çağrısına `-Dfile.encoding=UTF-8` ekler. Türkçe karakterler `ç ğ ı ö ş ü` doğru render edilir.
- **Otomatik kapatma:** Limitör → *Shutdown* seçimi Windows'ta `shutdown -s -t 15`, macOS/Linux'ta `shutdown -h +1` çağırır. Bilinmeyen sistemde kullanıcıya uyarı verir, müdahale etmez.
- **JNativeHook + macOS:** macOS'ta *Erişilebilirlik* izni gerekir (`System Settings → Privacy & Security → Accessibility`).
- **Anti-cheat oyunlar:** Bu araç öğrenme ve verimlilik amacıyla yapılmıştır. Çok oyunculu / anti-cheat korumalı oyunlarda kullanmak hesap askısı veya ban riski taşır — sorumluluk kullanıcıya aittir.

---

<div align="center">

**AutoClicker Ultimate** — V6 sürüm hattı
Made with ☕ and Java Swing

</div>
