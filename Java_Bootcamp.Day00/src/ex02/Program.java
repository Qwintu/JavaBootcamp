import java.util.Scanner;

public class Program
{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int coffee_counter = 0;

        while (true) {
            int number = input.nextInt();
            if(number == 42) break;
            int dig_sum = 0;
            while (number > 9) {
                dig_sum += number%10;
                number = number/10;
            }
            dig_sum += number;

            int max_devidor = (int)Math.sqrt(dig_sum);
            int devidor = 2;
            boolean isPrime = true;
            for(int i = 0; devidor <= max_devidor; i++){
                if(dig_sum%devidor == 0) {
                    isPrime = false;
                    break;
                }
                devidor++;
            };
            if(isPrime) coffee_counter++;
        }

        System.out.println("Count of coffee-request – " + coffee_counter);
        System.exit(0);
    }
}
