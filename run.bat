@echo off
setlocal
cd /d "%~dp0"

IF NOT EXIST "lib" (
    mkdir lib
)

IF NOT EXIST "lib\jnativehook.jar" (
    echo [INFO] JNativeHook kutuphanesi indiriliyor...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/github/kwhat/jnativehook/2.2.2/jnativehook-2.2.2.jar' -OutFile 'lib\jnativehook.jar'"
)

IF NOT EXIST "lib\flatlaf.jar" (
    echo [INFO] FlatLaf Dark Mode kutuphanesi indiriliyor...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/formdev/flatlaf/3.4.1/flatlaf-3.4.1.jar' -OutFile 'lib\flatlaf.jar'"
)

echo [INFO] Kod derleniyor...
javac -cp ".;lib\jnativehook.jar;lib\flatlaf.jar" AutoClicker.java
IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Derleme hatasi olustu! Lutfen dosyayi kontrol edin.
    pause
    exit /b %ERRORLEVEL%
)

echo [INFO] AutoClicker baslatiliyor...
start javaw -cp ".;lib\jnativehook.jar;lib\flatlaf.jar" AutoClicker
exit
