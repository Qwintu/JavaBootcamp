# Day 04 — Java Bootcamp
### JAR

*_Сегодня вы научитесь создавать библиотечные архивы и использовать внешние библиотеки._*


# Часть I
### Packages

Код можно организовать на нескольких уровнях. Пакеты — один из способов организации кода, при котором классы располагаются в отдельных папках.

Ваша задача — реализовать функциональность, которая выводит двухцветное изображение в консоль.

Пример черно-белого изображения в формате BMP (этот формат обязателен для решения). Размер изображения — 16*16 пикселей.

![это](https://markdownlivepreview.com/misc/images/it_black.png)

Скачать это изображение можно [здесь](https://yadi.sk/i/nt-C_kZKWrlyNQ) .

Ваше приложение должно принимать входные параметры, соответствующие символам, которые должны отображаться вместо белых и черных пикселей. Еще один важный параметр запуска функции — это полный путь к изображению на вашем жестком диске.

Если символ "." используется для обозначения белого цвета, а "0" — для черного, изображение в консоли может выглядеть так:

![it_console](https://markdownlivepreview.com/misc/images/it_console.png)

Логика приложения должна быть распределена между различными пакетами и иметь следующую структуру:

- ImagesToChar — project folder.
    - src — source files.
        - java — files of Java source code.
            - edu.school21.printer — a series of main packages.
            - app — a package that contains classes for startup.
            - logic — a package that contains the logic for converting an image into an array of characters.
    - target — compiled .class files.
    - edu.school21.printer ...
    - README.txt

Файл README.txt должен содержать инструкции по компиляции и запуску исходного кода из консоли (не из IDE). Инструкции написаны для состояния, когда консоль открыта в корневой папке проекта.

# Часть II
### Первый JAR

Теперь необходимо создать дистрибутив приложения — JAR-архив. Важно, чтобы образ был включен в этот архив (параметр командной строки для полного пути к файлу для этой задачи не требуется).

Необходимо придерживаться следующей структуры проекта:

- ImagesToChar — project folder.
    - src — source files.
        - java — files of Java source code.
            - ...
        - resources — a folder with resource files.
            - image.bmp — the displayed image.
        - manifest.txt — a file containing the description of the initial point for archive startup.
    - target — compiled .class files and archive.
        - edu.school21.printer ...
        - resources
        - images-to-chars-printer.jar
    - README.txt

Архив и все скомпилированные файлы необходимо поместить в целевую папку во время сборки (без ручной передачи файлов; можно использовать команду `cp` в папке ресурсов).

В файле README.txt также должна содержаться информация о сборке и запуске архива.

# Часть III
### JCommander & JCDP


Теперь вам следует добавить и использовать внешние библиотеки:

-   JCommander для командной строки.
-   Для цветного вывода используйте JCDP или JColor.

Загрузите архивы, содержащие эти библиотеки, и добавьте их в проект предыдущего задания.

Теперь параметры запуска приложения следует отредактировать с помощью инструментов JCommander. Изображение следует отображать, используя опцию вывода «цветной» из библиотеки JCDP.

Требуемая структура проекта:
- ImagesToChar — project folder.
    - lib — external library folder.
    - jcommander-*.**.jar
    - JCDP-*.*.*.jar/JCOLOR-*.*.*.jar
    - src — source files.
    - target — compiled .class files and archive.
    - edu.school21.printer
    - com/beust ... — .class files of JCommander library.
    - com/diogonunes ... — .class files of JCDP library.
    - resources
    - images-to-chars-printer.jar
    - README.txt

Файл README.txt также должен содержать информацию о включении внешних библиотек в окончательную сборку.

Пример работы программы:

`$ java -jar images-to-chars-printer.jar --white=RED --black=GREEN`

![it_red](misc/images/it_red.png)