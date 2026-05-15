# 🖱️ AutoClicker Ultimate (V5 Suite)

Sürekli tıklamaktan veya aynı klavye kombinasyonlarını girmekten usandınız mı? O zaman oyunları kurallarına, hayatı akışına göre oynatan profesyonel araca hoş geldiniz! ^-^ 

Java tabanlı `AutoClicker Ultimate v5.3`, eski nesil basit tıklayıcıların ötesine geçerek; **Piksel (Renk) Analizi yapabilen Zeka**, **Zincir Makro (Combo) Dizilimleri**, **Oto-Bilgisayar Kapatıcı** gibi ileri teknoloji sistemleri barındıran tam donanımlı profesyonel bir otomasyon yazılımıdır.

## 🌟 Öne Çıkan Özellikler (Ultimate Sürüm)

- **👁️ Piksel Tarayıcı (Reaction Bot):** Belirlediğiniz bir (X,Y) noktasındaki **Renkte (%5-10 tolerans esnekliği ile)** bir eşleşme veya değişim yakalandığında, bir programcı zekasıyla anında fare tıklayabilir, özel bir tuşa basabilir veya uyarı zili çalabilir. Videolarda ya da oyun içi envanter yönetimlerinde mükemmeldir!
- **🔗 Zincir Makro Motoru:** Basit tıklamaları unutun. Fare hareketi (X,Y), Klavye vuruşları ve Bekleme (ms) sürelerini art arda listeleyerek sonsuz döngülü dev kombinaosyonlar inşa edin.
- **🛡️ İnsan Modu (Humanizer / Anti-Ban):** Girdiğiniz saniye / milisaniye komutlarına +- %15 arasında kusursuz rastgeleliğe sahip, "Organik Dalgalanmalar" ekleyerek bot korumalarını (Anti-Bot) insan olduğunuza ikna eder.
- **🛑 Akıllı Limitör Sistemi (Otomatik Kapanış):** Bilgisayarı / Oyunu kendi haline bıraktığınızda belirlenen bir `Dakika` veya gerçekleştirilen `İşlem (Döngü) Sayısı` dolduğunda önce makroyu keser, isterseniz bilgisayarı komple otomatik kapatarak (Shutdown) elektrik faturasını kurtarır!
- **🌐 Evrensel Dil (i18n):** TR, EN, DE, FR, IT ve RU dahil tam 6 adet dünya dilini anında çevirisini barındırır. Erişilebilirlik `[ ? ]` bilgi butonlarıyla tüm karmaşık ayarları kullanımınıza sadeleştirir.
- **🎨 FlatLaf Özelleştirilebilir Arayüz:** Modern "Dark Mode" temasının yanı sıra, programın çalışma esnasında kapatılmadan bile metin renklerini, ikon boyutlarını ve "Aydınlık/Karanlık" tonlarını anlık (Live Reload) değiştirebileceğiniz gelişmiş stil yapısı!
- **🗃️ Seçilebilir Kısayol (Global Hotkey) & Save Sistemi:** Tuşlarınızı atayın (F6 vb.) ve pencere alta atıldığında dahi çalışsın! Tüm zincir makroları, ayarlarınız, renk profiliniz arkada sürekli anında şifrelenerek saklanır, ertesi gün eksiksiz kaldığınız yerden devam edersiniz.

## 🚀 Kurulum ve Kullanım

### Gereksinimler
Platformdan bağımsızdır, sisteminizde **Java (JDK veya JRE 8+)** kurulu olmalıdır.

### Hızlı Başlangıç
1. Proje ana klasöründeki **`run.bat`** dosyasına çift tıklayın.
2. İlk çalıştırmanız esnasında UI ek kütüphaneleri (`FlatLaf` ve `JNativeHook`) kod dizinine (`lib/`) PowerShell scriptleri ile sizin için otomatik indirilir!
3. Uygulama arayüzünden ihtiyacınız olan sekmeye girin (Normal Makro, Zincir, Piksel). Programın `[ ? ]` tuşlarını takip ederek bilgilenin.
4. Oyun/Belge içerisinde dilediğiniz vakit Kısayol Tuşunuzla (**Standart: F6**) komut zincirini kırbaçlayın!

## 🛠️ Mimari & Teknolojiler
- **Java Swing & FormDev FlatLaf:** Modern Arayüz ve UI sekme/renk yönetimi.
- **Java AWT Robot / MouseInfo:** DPI'a (-ölçeklendirmeye) duyarlı Koordinat, Piksel okuma ve mekanik donanım simülasyonu.
- **JNativeHook:** İşletim sistemine (Kernel seviyesinde) bağlanan çok katmanlı Anti-Blok arka plan tuş dinleme kütüphanesi.
- **i18n In-Memory Mapping:** 6 dilde kayıpsız & gecikmesiz Hot-Reload Çeviri Mimarisi.
