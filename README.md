# 🖱️ AutoClicker Pro

Sürekli tıklamaktan usandınız mı? Bunu deneyin! ^-^ 
Java ve Swing ile geliştirilmiş, arka plan dinleme mimarisine sahip modern ve akıllı bir otomatik tıklayıcı projesidir.

## 🌟 Özellikler

- **Global Kısayol Tuşu (F6):** Program arka planda görünmez olsa veya oyun oynuyor olsanız dahi (odağa bağlı olmadan) `F6` tuşu ile otomatik tıklamayı istediğiniz an başlatıp durdurabilirsiniz.
- **Çoklu Tıklama Seçenekleri:** Geleneksel Sol tık'ın yanı sıra; **Sağ Tık**, **Orta Tık** ve **Çift Sol Tık** atamaları yapabilirsiniz.
- **Senkronize Hız Kontrolü:** Saniyedeki tıklama hızınızı (CPS) hassas kaydırıcı çubuk (Slider) veya metin giriş kutusu ile belirleyebilirsiniz. Modüller birbiri ile anlık senkronize çalışır.
- **Otomatik Kurulum (run.bat):** Java parametreleriyle uğraşmazsınız. Çalıştırma betiği bağımlılıkları tek seferlik otomatik çeker ve ekranınıza uygulamayı anında getirir.

## 🚀 Kurulum ve Kullanım

### Gereksinimler
Sisteminizde **Java (JDK veya JRE 8+)** kurulu olmalıdır.

### Hızlı Başlangıç
1. Proje ana klasöründeki **`run.bat`** dosyasına çift tıklayın.
2. İlk çalışmada ek arka plan dinleyici kütüphanesi (`JNativeHook`) sizin için PowerShell üzerinden otomatik indirilecek ve derleme sağlanacaktır.
3. Uygulama arayüzü açıldıktan sonra tıklama tipiniz ile CPS (hız) değerinizi ayarlayın.
4. Bilgisayarda nerede olursanız olun tıklamayı başlatmak/durdurmak için **`F6`** tuşuna basın!

## 🛠️ Teknolojiler
- **Java Swing & AWT Robot:** Arayüz bileşenleri ve otomatik fare manipülasyonu.
- **JNativeHook:** İşletim sistemine doğrudan bağlantı kuran arka plan (global) tuş dinleyicisi.
