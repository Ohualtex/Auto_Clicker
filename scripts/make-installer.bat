@echo off
REM AutoClicker - Windows native paket (JRE gomulu, kullanicida Java gerekmez)
REM app-image: WiX gerektirmez, dist\AutoClicker\AutoClicker.exe uretir.
REM .exe/.msi installer icin WiX Toolset kurulu olmali; o zaman --type msi kullanin.
setlocal
cd /d "%~dp0\.."

echo [INFO] Fat-jar derleniyor...
call mvnw.cmd -q clean package
IF %ERRORLEVEL% NEQ 0 ( echo [ERROR] Derleme hatasi! & exit /b 1 )

set "JPACKAGE=jpackage"
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\jpackage.exe" set "JPACKAGE=%JAVA_HOME%\bin\jpackage.exe"

if exist dist rmdir /s /q dist

echo [INFO] app-image olusturuluyor (JRE gomulu)...
"%JPACKAGE%" --type app-image --input target --main-jar autoclicker.jar ^
  --name AutoClicker --app-version 7.2 --dest dist ^
  --java-options "-Dfile.encoding=UTF-8"
IF %ERRORLEVEL% NEQ 0 ( echo [ERROR] jpackage basarisiz! & exit /b 1 )

echo [OK] Olusturuldu: dist\AutoClicker\AutoClicker.exe
echo      (.msi installer icin WiX Toolset kurup --type msi kullanin)
