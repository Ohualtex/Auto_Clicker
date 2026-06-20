@echo off
setlocal
cd /d "%~dp0"

echo [INFO] Maven (wrapper) ile derleniyor (fat-jar)...
call mvnw.cmd -q clean package
IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Derleme hatasi olustu! Lutfen ciktiyi kontrol edin.
    pause
    exit /b %ERRORLEVEL%
)

echo [INFO] AutoClicker baslatiliyor...
start javaw -jar target\autoclicker.jar
exit
