// import java.util.ArrayList;

public class UsersArrayList implements UsersList {
    // private ArrayList<User> usersList;
    private User[] usersList;
    private int usersInList = 0;

    UsersArrayList(){
        // usersList = new ArrayList<User>(10);
        usersList = new User[2];
    }
    
    @Override
    public void addUser(User user){
        if(usersInList < usersList.length) {
            usersList[usersInList] = user;
            usersInList++;
        } else {
            usersList = increaseUsrsLst(usersList, usersList.length * 2);
            usersList[usersInList] = user;
            usersInList++;
        }
    }
    private User[] increaseUsrsLst(User[] usersList, int newLength) {
        User[] newUsrsLst = new User[newLength];
        System.arraycopy(usersList, 0, newUsrsLst, 0, usersList.length);
        return newUsrsLst;
    }

    @Override
    public User getUserById(int userId) throws UserNotFoundException{
        // for(User cur_user : usersList) {
        User curUser;
        for(int i = 0; i < usersInList; i++) {
            curUser = usersList[i];
            if(curUser.getUserId() == userId) {
                return curUser;
            }
        }
        throw new UserNotFoundException("Error: User not found", userId);
    }
    
    @Override
    public User getUserByIndx(int userIndx) throws UserNotFoundException{
        if(userIndx > usersInList || userIndx < 0){
            throw new UserNotFoundException("Error: User not found", userIndx);
        }
        return usersList[userIndx];
    }

    @Override
    public int getUsersQnty(){
        return usersInList;
    }
}