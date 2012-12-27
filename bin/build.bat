@echo off
if EXIST ..\lib\comm.jar goto build
echo.
echo ..\lib\comm.jar could not be found.
echo Make sure you downloaded this file and placed it in the lib dir.
goto end

:build
if %windir%==C:\WINDOWS goto windows
if %windir%==C:\WINNT goto winnt
echo Sorry, couldn't tell windows type.
echo That's what you get for using such a shitty OS!
goto end

:windows
cd windows
call build.bat
cd ..
goto end

:winnt
call winnt\build.bat

:end
echo.
