import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program {
    public static void main(String [] args){

        int numbersOfThreads = Integer.parseInt (args[0].split("=")[1]);
        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            System.out.println("Instruction: java Program --threadsCount=<number>");
            return;
        }
        String pathFileWithUrls = "files_urls.txt";
        LinkedList<URL> downloadableUrls = new LinkedList<>();

        readFileWithUrls(pathFileWithUrls, downloadableUrls);

        FileDownloader downloader = new FileDownloader(numbersOfThreads);
        downloader.downloadFiles(downloadableUrls);
    }

    public static void readFileWithUrls(String filePath, LinkedList<URL> downloadableUrls){
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                URL url = new URL(scanner.nextLine());
                downloadableUrls.add(url);
                System.out.println(url);
            }
            System.out.println();
            scanner.close();
        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
