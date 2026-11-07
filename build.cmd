@echo off
rem Rebuild Biome HUD for Minecraft 1.18.2. Everything stays on D: except the existing JDK 17.
setlocal
set "GRADLE_USER_HOME=D:\Dev\biome-hud\gradle-home"
set "JAVA_HOME=C:\Program Files\Java\jdk-17"
call "D:\Dev\gradle\gradle-8.7\bin\gradle.bat" -p "D:\Dev\biome-hud" build --console=plain
set "EXITCODE=%ERRORLEVEL%"
echo.
echo Jar output: D:\Dev\biome-hud\build\libs
endlocal & exit /b %EXITCODE%