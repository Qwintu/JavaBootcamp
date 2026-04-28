import java.util.UUID;

public class User {
    private final int userId;
    private String userName;
    private double balance;
    private TransactionsList userTransactions;

    User(String name, double balance) throws NegativeBalanceUserException {
        setBalance(balance);
        this.userId = UserIdsGenerator.getInstance().generateId();
        userName = name;
        userTransactions = new TransactionsLinkedList();
    }

    private void setBalance(double balance) throws NegativeBalanceUserException{
        if(balance < 0){
            throw new NegativeBalanceUserException("Error: The user's balance must be positive.");
//            System.out.println("Балаанс пользователя не может быть отрицательным.");
//            System.exit(-1);
        }
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction){
        userTransactions.addTransaction(transaction);
    }

    public void removeTransaction(UUID uuid){
        try{
            userTransactions.delTransactionById(uuid);
        } catch(TransactionNotFoundException excp) {
            System.out.println(excp.getMessage() + "; " + "Transaction ID: " + excp.getUuid());
        }
    }

    public Transaction[] getTransactionsArr() {
        return userTransactions.toArray();
    }

    // Нужно переделать эти два метода
    public void outgoing_payment(User Receiver, double payment_amount){
        if(payment_amount > 0){
            System.out.println("Ошибка: Сумма транзакции должна быть отрицательной.");
            System.exit(-1);
        }
        if(payment_amount <= this.balance){
            this.balance += payment_amount;
        } else {
            System.out.println("Ошибка: У пользователя не не достаточно средств.");
            System.exit(-1);
        }
    }

    public void incoming_payment(User Sender, double payment_amount){
        if(payment_amount < 0){
            System.out.println("Ошибка: Сумма транзакции должна быть положительной.");
            System.exit(-1);
        }
        this.balance += payment_amount;
    }

    public TransactionsList getUserTransactionslist(){
        return  userTransactions;
    }
    public double getBalance() {return balance;}

    public String getUserName(){return userName;}
    
    public int getUserId(){return userId;}

    @Override
    public String toString() {
        return "User {" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                '}';
    }
}