public class UserNotFoundException extends Exception {
    private int indx;
    UserNotFoundException(String message, int indx) {
        super(message);
        this.indx = indx;
    }
    public int getIndx() {return indx;}
}