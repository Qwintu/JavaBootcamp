import java.util.Scanner;
import java.util.Arrays;
public class Program
{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

//        Список студентов
        String[] students_name = new String[10];
        for(int i = 0; i < 10; i++){
            String stud_name = input.nextLine();
            if(stud_name.equals(".")) break;
            students_name[i] = stud_name;
        }

        int weeks = 5;
        int days_in_month = 30;
        int day_shift = 1;
        int tot_lesson = 0;
        String[] calendar = new String[days_in_month];
        Arrays.fill(calendar, "");
        for(int i = 0; i < 10; i++){
            String lesson = input.nextLine();
            if(lesson.equals(".")) break;
            String[] schedule_elem = lesson.split(" ");
            fillSchedule(schedule_elem, calendar, weeks, days_in_month, day_shift);
        }

        String[] presence = new String[500];
        for(int i = 0; i < 10; i++){
            String presence = input.nextLine();
            if(presence.equals(".")) break;
            presence[i] = presence;
        }

        System.out.print("          ");
        for(int i = 0; i < days_in_month; i++) {
            if (calendar[i].equals("")) continue;
            String[] schedule_day = calendar[i].split(" ");
            Arrays.sort(schedule_day, 1, schedule_day.length);
            for(int j = 1; j < schedule_day.length; j++) {
                System.out.print(String.format("%s:00 %s %2d|", schedule_day[j], schedule_day[0], i + 1));
//                System.out.print(schedule_day[j] + ":00 " + schedule_day[0]  + "   "+ (i + 1) + "|");
                tot_lesson++;
            }
        }
        System.out.println();
        for(int i = 0; i < 10; i++) {
            if (students_name[i] == null) break;
            System.out.print(String.format("%10s", students_name[i]));
            System.out.println("          |".repeat(tot_lesson));
        }
    }
    public static void fillSchedule (String[] schedule_elem, String[] calendar, int weeks, int days_in_month, int day_shift){
        int day_number = getDeyNumber(schedule_elem[1]);
        int day_indx = 0;
        for(int i = 0; i < weeks; i++){
            day_indx = i * 7 + day_number - day_shift;
            if(day_indx < 0 || day_indx > (days_in_month - 1)) continue;
            if(calendar[day_indx].equals("")) calendar[day_indx] += schedule_elem[1]  + " ";
            calendar[day_indx] += schedule_elem[0]  + " ";
        }
    }
    public static int getDeyNumber(String week_day){
        int day_number = 0;
        switch (week_day) {
            case "MO" : day_number = 0;
                break;
            case "TU" : day_number = 1;
                break;
            case "WE" : day_number = 2;
                break;
            case "TH" : day_number = 3;
                break;
            case "FR" : day_number = 4;
                break;
            case "SA" : day_number = 5;
                break;
            case "SU" : day_number = 6;
                break;
        }
        return day_number;
    }
}

// не сделана посещаемость

//dsf
//gr6h
//giteruf
//456f
//.
//2 MO
//4 FR
//2 FR
//3 WE
//.
