@echo off
echo ============================================
echo Running tests on Selenium Grid...
echo ============================================

set /p THREADS="Enter number of threads: "
set GRID_ENABLED=true

cd /d %~dp0..

mvn clean test -Pparallel-suites "-Dthread.pool.size=%THREADS%"
pause