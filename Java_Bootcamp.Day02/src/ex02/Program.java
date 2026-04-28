import java.io.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;

public class Program
{
    private static File currentFolder;
	public static void main(String[] args) {
        if (args.length != 1 || args[0].indexOf('=') == -1) {
            System.out.println("Input current folder");
            System.exit(-1);
        }
        currentFolder = new File(args[0].split("=")[1]);

        System.out.print("-> ");
        Scanner input = new Scanner(System.in);
        String inputCommand;
        Boolean endFlag = true;
        while (endFlag) {
            String[] inputStringArr = input.nextLine().split(" ");

            inputCommand = inputStringArr[0];
            switch (inputCommand) {
                case "ls":
                    printFileStruct(currentFolder);
                    break;
                case "mv":
                    try {
                        mvFile(inputStringArr);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "cd":
                    try {
                        changeDir(inputStringArr);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "read":
                    try {
                        readFile(inputStringArr);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "exit":
                    endFlag = false;
                    break;
                default:
                    System.out.println("Enter command");
                    break;
            }
            if (endFlag) System.out.print("-> ");
        }
    }

    private static void printFileStruct(File currentFolder) {
        for(File item : currentFolder.listFiles()){
            if (item.isFile()) {
                System.out.println(item.getName() + " " + item.length() / 1024 + "KB");
            } else {
                System.out.println(item.getName() + " " + getFolderSize(item) / 1024 + "KB");
            }
        }
    }

    private static long getFolderSize(File dir) {
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += file.length();
                size += getFolderSize(file);
            }
        }
        return size;
    }

    private static void mvFile(String[] inputStringArr) throws IOException {
        Path sourcePath = createNewPath(inputStringArr[1]);
        File sourceFile = new File(sourcePath.toString());
        if(!sourceFile.isFile()) throw new IOException("mv: file not found: " + sourcePath.toString());  // проверяем существует ли файл
        Path distPath = createNewPath(inputStringArr[2]);
        File distFile = new File(distPath.toString());

        if(Files.exists(distPath) && distFile.isDirectory()) {
            distPath = distPath.resolve(sourcePath.getFileName());
        } else if (Files.exists(distPath) && distFile.isFile()) {
            throw new IOException("mv: File is already exists: " + distPath.toString());
        } else {
//            if (Files.exists(distPath.getParent()) && isDirectory(distPath.getParent())){
//                // do noting
//            } else if (!Files.exists(distPath.getParent()) {
//                throw new IOException("mv: No such file or directory: " + distPath.toString());
//            } else if (Files.exists(distPath.getParent()) {
//                distPath = distPath.resolve(sourcePath.getFileName())
//            }

            if (!Files.exists(distPath.getParent())) throw new IOException("mv: No such file or directory: " + distPath.toString());
        }


        try {
            Files.move(sourcePath, distPath);  // this is wrong!
        } catch (FileAlreadyExistsException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void changeDir(String[] inputStringArr) throws IOException {
        if(inputStringArr[1].charAt(0) == '/'){
            currentFolder = new File(inputStringArr[1]);
        } else {
            Path nPath = createNewPath(inputStringArr[1]);
            if(Files.notExists(nPath)) throw new IOException("cd: no such file or directory: " + nPath.toString());  // проверяем существует ли новый путь
            currentFolder = new File(nPath.toString());
        }
    }

    private static void readFile(String[] inputStringArr) throws IOException {
        Path filePath = createNewPath(inputStringArr[1]);
        File file = new File(filePath.toString());

        if(file.isFile()){
            try(FileInputStream readText = new FileInputStream(file)) {
                try(BufferedInputStream bis = new BufferedInputStream(readText)){
                    int ch;;
                    StringBuilder word = new StringBuilder(3);
                    while((ch = bis.read())!=-1){
                        word.append((char)ch);
                    }
                    System.out.println(word.toString());
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            } catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            throw new IOException("read file: file not found: " + filePath.toString());
        }
    }

    private static Path createNewPath(String distPath) throws IOException {
        // correct version
//        Path newPath1 = Paths.get(currentFolder.getCanonicalPath()).resolve(distPath);
//        System.out.println("NNN " + newPath1.toString());

        StringBuilder newPath = new StringBuilder(currentFolder.getCanonicalPath());
        if(newPath.charAt(newPath.length() - 1) != '/') newPath.append('/');
        newPath = newPath.append(distPath);
        Path nPath = Paths.get(newPath.toString());
        Path normalizedPath = nPath.normalize();
        return normalizedPath;
    }
}
