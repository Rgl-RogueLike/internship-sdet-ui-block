@echo off
echo ============================================
echo Starting Selenium Grid Node...
echo ============================================

if not exist selenium-server.jar (
    echo Error: selenium-server.jar not found. Run start-hub.bat first to download it.
    pause
    exit /b
)

java -jar selenium-server.jar node --hub http://192.168.0.62:4444
pause