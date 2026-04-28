package edu.school21.printer.logic;

import java.io.*;
import java.io.File;

import com.diogonunes.jcolor.Attribute;
import static com.diogonunes.jcolor.Ansi.colorize;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BmpPrinter {
    private BmpPrinter() {};

    public static void printWithChar(File file, char whiteCh, char blackCh) {
        char ch;
        String imageToBinaryString = convertBmpToBinaryString(file);
        for (int i = 0; i < imageToBinaryString.length(); i++) {
            ch = imageToBinaryString.charAt(i);
            if(ch == '1') {
                System.out.print(blackCh);
            } else if (ch == '0') {
                System.out.print(whiteCh);
            } else {
                System.out.print(ch);
            }
        }
    }

    public static void printWithColor(File file, String whiteColor, String blackColor) {
        final Map<String, Supplier<Attribute>> methodMap = new HashMap<>();
        fillMapWithColors(methodMap);
        char ch;
        String imageToBinaryString = convertBmpToBinaryString(file);
        for (int i = 0; i < imageToBinaryString.length(); i++) {
            ch = imageToBinaryString.charAt(i);
            if(ch == '0') {
                Attribute attribute = methodMap.get(whiteColor).get();  // Используем метод get() для получения объекта Attribute
                System.out.print(colorize(" ",  attribute));
            } else if (ch == '1') {
                Attribute attribute = methodMap.get(blackColor).get();
                System.out.print(colorize(" ",  attribute));
            } else {
                System.out.print(ch);
            }
        }
    }

    public static void printHexFile(File file) {
        if(!file.isFile()){
            System.out.println("read file: file not found " + file.toString());
            return;
        }
        try(FileInputStream readFile = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(readFile)){
            int ch;
            int i = 1;
            while((ch = bis.read()) != -1){ // Read one byte at a time
                String hex = Integer.toHexString(ch);
                if(hex.length() == 1) hex = "0".concat(hex);
                System.out.print(hex + " ");
                if(i % 16 == 0) System.out.print("\n");
                i++;
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String convertBmpToBinaryString(File file) throws IllegalArgumentException {
        if(!file.isFile()){
            throw new IllegalArgumentException("read file: file not found " + file.toString());
        }

        StringBuilder imageToBinaryString = new StringBuilder(256);
        try(FileInputStream readFile = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(readFile)){
            int ch;
            int i = 0;
            final int rowLenInByts = 2;
//            final int rowLenInByts = 22;  // для второго файла
            int byteCounter = 0;
            int byteShiftToPixInfo = 61;
            int flag = 1;
            StringBuilder bufferStr = new StringBuilder(256);
            while((ch = bis.read()) != -1){ // Read one byte at a time
                if(i >byteShiftToPixInfo) {
                    // конец строки пикселей это два байта 00 00, их нужно пропускать при выводе и заменить один на \n
                    if(byteCounter == rowLenInByts || flag < 0) {
                        if(flag > 0) {
                            bufferStr.append("\n");
                            imageToBinaryString.insert(0, bufferStr);
                            bufferStr = new StringBuilder();
                        }
                        byteCounter = 0;
                        i++;
                        flag *= -1;
                        continue;
                    }
                    bufferStr.append(byteToBinaryString((byte)ch));
                    byteCounter++;
                }
                i++;
            }
//            System.out.println(imageToBinaryString);
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        return imageToBinaryString.toString();
    }

    private static String byteToBinaryString(int value) {
        StringBuilder result = new StringBuilder(8);
        for(int i = 0; i < 8; ++i) {
            result.append((value & 1) == 1 ? '0' : '1');
            value >>>= 1;
        }
        return result.reverse().toString();
    }

    private static void fillMapWithColors(Map<String, Supplier<Attribute>> methodMap) {
        // Добавляем ссылки на методы
        methodMap.put("BLUE", Attribute::BLUE_BACK);
        methodMap.put("BLACK", Attribute::BLACK_BACK);
        methodMap.put("BRIGHT_BLACK", Attribute::BRIGHT_BLACK_BACK);
        methodMap.put("BRIGHT_BLUE", Attribute::BRIGHT_BLUE_BACK);
        methodMap.put("BRIGHT_CYAN", Attribute::BRIGHT_CYAN_BACK);
        methodMap.put("BRIGHT_GREEN", Attribute::BRIGHT_GREEN_BACK);
        methodMap.put("BRIGHT_MAGENTA", Attribute::BRIGHT_MAGENTA_BACK);
        methodMap.put("BRIGHT_RED", Attribute::BRIGHT_RED_BACK);
        methodMap.put("BRIGHT_WHITE", Attribute::BRIGHT_WHITE_BACK);
        methodMap.put("BRIGHT_YELLOW", Attribute::BRIGHT_YELLOW_BACK);
        methodMap.put("CYAN", Attribute::CYAN_BACK);
        methodMap.put("GREEN", Attribute::GREEN_BACK);
        methodMap.put("MAGENTA", Attribute::MAGENTA_BACK);
        methodMap.put("RED", Attribute::RED_BACK);
        methodMap.put("WHITE", Attribute::WHITE_BACK);
        methodMap.put("YELLOW", Attribute::YELLOW_BACK);
    }
}