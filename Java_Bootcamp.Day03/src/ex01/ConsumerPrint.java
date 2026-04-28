import java.util.LinkedList;


public class ConsumerPrint implements Runnable{
    private LinkedList<String> buffer;
    boolean isActive = true;

    ConsumerPrint(LinkedList<String> buffer){
        this.buffer = buffer;
    }

    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        synchronized (buffer) {
            while (isActive) {
                if (buffer.isEmpty()) {
                    try {
//                        System.out.println("wait pr " +  Thread.currentThread().getName());
                        buffer.wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.printf("%s\n", buffer.removeLast());
//                    System.out.println("rr " + Program.lastWord);
                    buffer.notifyAll();
                }
            }
        }
    }
    void shutdown(){
        isActive=false;
    }
}
