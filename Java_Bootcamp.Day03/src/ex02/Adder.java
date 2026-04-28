public class Adder implements Runnable {
    private int[] numArray;
    private int startIndx;
    private int endIndx;
    private TotalArraySum totalArraySum;
    private int thredSum = 0;

    Adder(int[] numArray, int startIndx, int endIndx, TotalArraySum totalArraySum){
        this.numArray = numArray;
        this.startIndx = startIndx;
        this.endIndx = endIndx;
        this.totalArraySum = totalArraySum;
    }

    public void run() {
        for (int i = startIndx; i <= endIndx; i++){
            thredSum += numArray[i];
        }
        synchronized (totalArraySum) {
            totalArraySum.sum += thredSum;
        }
//        System.out.println("Local sum = " + thredSum + " " + Thread.currentThread().getName());
        System.out.printf("%s: from %d to %d sum is %d\n", Thread.currentThread().getName(), startIndx, endIndx, thredSum);
    }
}
