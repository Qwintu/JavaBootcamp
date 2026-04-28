//import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class Program {
    private static int loopCounter;
    static String lastWord = "start";
    public static void main(String /*@NotNull*/ [] args) {
        if (args.length != 1 || args[0].indexOf('=') == -1 /*|| !args[0].startsWith("--count=")*/) {
            System.out.println("\"Instruction: java Program --count=<number>\"");
            System.exit(-1);
        }
        loopCounter = Integer.parseInt (args[0].split("=")[1]);

        LinkedList<String> bufferQue = new LinkedList<>();

        ProducerName printEgg = new ProducerName("Egg", loopCounter, bufferQue);
        ProducerName printHen = new ProducerName("Hen", loopCounter, bufferQue);
        ConsumerPrint printList = new ConsumerPrint(bufferQue);

        Thread threadEgg = new Thread(printEgg);
        Thread threadHen = new Thread(printHen);
        Thread threadPrint = new Thread(printList);
        threadEgg.start();
        threadHen.start();
        threadPrint.start();

        try{
            threadEgg.join();
            threadHen.join();
//            threadPrint.join();
        } catch(InterruptedException e) {
            System.out.printf("Thread has been interrupted");
//            System.out.printf("%s has been interrupted", t.getName());
        }

        // убить consumer
//        Thread.getAllStackTraces().keySet().forEach(t -> System.out.println(t.getName()));
        printList.shutdown();

        System.out.println("end");
    }

    static void writeLastWord(String newLastWord) {
        lastWord = newLastWord;
    }
}

