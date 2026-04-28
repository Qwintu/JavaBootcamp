import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Iterator;

public class Program {
    private static Map<String, Integer> firstText = new HashMap<>();
    private static Map<String, Integer> secondText = new HashMap<>();
    private static SortedSet<String> dictionary = new TreeSet<>();
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Please specify two files");
            System.exit(-1);
        }

        int fileNumbr = 1;
        for(String filePath : args) {
            try(FileInputStream readText = new FileInputStream(filePath)) {
                try(BufferedInputStream bis = new BufferedInputStream(readText)){
                    int ch;
                    int prevCh = -1;
                    StringBuilder word = new StringBuilder(3);
                    while((ch = bis.read())!=-1){
                        if(checkChar(ch)){
                            word.append((char)ch);
                            prevCh = ch;
                        } else if (!checkChar(ch) && checkChar(prevCh)) {
                            dictionary.add(word.toString());
                            addWordToMap(fileNumbr, word.toString());
                            word = new StringBuilder(3);
                            prevCh = ch;
                        }
                    }
                    dictionary.add(word.toString());
                    addWordToMap(fileNumbr, word.toString());
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
            fileNumbr++;
        }

        double similarity = calcSimilarity();
        System.out.format("Similarity = %.2f", Math.floor(similarity * 100) / 100);
    }

    private static Boolean checkChar(int ch) {
        return (ch > 64 && ch < 91) || (ch > 96 && ch < 123);
    }

    private static void addWordToMap(int fileNumbr, String word){
        Map<String, Integer> textMap;
        textMap = (fileNumbr == 1) ? firstText : secondText;
        Integer wordCount;
        if((wordCount = textMap.get(word)) != null){
            textMap.put(word, wordCount + 1);
        } else {
            textMap.put(word, 1);
        }
    }

    private static double calcSimilarity() {
        int[] firstArr = new int[dictionary.size()];
        int[] secondArr = new int[dictionary.size()];
        int i = 0;
        String iterVal;
        Integer wordCount;
        Iterator<String> iterator = dictionary.iterator();
        while (iterator.hasNext()) {
            iterVal = iterator.next();
            firstArr[i] = (wordCount = firstText.get(iterVal)) != null ? wordCount : 0;
            secondArr[i] = (wordCount = secondText.get(iterVal)) != null ? wordCount : 0;
            i++;
        }

        int numerator =  0;
        double denominator = 0;
        int firstDen = 0;
        int secondDen = 0;
        for(int k = 0; k < dictionary.size(); k++){
            numerator += firstArr[k] * secondArr[k];
            firstDen += firstArr[k] * firstArr[k];
            secondDen += secondArr[k] * secondArr[k];
        }
        denominator = Math.sqrt(firstDen) * Math.sqrt(secondDen);
        return numerator / denominator;
    }
}