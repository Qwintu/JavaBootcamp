# Инструкции по сборке и запуску проекта ImagesToChar

## Структура проекта
```
ImagesToChar/
├── src/
│   ├── java/
│   │   └── edu/
│   │       └── school21/
│   │           └── printer/
│   │               ├── app/
│   │               │   └── Program.java
│   │               └── logic/
│   │                   ├── ComndLineParser.java
│   │                   └── BmpPrinter.java
│   ├── resources/
│   └── manifest.mf
├── target/
│   ├── edu/school21/printer/
│   └── resources/
├── compil.sh
└── README.txt
```

## Компиляция и запуск

### Компиляция
Перейдите в корневую директорию проекта и запустите скрипт compil.sh:
```sh
cd ImagesToChar
sh compil.sh
```

### Компиляция и запуск вручную:
Компилляция
```sh
cd ImagesToChar
```

Скомпилируйте проект, указав путь к библиотекам JCommander и JColor:
```sh
javac -d ./target -cp "lib/jcommander-1.82.jar:lib/JColor-5.5.1.jar" src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/*.java
```

Запуск  
Создать директорию target/resources и скопировать туда it.bmp:
``` sh
mkdir -p ./target/resources
cp ./src/resources/it.bmp ./target/resources/it.bmp
```

Создать jar файл:
```sh
jar cvfm images-to-chars-printer.jar ./src/manifest.mf -C target .
```
jar: команда для работы с JAR-файлами в Java. Она используется для создания, обновления, извлечения и просмотра содержимого JAR-файлов.  
cvfm: набор опций, которые указывают, что именно нужно сделать:  
c: Создать новый JAR-файл.  
v: Выводить подробную информацию о процессе (verbose).  
f: Указывает, что следующий аргумент будет именем файла JAR.  
m: Указывает, что следующий аргумент будет файлом манифеста, который будет включен в JAR-файл.  
images-to-chars-printer.jar: Это имя создаваемого JAR-файла. В данном случае он будет называться images-to-chars-printer.jar.  
src/manifest.txt: Это путь к файлу манифеста, который будет включен в JAR-файл. Файл манифеста содержит метаданные о JAR-файле, такие как версия, главные классы и другие настройки.  
-C target .: Эта часть команды указывает, что нужно изменить директорию на target и затем включить все файлы и папки из этой директории в JAR-файл.  

Запуск jar файла из корня проекта с указанием цветов для белых и черных пикселей::
```sh
java -jar ../images-to-chars-printer.jar -w=MAGENTA -b=GREEN
```

где:

--white - имя, для цвета для белых пикселей  
--black - символ, для цвета  для черных пикселей  

Пример запуска
```s
java -jar ../images-to-chars-printer.jar -w=MAGENTA -b=GREEN
```
Поддерживаемые цвета:  
BLACK,  
BLUE,  
BRIGHT_BLACK,  
BRIGHT_BLUE,  
BRIGHT_CYAN,  
BRIGHT_GREEN,  
BRIGHT_MAGENTA,  
BRIGHT_RED,  
BRIGHT_WHITE,  
BRIGHT_YELLOW,  
CYAN,  
GREEN,  
MAGENTA,  
RED,  
WHITE,  
YELLOW  
