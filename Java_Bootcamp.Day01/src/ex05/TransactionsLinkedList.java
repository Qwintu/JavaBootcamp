import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private int size = 0;
    Entry first;
    Entry last;

    class Entry{
        Transaction data;
        Entry prevTransaction;
        Entry nextTransaction;
        Entry(Transaction data) {
            this.data = data;
            this.prevTransaction = null;
            this.nextTransaction = null;
        }
    }
    
    TransactionsLinkedList(){}
    
    public void addTransaction(Transaction transaction){
        Entry newEntry = new Entry(transaction);
        if (size == 0) {
            first = last = newEntry;
        } else {
           newEntry.prevTransaction = last;
           last.nextTransaction = newEntry;
           last = newEntry;
        }
        size++;
    }


    public void delTransactionById(UUID uuid) throws TransactionNotFoundException{
        Entry curTransn = first;
        while (curTransn !=null) {
            if(curTransn.data.getUuid().equals(uuid)) {
                removeEntry(curTransn);
                return;
            }
            curTransn = curTransn.nextTransaction;
        }
        throw new TransactionNotFoundException("Error: Transaction not found", uuid);
    }

    public Transaction findTransactionByUuid(UUID uuid) throws TransactionNotFoundException{
        Entry curTransn = first;
        while (curTransn !=null) {
            if(curTransn.data.getUuid().equals(uuid)) {
                return curTransn.data;
            }
            curTransn = curTransn.nextTransaction;
        }
        throw new TransactionNotFoundException("Error: Transaction not found", uuid);
    }


    private void removeEntry(Entry curTransn) {
        size--;
        if(curTransn == first) {
            first = curTransn.nextTransaction;
            if(curTransn.nextTransaction != null) curTransn.nextTransaction.prevTransaction = null;
            curTransn.nextTransaction = null;
        } else if(curTransn == last) {
            last = curTransn.prevTransaction;
            curTransn.prevTransaction.nextTransaction = null;
            curTransn.prevTransaction = null;
        } else {
            curTransn.nextTransaction.prevTransaction = curTransn.prevTransaction;
            curTransn.prevTransaction.nextTransaction = curTransn.nextTransaction;
            curTransn.nextTransaction = null;
            curTransn.prevTransaction = null;
        }
        
    }

    public Transaction[] toArray(){
        Transaction[] transactionArr= new Transaction[size];
        Entry curTransn = first;
        for (int i = 0; i < size; i++) {
            transactionArr[i] = curTransn.data;
            curTransn = curTransn.nextTransaction;
        } 
        return transactionArr;
    }

    public int size() {return size;}
}