package edu.school21.printer.app;

import java.io.File;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import edu.school21.printer.logic.BmpPrinter;
import edu.school21.printer.logic.ComndLineParser;

public class Program {
    public static void main(String[] args) {
        ComndLineParser clParser = new ComndLineParser();

        // Создаем новый экземпляр JCommander.Builder
        JCommander.Builder jCBuilder = JCommander.newBuilder();
        // Добавляем объект app для хранения значений параметров командной строки
        jCBuilder.addObject(clParser);
        // Завершаем процесс построения и создаем экземпляр JCommander
        JCommander jCommander = jCBuilder.build();

        try {
            // Разбор аргументов командной строки
            jCommander.parse(args);
        } catch (ParameterException e) {
            // Обработка ошибок разбора параметров
            System.err.println(e.getMessage());
            jCommander.usage(); // Вывод справки по использованию
            return;
        }

        File file = new File("resources/it.bmp");

        BmpPrinter.printWithColor(file, clParser.getWhite().toUpperCase(), clParser.getBlack().toUpperCase());
        System.out.println();
        BmpPrinter.printWithChar(file, clParser.getWhite().charAt(0), clParser.getBlack().charAt(0));
        System.out.println("\n" + file.getName() + " in HEX");
        BmpPrinter.printHexFile(file);
    }
}
