import java.util.UUID;
public class TransactionNotFoundException extends Exception {
    private UUID uuid;
    TransactionNotFoundException(String message, UUID uuid) {
        super(message);
        this.uuid = uuid;
    }
    public UUID getUuid() {return uuid;}
}