#!/bin/bash
rm -rf target

javac -d ./target -cp "lib/jcommander-1.82.jar:lib/JColor-5.5.1.jar" src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/*.java

mkdir -p ./target/resources
cp ./src/resources/it.bmp ./target/resources/it.bmp

jar cvfm images-to-chars-printer.jar ./src/manifest.mf -C target .
cd target

#java edu.school21.printer.app.Program 0 . ./resources/it.bmp

java -jar ../images-to-chars-printer.jar -w=MAGENTA -b=GREEN  # запуск jar файла

#java -jar ../images-to-chars-printer.jar . Q ./target/resources/it.bmp    # для запуска вручную из консоли из корня проекта