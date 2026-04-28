import java.util.Scanner;
import java.lang.Math;
public class Program
{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        if(number <= 1) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        };
        int iterations_counter = 0;
        int max_devidor = (int)Math.sqrt(number);
        int devidor = 2;
        boolean isPrime = true;
        for(int i = 0; devidor <= max_devidor; i++){
            iterations_counter++;
            if(number%devidor == 0) {
                isPrime = false;
                break;
            }
            devidor++;
        };

        System.out.println(isPrime + " " + iterations_counter);
        System.exit(0);
    }
}