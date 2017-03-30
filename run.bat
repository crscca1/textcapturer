set JAVA_HOME=D:\dev\pub\java\jdk1.8.0_112
set PATH=%JAVA_HOME%\bin
mkdir bin
javac -source 1.8 -target 1.8 -cp lib\*;bin -d bin -sourcepath src src\textcapturer\TextCapturer.java
java -cp lib\*;bin textcapturer.TextCapturer