import java.util.LinkedList;
public class ProducerName implements Runnable {
    private String answer;
    private int loopCounter;
    private LinkedList<String> buffer;
    private String lastWord;
    ProducerName(String answer, int loopCounter, LinkedList<String> buffer){
        this.answer = answer;
        this.loopCounter = loopCounter;
        this.buffer = buffer;
    }

    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        synchronized (buffer) {
            for (int i = 0; i < loopCounter; i++) {
                if (/*!buffer.isEmpty() &&*/ Program.lastWord.equals(answer)) {
                    try {
//                        System.out.println("wait " +  Thread.currentThread().getName());
                        buffer.wait();
                        i--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    buffer.addFirst(answer);
                    Program.writeLastWord(answer);
//                    System.out.printf("%d %s\n", i, answer);
//                    System.out.println(Program.lastWord.equals(answer));
                    buffer.notifyAll();
                }
            }
        }
    }
}
