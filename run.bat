@echo off
setlocal
cd /d "%~dp0"

IF NOT EXIST "lib" (
    mkdir lib
)

IF NOT EXIST "lib\jnativehook.jar" (
    echo [INFO] JNativeHook kutuphanesi indiriliyor... ^(Ilk Calistirma^)
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/github/kwhat/jnativehook/2.2.2/jnativehook-2.2.2.jar' -OutFile 'lib\jnativehook.jar'"
)

echo [INFO] Kod derleniyor...
javac -cp ".;lib\jnativehook.jar" AutoClicker.java
IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Derleme hatasi olustu!
    pause
    exit /b %ERRORLEVEL%
)

echo [INFO] AutoClicker baslatiliyor...
start javaw -cp ".;lib\jnativehook.jar" AutoClicker
exit
