call runcrud.bat
if "%ERRORLEVEL%" == "0" goto waitfortomcat
echo.
echo Startup script failed, ending script.
goto fail

:waitfortomcat
set /A tomcatloopcount = 0
:tomcatloop
set /A tomcatloopcount=tomcatloopcount+1
if "%tomcatloopcount%" == "30" (
    echo.
    echo Waiting for tomcat timed out. Exiting script.
    goto fail
)
echo In tomcat loop - waiting for tomcat server
echo --- sleeping for 1 second
sleep 1
curl -s --max-time 2 localhost:8080 > nul && goto openbrowser || goto tomcatloop

:openbrowser
call "C:\Program Files\Mozilla Firefox\firefox.exe" -new-window http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo Error opening browser, ending script.
goto fail

:fail
echo.
echo Errors in script execution.

:end
echo.
echo Script finished.