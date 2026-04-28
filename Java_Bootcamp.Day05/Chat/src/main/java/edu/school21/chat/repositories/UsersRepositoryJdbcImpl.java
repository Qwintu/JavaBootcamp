package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository{
    private final HikariDataSource ds;
    public UsersRepositoryJdbcImpl(HikariDataSource dataSource){
        this.ds = dataSource;
    }
    private static final String FINDALL_USERS = """
            WITH usrs AS (
                SELECT id AS u_id, login
                FROM users
                ORDER BY id
                LIMIT ?
                OFFSET ?
            )
 
            SELECT
                u.u_id,
                u.login,
                c.id AS chatroom_id,
                'owner' AS relation,
                c.name AS chatroom_name,
                c.owner_id AS chatroom_owner_id,
            	(select login AS chatroom_owner_login from users u where u.id = c.owner_id )
            FROM usrs u
            JOIN chatrooms c ON c.owner_id = u.u_id
            
            UNION ALL
            
            SELECT
                u.u_id,
                u.login,
                uc.chatroom_id,
                'participant',
                c.name AS chatroom_name,
                c.owner_id AS chatroom_owner_id,
            	(select login AS chatroom_owner_login from users u where u.id = c.owner_id )
            FROM usrs u
            JOIN users_chatrooms uc ON uc.user_id = u.u_id
            JOIN chatrooms c ON c.id = uc.chatroom_id
            
            UNION ALL
            
            SELECT
                u.u_id,
                u.login,
                NULL AS chatroom_id,
                'none' AS relation,
                null,
                null,
                NULL
            FROM usrs u
            WHERE NOT EXISTS (
                SELECT 1 FROM chatrooms c WHERE c.owner_id = u.u_id
            )
            AND NOT EXISTS (
                SELECT 1 FROM users_chatrooms uc WHERE uc.user_id = u.u_id
            )
            
            ORDER BY u_id, relation, chatroom_id;
    """;

    public List<User> findAll(int page, int size) throws IllegalArgumentException{
        if(page < 0 || size < 1) {
            throw new IllegalArgumentException("The page number must not be negative or the size must be greater than zero."); // переписать текст ошибки
        }
        HashMap<Long, User> usersById = new HashMap<>();
        try(Connection connection = ds.getConnection(); PreparedStatement ps = connection.prepareStatement(FINDALL_USERS)){
            ps.setInt(1, size);
            ps.setInt(2, page * size);
            ResultSet resSet = ps.executeQuery();
            while (resSet.next()) {
                processRow(resSet, usersById);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error while fetching users", e);
        }
        return new ArrayList<>(usersById.values());
    }

    private void processRow(ResultSet resSet, HashMap usersById) throws SQLException {
        Long userId = resSet.getLong("u_id");
//                Long chatroomId = resSet.getObject("chatroom_id", Long.class);     // Ошибка конвертации
        Integer chatroomIdInt = resSet.getObject("chatroom_id", Integer.class);
        Long chatroomId = (chatroomIdInt != null) ? chatroomIdInt.longValue() : null;

        if(!usersById.containsKey(userId)){
            User newUser = createUser(resSet, userId);
            usersById.put(userId, newUser);
        }
        if(chatroomId != null) {
            addChatroomToUserList(resSet, userId, chatroomId, usersById);
        }
    };

    private User createUser(ResultSet resSet, Long userId) throws SQLException {
        String login =  resSet.getString("login");
        List<Chatroom> listOfCreatedRooms = new ArrayList<>();
        List<Chatroom> socializedRooms = new ArrayList<>();
        return new User(userId, login, null, listOfCreatedRooms, socializedRooms);
    }
    private void addChatroomToUserList(ResultSet resSet, Long userId, Long chatroomId, HashMap<Long, User> allUsersMap) throws SQLException, IllegalStateException {
        String relation =  resSet.getString("relation");
        Chatroom chatroom = extractChatroom(resSet, chatroomId);
        if ("participant".equals(relation)) {
            allUsersMap.get(userId).getSocializedRooms().add(chatroom);
        } else if ("owner".equals(relation)) {
            allUsersMap.get(userId).getListOfCreatedRooms().add(chatroom);
        } else if (!"none".equals(relation)) {
            throw new IllegalStateException("illegal user relation " + relation);
        }
    }
    private Chatroom extractChatroom(ResultSet resSet, Long chatroomId) throws SQLException {
        String chatroomName = resSet.getString("chatroom_name");
        Long ownerId = resSet.getLong("chatroom_owner_id");
        String ownerLogin = resSet.getString("chatroom_owner_login");
        return new Chatroom(chatroomId, chatroomName, new User(ownerId, ownerLogin, null));
    }
}

