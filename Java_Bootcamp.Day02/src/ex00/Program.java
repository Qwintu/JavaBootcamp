//import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {
    private static Map<String, String> signMap = new HashMap<>();
    private static ArrayList<String> resultTypeList = new ArrayList<>();
    public static void main(String[] args) {
        try(FileInputStream readSig = new FileInputStream("signatures.txt")) {
            int i;
            StringBuilder fileSig = new StringBuilder(10);
            while((i = readSig.read()) != -1) {
                if((char)i == '\n'){
                    addStringToMap(fileSig.toString());
                    fileSig = new StringBuilder(10);
                    continue;
                }
                fileSig.append((char)i);
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println();
        System.out.print("-> ");
        Scanner inputPath = new Scanner(System.in);
        String filePath;
        String fileType;
        while (true) {
            filePath = inputPath.next();
            if(filePath.equals("42")) break;
            fileType = chackFileType(filePath);
            inputPath.nextLine();
            if(!fileType.equals("UNDEFINED")) resultTypeList.add(fileType);
            System.out.print("PROCESSED\n-> ");
        }
        saveToFile();
    }

    private static void addStringToMap(String fileSigStr){
        String[] substrKeyVal = fileSigStr.split(", ");
        String key = substrKeyVal[1].replace(" ", "");
        signMap.put(key, substrKeyVal[0]);
    }

    private static String chackFileType(String filePath) {
        String fileType = "UNDEFINED";
        try(FileInputStream fins = new FileInputStream(filePath)) {
            int b;
            int i = 0;
            StringBuilder hexFirstBytes = new StringBuilder(1);
             while(i < 8 && (b = fins.read()) != -1){
                hexFirstBytes.append(Integer.toHexString(b).toUpperCase());
                fileType = signMap.get(hexFirstBytes.toString());
                if(fileType != null){
                    break;
                }
                i++;
            }
            if(fileType == null) fileType = "UNDEFINED";
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return fileType;
    }

    private static void saveToFile() {
        try(FileOutputStream fostr = new FileOutputStream("result.txt")) {
            // System.out.println("The file has been written");
            for(String fileType : resultTypeList){
                byte[] buffer = fileType.getBytes();
                fostr.write(buffer, 0, buffer.length);
                fostr.write('\n');
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}