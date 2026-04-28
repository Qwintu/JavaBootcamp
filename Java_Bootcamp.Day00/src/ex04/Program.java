import java.util.Scanner;

public class Program
{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String input_str = input.nextLine();
        int[] char_count = new int[65535];
//        int[] char_count = new int[127];
        int[] char_count_copy = new int[65535];
        int[][] top_ten_simbls = new int[2][10];
        String[][] res_gist = new String[12][10];

        // счетаем количество каждого символа во входной страки и записываем в массив
        for(int i = 0; i < input_str.length(); i++){
            int indx = input_str.charAt(i);
            char_count[indx]++;
        }
        char_count_copy = char_count.clone(); // клонируем массив, чтобы не портить исходные данные

        int last_max_val = 0;
        int char_tmp = 0;

        // за 10 проходов находим 10 макс значений, после этого записываем туда 0
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < char_count_copy.length; j++) {
                if (char_count_copy[j] > last_max_val) {
                    last_max_val = char_count_copy[j];
                    char_tmp = j;
                }
            }
            char_count_copy[char_tmp] = 0;
            top_ten_simbls[0][i] = last_max_val;
            top_ten_simbls[1][i] = char_tmp;
            last_max_val = 0;
            char_tmp = 0;
        }

        // ищем макс и мин значение кол-ва букв
        int max_val = 0;
        int min_val = 1000;
        int k = 0;
        while((k < 10) && (top_ten_simbls[0][k] != 0)){
            if(max_val < top_ten_simbls[0][k]) max_val = top_ten_simbls[0][k];
            if(min_val > top_ten_simbls[0][k]) min_val = top_ten_simbls[0][k];
            k++;
        }

        // считаем высоту и рисуем гистограмму
        double step_of_gist = (max_val - min_val) / 10.0;
        int gist_h = 0;
        for(int i = 0; i < 10; i++){
//            gist_h = (int)Math.round((top_ten_simbls[0][i] - min_val) / step_of_gist);
            gist_h = (int)((top_ten_simbls[0][i] - min_val) / step_of_gist);
            for(int j = 0; j < gist_h; j++){
                int l = 10 - j;
                res_gist[l][i] = "   #";
            }

            int annot_h = 11 - 1 - gist_h;
            String spaces = (top_ten_simbls[0][i] < 10) ?  "   " : "  ";
            res_gist[annot_h][i] = spaces + String.valueOf(top_ten_simbls[0][i]);
            res_gist[11][i] = "   " + String.valueOf((char)(top_ten_simbls[1][i]));
        }

        for(int i = 0; i < 127; i++)
            System.out.print(char_count_copy[i] + " ");
        System.out.print("\n");

        for(int i = 0; i < 10; i++) {
            String spaces2 = (top_ten_simbls[0][i] < 10) ?  "   " : "  ";
            System.out.print(spaces2 + top_ten_simbls[0][i]);
        }
        System.out.print("\n");

        for(int i = 0; i < 10; i++)
            System.out.print("   " + (char)top_ten_simbls[1][i]);
        System.out.print("\n");

        String print_str = new String();
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 10; j++){
                print_str = res_gist[i][j] == null ? "" : res_gist[i][j];
                System.out.print(print_str);
            }
            System.out.print("\n");
        }
    }
}

// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSSSSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDWEWWKFKKDKKDSKAKLSLDKSKALLLLLLLLLLRTRTETWTWWWWWWWWWWOOOOOOO42
