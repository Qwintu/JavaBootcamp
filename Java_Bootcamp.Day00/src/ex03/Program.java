import java.util.Scanner;

public class Program
{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String output_str = new String();
        String week_prev = "Week 0";
        int test_res_min = 10;

        while (true) {
            String input_str = input.nextLine();
            if(input_str.equals("42")) break;
            if (input_str.startsWith("Week")){
                if(input_str.compareTo(week_prev) != 1) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                };
            output_str = output_str.concat(input_str);
            week_prev = input_str;
            } else {
                for(int i = 0; i < 10; i++){
                    if(i%2 != 0) continue;
                    int test_res = input_str.charAt(i) - 48;
                    if(test_res < test_res_min) test_res_min = test_res;
                }
                output_str = output_str + " " + "=".repeat(test_res_min) + ">\n";
            }
            test_res_min = 10;
        }
        System.out.println(output_str);
    }
}
//Week 1
//4 5 2 4 2
//Week 2
//7 7 7 7 6
//Week 3
//4 3 4 9 8
//Week 4
//9 9 4 6 7
//42
