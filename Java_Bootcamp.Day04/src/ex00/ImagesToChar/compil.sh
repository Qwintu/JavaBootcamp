#!/bin/bash
# rm -rf target
#javac -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/BmpPrinter.java

javac -d ./target src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/BmpPrinter.java

cd target
#java edu.school21.printer.app.Program #--black="0" --white="." --image="/Users/bankblac/IdeaProjects/Java_Bootcamp.Day04-1/src/it.bmp"

java edu.school21.printer.app.Program 0 . it.bmp