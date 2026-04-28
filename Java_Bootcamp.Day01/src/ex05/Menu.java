import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.UUID;


public class Menu {
    private boolean isDev;
    private TransactionsService service;

    Menu(boolean isDev, TransactionsService transactionsService) {
        this.isDev = isDev;
        this.service = transactionsService;
    }

    public void show() {
        String wrongMenuItemMsg = "Select one of menu items.\n";
        Scanner input = new Scanner(System.in);
        while (true) {
            startMenu();
            int menuItem = 0;
            System.out.print("-> ");
            try {
                menuItem = input.nextInt();
            } catch (InputMismatchException e) {
                System.err.println(wrongMenuItemMsg);
                input.nextLine();
                continue;
            }

            // обернуть в ошибку ввода
            try {
                switch (menuItem) {
                    case 1:
                        System.out.print("Enter a user name and a balance\n-> ");
                        String userInitialName = input.next();
                        double userInitialBalance = input.nextDouble();
                        try {
                            service.addUser(userInitialName, userInitialBalance);
                            System.out.println("User with id = " + service.getLastUserId() + " is added\n");
                        } catch (NegativeBalanceUserException ex) {
                            System.err.println(ex.getMessage());
                        }
                        break;
                    case 2:
                        System.out.print("Enter a user ID\n-> ");
                        int userId = input.nextInt();
                        try {
                            double userBalance = service.getUserBalance(userId);
                            String userName = service.getUserNameById(userId);
                            System.out.println(userName + " - " + userBalance + "\n");
                        } catch (UserNotFoundException e) {
                            System.err.println(e.getMessage() + "; " + "User ID: " + e.getIndx());
                        }
                        break;
                    case 3:
                        System.out.print("Enter a sender ID, a recipient ID, and a transfer amount\n-> ");
                        int senderId = input.nextInt();
                        int recipientId = input.nextInt();
                        double transferAmount = input.nextDouble();
                        try {
                            service.makeTransaction(senderId, recipientId, transferAmount);
                        } catch (UserNotFoundException e) {
                            System.err.println(e.getMessage() + "; " + "User ID: " + e.getIndx());
                            break;
                        } catch (SenderHaveNoMoneyException e2) {
                            //                        System.err.println(e2.getMessage());
                            break;
                        }
                        System.out.println("The transfer is completed\n");
                        break;
                    case 4:
                        System.out.print("Enter a user ID\n-> ");
                        int userIdForPrintTrans = input.nextInt();
                        try {
                            Transaction[] usrTrans = service.getUserTransactions(userIdForPrintTrans);
                            printTransactions(usrTrans);
                        } catch (UserNotFoundException e) {
                            System.err.println(e.getMessage() + "; " + "User ID: " + e.getIndx());
                        }
                        break;
                    case 5:
                        if (!isDev) {
                            System.err.println(wrongMenuItemMsg);
                            break;
                        }
                        System.out.print("Enter a user ID and a transfer ID\n-> ");
                        int userIdForRemTrans = input.nextInt();
                        try {
                            UUID uuidRem = UUID.fromString(input.next());
                            Transaction transaction = service.findTransaction(userIdForRemTrans, uuidRem);
                            service.removeTransaction(userIdForRemTrans, uuidRem);
                            printRemTransMsg(transaction);
                        } catch (UserNotFoundException exp1) {
                            System.err.println(exp1.getMessage() + "; " + "User ID: " + exp1.getIndx());
                        } catch (IllegalArgumentException exp2) {
                            System.err.println(exp2.getMessage());
                        } catch (TransactionNotFoundException exp3) {
                            System.err.println(exp3.getMessage() + "; " + "UUID: " + exp3.getUuid());
                        }

                        break;
                    case 6:
                        if (!isDev) {
                            System.err.println(wrongMenuItemMsg);
                            break;
                        }
                        System.out.print("Check results:\n");
                        Transaction[] unpairedTransactions = service.checkTransactions();
                        printUnpairedTransactions(unpairedTransactions);
                        break;
                    case 7:
                        System.out.println("Execution finished");
                        return;
                    default:
                        System.err.println(wrongMenuItemMsg);
                        input.nextLine();
                        break;
                }
            } catch (InputMismatchException ext) {
                System.err.println("Error: Incorrect input\nTry again\n");
                input.nextLine();
            }
        }
    }

    private void startMenu() {
        System.out.print("""                
                1. Add a user
                2. View user balances
                3. Perform a transfer
                4. View all transactions for a specific user
                """);
        if (isDev) {
            System.out.print("""
                    5. DEV - remove a transfer by ID
                    6. DEV - check transfer validity
                    """);
        }
        System.out.print("""
                7. Finish execution
                """);
    }

    // Может перенести из меню в Service
    private void printTransactions(Transaction[] usrTrans) {
        String prefix;
        String counterparty;
        int counterpartyId;
        double paymentAmount;
        UUID uuid;

        if(usrTrans.length == 0) {
            System.out.println("This user has no transactions");
            return;
        }

        for (Transaction curTr : usrTrans) {
            uuid = curTr.getUuid();
            paymentAmount = curTr.getAmountOfPayment();
            if (curTr.getTypeOfPayment().equals("INCOME")) {
                prefix = "From ";
                counterparty = curTr.getSenderName();
                counterpartyId = curTr.getSenderId();
            } else {
                prefix = "To ";
                counterparty = curTr.getReceiverName();
                counterpartyId = curTr.getReceiverId();
                paymentAmount *= -1;
            }
            System.out.println(prefix + counterparty + "(id = " + counterpartyId + ") " + paymentAmount + " with id = " + uuid);
        }
        System.out.println();
    }

    private void printRemTransMsg(Transaction transaction){
        String prefix;
        String counterparty;
        int counterpartyId;
        double paymentAmount = transaction.getAmountOfPayment();;
        if (transaction.getTypeOfPayment().equals("INCOME")) {
            prefix = "From ";
            counterparty = transaction.getSenderName();
            counterpartyId = transaction.getSenderId();
        } else {
            prefix = "To ";
            counterparty = transaction.getReceiverName();
            counterpartyId = transaction.getReceiverId();
            paymentAmount *= -1;
        }
        System.out.println("Transfer " +prefix + counterparty + "(id = " + counterpartyId + ") " + paymentAmount + " removed\n");
    }

    private void printUnpairedTransactions(Transaction[] unpairedTransactions){
        String direction;
        String userName;
        String counterpartyName;
        int userId;
        int counterpartyId;
        double paymentAmount;
        UUID uuid;

        if(unpairedTransactions.length == 0) {
            System.out.println("No unpaired transactions");
            return;
        }

        for (Transaction curTransaction : unpairedTransactions) {
            uuid = curTransaction.getUuid();
            paymentAmount = curTransaction.getAmountOfPayment();
            if (curTransaction.getTypeOfPayment().equals("INCOME")) {
                direction = "From ";
                userName = curTransaction.getReceiverName();
                userId = curTransaction.getReceiverId();
                counterpartyName = curTransaction.getSenderName();
                counterpartyId = curTransaction.getSenderId();
            } else {
                direction = "To ";
                userName = curTransaction.getSenderName();
                userId = curTransaction.getSenderId();
                counterpartyName = curTransaction.getReceiverName();
                counterpartyId = curTransaction.getReceiverId();
                paymentAmount *= -1;
            }
            System.out.println(userName + "(id = " + userId + ") has an unacknowledged transfer id = " + uuid + " " + direction + " " + counterpartyName + "(id = " + counterpartyId + ") for " + paymentAmount);
        }
        System.out.println();
    };

}