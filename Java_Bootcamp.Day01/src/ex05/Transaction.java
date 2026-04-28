import java.util.UUID;
public class Transaction {
    private UUID uuid = UUID.randomUUID();
    private User receiver;
    private User sender;
    private String typeOfPayment;
    private double amountOfPayment;


    Transaction(User sender, User receiver, String typeOfPayment, double amountOfPayment) {
        this.receiver = receiver;
        this.sender = sender;
        this.amountOfPayment = amountOfPayment;
        this.typeOfPayment = typeOfPayment;
        sender.outgoing_payment(receiver, -amountOfPayment);
        receiver.incoming_payment(sender, amountOfPayment);
    }

    Transaction(UUID uuid, User sender, User receiver, String typeOfPayment, double amountOfPayment) throws SenderHaveNoMoneyException{
        this.receiver = receiver;
        this.sender = sender;
        this.amountOfPayment = amountOfPayment;
        this.typeOfPayment = typeOfPayment;
        this.uuid = uuid;
        switch (typeOfPayment){
            case "INCOME":
                receiver.incoming_payment(sender, amountOfPayment);
                break;
            case "OUTCOME":
                if(sender.getBalance() < amountOfPayment)
                    throw new SenderHaveNoMoneyException("Error: Sender does not have enough money");
                sender.outgoing_payment(receiver, -amountOfPayment);
                break;
        }
    }

    public UUID getUuid() {return uuid;}

    public String getReceiverName() {return receiver.getUserName();}
    public String getSenderName() {return sender.getUserName();}


    public int getReceiverId() {return receiver.getUserId();}
    public int getSenderId() {return sender.getUserId();}
    public String getTypeOfPayment() {return typeOfPayment;}
    public double getAmountOfPayment() {return amountOfPayment;}

    @Override
    public String toString(){
        return "Transaction {" +
                "uuid=" + uuid +
                ", sender=" + sender.getUserName() +
                ", receiver=" + receiver.getUserName() +
                ", Amount=" + amountOfPayment +
                ", Type=" + typeOfPayment +
                '}';
    }
}