interface UsersList {
    void addUser(User user);
    User getUserById(int userId) throws UserNotFoundException;
    User getUserByIndx(int userIndx) throws UserNotFoundException;
    int getUsersQnty();
}
