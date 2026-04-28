import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        boolean isDev = args.length > 0 && args[0].equals("--profile=dev");

        TransactionsService service = new TransactionsService();
        Menu menu = new Menu(isDev, service);
        menu.show();
    }
}