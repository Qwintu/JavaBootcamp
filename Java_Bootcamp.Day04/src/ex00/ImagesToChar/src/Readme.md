# Инструкции по сборке и запуску проекта ImagesToChar

## Структура проекта
```
ImagesToChar/
├── src/
│   └── java/edu/school21/printer/
│       ├── app/
│       │   └── Program.java
│       └── logic/
│           └── ImageConverter.java
├── target/
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

```sh
javac -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageToCharConverter.java
```

Запуск  
Перейдите в директорию target:
``` sh
cd target
```

Запустите программу:
```sh
java java.edu.school21.printer.app.Program WHITE_CHAR BLACK_CHAR PATH_TO_IMAGE
```

где:

WHITE_CHAR - символ, который будет использоваться для белых пикселей
BLACK_CHAR - символ, который будет использоваться для черных пикселей
PATH_TO_IMAGE - полный путь к изображению в формате BMP

Пример запуска
```s
java java.edu.school21.printer.app.Program . 0 it.bmp
```