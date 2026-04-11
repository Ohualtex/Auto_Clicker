# 🖱️ AutoClicker Ultimate

Sürekli tıklamaktan usandınız mı? Bunu deneyin! ^-^ 

Java ile geliştirilmiş, **FlatLaf** karanlık (dark) arayüz tasarımı ile son derece modern, arka plan klavye dinleme yapısına (JNativeHook) sahip gelişmiş bir otomatik fare ve klavye makro projesidir.

## 🌟 Öne Çıkan Özellikler

- **🌑 FlatLaf Dark Mode Arayüz:** Modern, koyu temalı ve göz yormayan, 3 farklı sekmeye bölünmüş ("Fare Makrosu", "Klavye Makrosu", "Ayarlar") profesyonel tasarım.
- **🔠 Klavye Makrosu (Auto-Typer):** Fare makrosuna ek olarak sadece bir fare tıklayıcısı değil; dilediğiniz bir harfe (Örn: Boşluk, E, Q) saniyede belirlediğiniz defa otomatik basabilen bir klavye otomatıdır.
- **🛡️ İnsan Modu (Humanizer / Anti-Ban):** Basit bot korumalarına takılmamak için tıklamaların arasına +- %20 arasında tamamen makine yapası olmayan, organik "Rastgele Gecikmeler" serpiştirerek insanlaştırma sağlar.
- **🎯 Kilitli Hedef Belirleme:** Bir tuşla ekrandaki (X,Y) koordinatlarını kilitlersiniz. Ardından farenizi nereye çekerseniz çekin, program inatla başlattığınız hedefe tıklamaya devam eder.
- **🗃️ Seçilebilir Kısayol (Custom Hotkey) & Save/Load Sistemi:**
  - Uygulamayı Başlatma/Durdurma kısayolunu herhangi bir tuşa (F6, F12, vb.) serbestçe atayabilirsiniz. Oyun oynarken veya arka plandayken her zaman dinler.
  - CPS Hızınız, Atadığınız kısayol tuşunuz, seçtiğiniz ayarlar uygulama kapatıldığında otomatik olarak arka planda kaydedilir. Ertesi gün kaldığınız yerden devam edersiniz.

## 🚀 Kurulum ve Kullanım

### Gereksinimler
Sisteminizde **Java (JDK veya JRE 8+)** kurulu olmalıdır.

### Hızlı Başlangıç
1. Proje ana klasöründeki **`run.bat`** dosyasına çift tıklayın.
2. İlk çalışmada ek UI dinleyici kütüphaneleri (`FlatLaf` ve `JNativeHook`) sizin için PowerShell üzerinden otomatik indirilecek klasöre dahil edilecektir.
3. Uygulama arayüzü açıldığında:
   - "Fare Makrosu" veya "Klavye Makrosu" sekmelerinden birine girip CPS (Hız) değerinizi ve gerekli hedeflerinizi belirleyin.
   - Herhangi bir ekranda oyundayken veya çalışırken kısayol tuşunuzla (Varsayılan: **F6**) eylemi başlatın ve durdurun!

## 🛠️ Teknolojiler
- **Java Swing & FlatLaf:** Modern Arayüz ve sekme yönetimi.
- **Java AWT Robot:** Koordinat ve mekanik işletim sistemi komut simülasyonu.
- **JNativeHook:** İşletim sistemine doğrudan bağlantı kuran arka plan (global) tuş / fare dinleyicisi.
