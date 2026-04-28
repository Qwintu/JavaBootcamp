public class UserIdsGenerator{
    private final static UserIdsGenerator usrIdGen = new UserIdsGenerator();
    private static int usrCount = 0;
    UserIdsGenerator(){};
    public static UserIdsGenerator getInstance() {return usrIdGen;};
    public int generateId() {
        UserIdsGenerator.usrCount++;
        return UserIdsGenerator.usrCount;
    };
}
