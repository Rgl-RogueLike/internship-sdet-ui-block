@echo off
echo ============================================
echo Starting Selenium Grid Hub...
echo ============================================


if not exist selenium-server.jar (
    echo Downloading Selenium Server...
    curl -L -o selenium-server.jar https://github.com/SeleniumHQ/selenium/releases/download/selenium-4.44.0/selenium-server-4.44.0.jar
)

java -jar selenium-server.jar hub
pause