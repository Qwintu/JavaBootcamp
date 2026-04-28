package edu.school21.printer.logic;
import java.io.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.FileAlreadyExistsException;
public class BmpPrinter {
    private BmpPrinter() {};
    public static void printBmp(char blackCh, char whiteCh, File file){
        if(!file.isFile()){
            System.out.println("read file: file not found " + file.toString());
            return;
        }

        try(FileInputStream readFile = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(readFile)){
            int ch;
            int i = 0;
            int j = 1;
            final int rowLenInByts = 2;
//            final int rowLenInByts = 22;
            int byteCounter = 0;
            int byteShiftToPixInfo = 61;
            int flag = 1;
            StringBuilder imageToChar = new StringBuilder(256);
            StringBuilder bufferStr = new StringBuilder(256);
            while((ch = bis.read()) != -1){ // Read one byte at a time
                if(i >byteShiftToPixInfo) {
                    // конец строки пикселей это два байта 00 00, их нужно пропускать при выводе и заменить один на \n
                    if(byteCounter == rowLenInByts || flag < 0) {
                        if(flag > 0) {
                            bufferStr.append("\n");
                            imageToChar.insert(0, bufferStr);
                            bufferStr = new StringBuilder();
                        }
                        byteCounter = 0;
                        i++;
                        j++;
                        flag *= -1;
                        continue;
                    }
//                    bufferStr.insert(0, byteToBinaryString((byte)ch, whiteCh, blackCh));
                    bufferStr.append(byteToBinaryString((byte)ch, whiteCh, blackCh));
                    j++;
                    byteCounter++;
                }
                i++;
            }
            System.out.println(imageToChar);
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private static String byteToBinaryString(int value, char whiteCh, char blackCh) {
        StringBuilder result = new StringBuilder(8);
        for(int i = 0; i < 8; ++i) {
            result.append((value & 1) == 1 ? whiteCh : blackCh);
            value >>>= 1;
        }
        return result.reverse().toString();
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
}