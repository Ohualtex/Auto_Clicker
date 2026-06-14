#!/usr/bin/env bash
# AutoClicker - macOS/Linux native paket (JRE gomulu, kullanicida Java gerekmez)
# Linux'ta .deb/.rpm icin sirasiyla dpkg/rpmbuild gerekir; macOS'ta .dmg uretilir.
# Bagimsiz calistirilabilir image icin: --type app-image
set -e
cd "$(dirname "$0")/.."

echo "[INFO] Fat-jar derleniyor..."
./mvnw -q clean package

JPACKAGE="jpackage"
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/jpackage" ]; then
  JPACKAGE="$JAVA_HOME/bin/jpackage"
fi

rm -rf dist
echo "[INFO] app-image olusturuluyor (JRE gomulu)..."
"$JPACKAGE" --type app-image --input target --main-jar autoclicker.jar \
  --name AutoClicker --app-version 7.2 --dest dist \
  --java-options "-Dfile.encoding=UTF-8"

echo "[OK] Olusturuldu: dist/AutoClicker"
