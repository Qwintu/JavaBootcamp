import java.util.UUID;

interface TransactionsList {
    void addTransaction(Transaction transaction);
    void delTransactionById(UUID uuid) throws TransactionNotFoundException;
    Transaction findTransactionByUuid(UUID uuid) throws TransactionNotFoundException;
    Transaction[] toArray();
}
