# Changelog

Tüm önemli değişiklikler bu dosyada belgelenir.
Format: [Keep a Changelog](https://keepachangelog.com/tr/1.1.0/) · Sürümleme: [SemVer](https://semver.org/lang/tr/).

## [Unreleased]

## [8.5.0] - 2026-06-21
SemVer benimsendi; sürüm tabanı bu sürümden itibaren `MAJOR.MINOR.PATCH`.

### Eklendi
- İlk-çalıştırma sorumlu-kullanım onay diyalogu.
- Oto-güncelleme: arka planda GitHub Releases sürüm kontrolü + bildirim.
- CI release: native installer (`.msi` / `.dmg` / `.deb`) + her dosya için SHA-256 checksum.
- Kod-imzalama iskeleti (yorumlu, sertifika eklenince aktif) + README "Güvenlik & Doğrulama".
- i18n `ResourceBundle` yapısı (`messages_<lang>.properties`, 6 dil) + `LangTest`, `VersionCompareTest`.

### Değişti
- i18n bellek-içi `String[][]` → `.properties` ResourceBundle.
- Yeniden kullanılabilir UI widget'ları `ui.Widgets` paketine taşındı.
- Çift-tık `999` sentinel'i → `ActionType.MOUSE_DOUBLE_CLICK` enum (geriye uyumlu).

## [7.5.0] - 2026-06-20
### Değişti
- Mimari: tek dosyadan `config`/`core`/`i18n`/`model`/`ui` paket katmanlarına; `MacroEngine` (Strategy), `ConfigStore`.
- Derleme hedefi Java 21; pencere başlığı manifest sürümünden.

## [7.0.0] - 2026-06-20
### Eklendi
- Maven build (`mvnw`), fat-jar, GitHub Actions CI, `core` birim testleri.
- Güvenlik: ESC panik tuşu, PC-kapatma iptal penceresi, limit doğrulama.
- Özellikler: record/replay, profiller, JSON import/export, tray, zamanlayıcı, per-sekme hotkey, yumuşak fare.
- Paketleme: jpackage native paket, tag-tetikli çok-OS Release otomasyonu.

[Unreleased]: https://github.com/Ohualtex/Auto_Clicker/compare/v8.5.0...HEAD
[8.5.0]: https://github.com/Ohualtex/Auto_Clicker/releases/tag/v8.5
[7.5.0]: https://github.com/Ohualtex/Auto_Clicker/releases/tag/v7.5
[7.0.0]: https://github.com/Ohualtex/Auto_Clicker/releases/tag/v7.0
