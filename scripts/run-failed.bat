@echo off
echo ============================================
echo Running previously failed tests...
echo ============================================

cd /d %~dp0..

if not exist target\surefire-reports\testng-failed.xml (
    echo No failed tests found. File testng-failed.xml does not exist.
    pause
    exit /b
)

mvn test "-DsuiteXmlFile=target/surefire-reports/testng-failed.xml"
pause