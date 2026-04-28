import java.util.UUID;

public class TransactionsService {
    private UsersList usersList = new UsersArrayList();

    void addUser(String name, double balance) throws NegativeBalanceUserException{
        User newUser = new User(name, balance);
        usersList.addUser(newUser);
    }
    double getUserBalance(int userId) throws UserNotFoundException{  //в месте вызова проверять на null
        double user_balance;
        user_balance = usersList.getUserById(userId).getBalance();
        return user_balance;
    }
    
    void makeTransaction(int senderId, int receiverId, double payment_amount) throws UserNotFoundException, SenderHaveNoMoneyException{
        User sender = usersList.getUserById(senderId);
        User receiver = usersList.getUserById(receiverId);
        UUID uuid = UUID.randomUUID();
        try {
            Transaction SenderTransaction = new Transaction(uuid, sender, receiver, "OUTCOME", payment_amount);
            Transaction ReceiverTransaction = new Transaction(uuid, sender, receiver, "INCOME", payment_amount);
            sender.addTransaction(SenderTransaction);
            receiver.addTransaction(ReceiverTransaction);
        } catch (SenderHaveNoMoneyException ex) {
            System.err.println(ex.getMessage());
        }
    }
    Transaction[] getUserTransactions(int userId) throws UserNotFoundException{
        User user = usersList.getUserById(userId);
        return user.getTransactionsArr();
    //        return usersList.getUserById(userId).getTransactionsArr();
    }

    void removeTransaction(int userId, UUID uuid) throws UserNotFoundException{
        User user = usersList.getUserById(userId);
        user.removeTransaction(uuid);
    }

    Transaction[] checkTransactions(){
        TransactionsList unpairedTrans = new TransactionsLinkedList();
        try {

            for (int i = 0; i < usersList.getUsersQnty(); i++) {
    //            int transArrLen = usersList.getUserByIndx(i).getTransactionsArr().length;
    //            Transaction[] transactions = new Transaction[transArrLen];
    //            transactions = usersList.getUserByIndx(i).getTransactionsArr();
                int currentUserId = usersList.getUserByIndx(i).getUserId();
                Transaction[] transactions = usersList.getUserByIndx(i).getTransactionsArr();
                for (int j = 0; j < transactions.length; j++){
                    Transaction curTrans = transactions[j];
                    UUID currentUuid = curTrans.getUuid();
                    int receiverId = curTrans.getReceiverId();
                    int senderId = curTrans.getSenderId();
                    int secondUserId = currentUserId == receiverId ? senderId : receiverId;
                    if(!isTransactionInArr(currentUuid, usersList.getUserById(secondUserId).getTransactionsArr())) {
                        unpairedTrans.addTransaction(curTrans);
                    }
                }
            }
        } catch(UserNotFoundException excp) {
            excp.printStackTrace();
        }
        return unpairedTrans.toArray();
    }

    boolean isTransactionInArr(UUID uuid, Transaction[] transactions){
        for (Transaction trans : transactions){
            if(trans.getUuid().equals(uuid)) return true;
        }
        return false;
    }

    Transaction findTransaction(int userId, UUID uuid) throws UserNotFoundException, TransactionNotFoundException{
        return usersList.getUserById(userId).getUserTransactionslist().findTransactionByUuid(uuid);
    }

    int getLastUserId(){       // по идее, так делать нельзя, но конкретно тут можно.
        return usersList.getUsersQnty();
    }

    String getUserNameById(int id) throws UserNotFoundException{
        return usersList.getUserById(id).getUserName();
    }
}

