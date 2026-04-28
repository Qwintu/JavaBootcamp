public class Program {
    public static void main(String[] args) {
        int loopCounter;
        if (args.length != 1 || args[0].indexOf('=') == -1) {
            System.out.println("Wrong input");
            System.exit(-1);
        }
        loopCounter = Integer.parseInt (args[0].split("=")[1]);

        MyThread threadEgg = new MyThread("Egg", "Egg", loopCounter);
        MyThread threadHen = new MyThread("Hen", "Hen", loopCounter);
        threadEgg.start();
        threadHen.start();
        try{
            threadEgg.join();
            threadHen.join();
        } catch(InterruptedException e) {
            System.out.printf("Thread has been interrupted");
 //           System.out.printf("%s has been interrupted", t.getName());
        }
        for (int i = 0; i < loopCounter; i++){
            System.out.printf("Human\n");
        }
        System.out.println("end");
    }
}

