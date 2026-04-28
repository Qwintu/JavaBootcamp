package edu.school21.printer.app;

import java.io.File;
import edu.school21.printer.logic.BmpPrinter;

public class Program {
    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Usage: java edu.school21.printer.app.Program <white> <black> <image>");
            return;
        }

        char blackCh = args[1].charAt(0);
        char whiteCh = args[0].charAt(0);
        
        File file = new File(args[2]);

        BmpPrinter.printBmp(blackCh, whiteCh, file);
        System.out.println("\n" + file.getName() + " in HEX");
        BmpPrinter.printHexFile(file);
    }
}
