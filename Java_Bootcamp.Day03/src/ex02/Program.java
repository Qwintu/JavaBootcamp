public class Program {
    private static int arraySize;
    private static int threadsCount;
    private static int maxVal = 1000;
    private static int minVal = -1000;

    public static void main(String[] args) {
        int numbersOfItemsForAllThreads = 0;
        int numbersOfItemsForLastThread = 0;
        if (args.length != 2 || !args[0].startsWith("--arraySize=") || !args[1].startsWith("--threadsCount=")) {
            System.out.println("Instruction: java Program --arraySize=<number> --threadsCount=<number>");
            return;
        }
        arraySize = Integer.parseInt (args[0].split("=")[1]);
        threadsCount = Integer.parseInt (args[1].split("=")[1]);
        if (arraySize <= 0 || arraySize > 2000000 || threadsCount <= 0 || threadsCount > arraySize) {
            System.out.println("Invalid parameters. Make sure that the array value is greater than zero and less than 2 000 000, and that the number of threads is greater than zero and does not exceed the size of the array.");
            return;
        }

        if(arraySize % threadsCount == 0) {
            numbersOfItemsForAllThreads = arraySize / threadsCount;
            if(threadsCount != 1){
                numbersOfItemsForLastThread =numbersOfItemsForAllThreads;
            }
        } else {
            numbersOfItemsForAllThreads = (arraySize - (arraySize % (threadsCount))) / (threadsCount);
                numbersOfItemsForLastThread = numbersOfItemsForAllThreads + arraySize % (threadsCount);
        }

        int[] numsArray = new int[arraySize];
        fillArrayBtRandomInt(numsArray);

        TotalArraySum totalArraySum = new TotalArraySum();

        for (int i = 0; i < threadsCount - 1; i++){
            int startIndx = i * numbersOfItemsForAllThreads;
            int endIndx = startIndx + numbersOfItemsForAllThreads - 1;
            Thread t = new Thread(new Adder(numsArray, startIndx, endIndx, totalArraySum));
            t.start();
            try{
                t.join();
            }
            catch(InterruptedException e){
                System.out.printf("%s has been interrupted", t.getName());
            }
        }

        if(threadsCount == 1) numbersOfItemsForLastThread = numbersOfItemsForAllThreads;
        Thread lastThread = new Thread(new Adder(numsArray, (numsArray.length - numbersOfItemsForLastThread), (numsArray.length-1), totalArraySum));
        lastThread.start();
        try{
            lastThread.join();
        }
        catch(InterruptedException e){
            System.out.printf("%s has been interrupted", lastThread.getName());
        }

        int arrSum = calcArraySum(numsArray);
        System.out.println("Sum: " + arrSum);
        totalArraySum.printSum();
    }


    static void fillArrayBtRandomInt(int[] numsArray){
        for (int i = 0; i < numsArray.length; i++) {
            numsArray[i] = getRandomInt(minVal, maxVal);
        }
//        for (int i : numsArray){
//             System.out.print(i + " ");
//        }
    }

    static int getRandomInt(int minVal, int maxVal){
        int diapason = maxVal + Math.abs(minVal) + 1;
        return  (int) (Math.random() * diapason) - Math.abs(minVal);
    }

    static int calcArraySum(int[] numsArray) {
        int sum = 0;
        for (int i : numsArray) {
            sum += i;
        }
        return  sum;
    }
}

class TotalArraySum{
    int sum = 0;
    public void printSum() {
        System.out.println("Sum by threads: " + sum);
    }
}