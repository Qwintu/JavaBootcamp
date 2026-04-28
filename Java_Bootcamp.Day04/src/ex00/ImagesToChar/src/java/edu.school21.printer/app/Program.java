package edu.school21.printer.app;

import java.io.*;
import java.io.File;
import edu.school21.printer.logic.BmpPrinter;

public class Program {
    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Usage: java edu.school21.printer.app.Program <white> <black> <image>");
            return;
        }


        char whiteCh = args[0].charAt(0);
        char blackCh = args[1].charAt(0);;
//        String fileName =
        File file = new File(args[2]);

        BmpPrinter.printBmp('8', '.', file);
        System.out.println("\n" + file.getName() + " in HEX");
        BmpPrinter.printHexFile(file);
    }
}
