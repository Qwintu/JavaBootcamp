public class MyThread extends Thread {
    private String answer;
    private int loopCounter;
    MyThread(String name, String answer, int loopCounter){
        super(name);
        this.answer = answer;
        this.loopCounter = loopCounter;
    }

    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        for (int i = 0; i < loopCounter; i++){
            System.out.printf("%d %s\n", i, answer);
        }
    }
}
