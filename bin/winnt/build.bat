cd ..\..
del /s /Q classes
mkdir classes
cd source\x10
echo building package x10
javac -classpath .;..;..\..\lib\comm.jar;..\..\classes -d ..\..\classes\ *.java
cd net
echo building package x10.net
javac -classpath .;..\..;..\..\..\lib\comm.jar;..\..\..\classes -d ..\..\..\classes\ *.java
cd ..\awt
echo building package x10.awt
javac -classpath .;..\..;..\..\..\lib\comm.jar;..\..\..\classes -d ..\..\..\classes\ *.java
cd ..\util
echo building package x10.util
javac -classpath .;..\..;..\..\..\lib\comm.jar;..\..\..\classes -d ..\..\..\classes\ *.java
cd ..\..\..\classes
jar cf ..\lib\x10.jar x10
cd ..
deltree /s /Q classes
cd bin\windows
echo done.
