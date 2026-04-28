package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.NotFoundException;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{
    private final HikariDataSource ds;

    public MessagesRepositoryJdbcImpl(HikariDataSource dataSource){
        this.ds = dataSource;
    }

    private static final String FIND_MESSAGE_SQL = "SELECT * FROM messages WHERE id = ?";
    private static final String FIND_USER_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_CHATROOM_SQL = "SELECT c.id, c.name, c.owner_id, u.login as owner_login FROM chatrooms c \n" +
            "left join users u ON c.owner_id = u.id WHERE c.id = ?;";
    private static final String SAVE_MASSAGE = "INSERT INTO messages (author_id, chatroom_id, text, created_at) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_MASSAGE = "UPDATE messages SET text = ?, updated_at= NOW() WHERE id = ?;";

    public Optional<Message> findById(Long id) throws SQLException {
        Optional<Message> optMsg = Optional.empty();
        try(Connection connection = ds.getConnection(); PreparedStatement ps  = connection.prepareStatement(FIND_MESSAGE_SQL);) {
            ps.setLong(1, id);
            ResultSet rs_1 = ps.executeQuery();
            if(rs_1.next()){
                Long author_id = rs_1.getLong("author_id");
                Long chatroom_id = rs_1.getLong("chatroom_id");
                String msgText = rs_1.getString("text");

                User author = findUserById(author_id, connection);
                Chatroom room = findChatroomById(chatroom_id, connection);
                Message msg = new Message(id, author, room, msgText);
                optMsg = Optional.of(msg);
            }
        }
        return optMsg;
    }

    public void save(Message message) throws NotSavedSubEntityException {
        if (message == null || message.getAuthor() == null || message.getRoom() == null || message.getCreated_at() == null) {
            throw new IllegalArgumentException("Message, author and room must not be null");
        }

        try(Connection connection = ds.getConnection(); PreparedStatement ps = connection.prepareStatement(SAVE_MASSAGE, Statement.RETURN_GENERATED_KEYS)){
            Long authorId = message.getAuthor().getId();
            Long roomId = message.getRoom().getId();

            // проверка корректности authorId и roomId
            if(!existsAuthorById(authorId, connection)) {
                throw new NotSavedSubEntityException("User not found. User id: " + authorId);
            }
            if(!existsChatroomById(roomId, connection)) {
                throw new NotSavedSubEntityException("Chatroom not found. Chatroom id: " + roomId);
            }

            ps.setLong(1, authorId);
            ps.setLong(2, roomId);
            ps.setString(3, message.getText());
            ps.setObject(4, Timestamp.from(message.getCreated_at()));

            int rowsUpdated = ps.executeUpdate();
            if(rowsUpdated == 0) {
                throw new NotSavedSubEntityException("Insert returned 0 rows — message not saved");
            }
            try(ResultSet keys = ps.getGeneratedKeys()) {
                if(keys.next()){
                    Long newMessageId = keys.getLong(1);
                    message.setId(newMessageId);
                } else {
                    throw new NotSavedSubEntityException("Generated key not returned — message saved but ID not assigned");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error while saving message", e);
        }
    }


    public void update(Message message) {
        if(message == null || message.getId() == null) {
            throw new IllegalArgumentException("Message or id must not be null");
        }

        try(Connection connection = ds.getConnection(); PreparedStatement ps = connection.prepareStatement(UPDATE_MASSAGE)){
            ps.setString(1, message.getText());
//            ps.setObject(2, Timestamp.from(message.getCreated_at()));
            ps.setLong(2, message.getId());

            int rowsUpdated = ps.executeUpdate();
            if(rowsUpdated == 0) {
                throw new NotFoundException("Message not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error while updating message", e);
        }
    }


    private User findUserById (Long userId, Connection connection) throws SQLException {
        User user;
        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_SQL);) {
            statement.setLong(1, userId);
            try (ResultSet resSet = statement.executeQuery();) {
                if(resSet.next()) {
                    user = new User(resSet.getLong("id"),
                                    resSet.getString("login"),
                                    resSet.getString("password"));
                } else {
                    throw new SQLException("User not found with id=" + userId);
                }
            }
        }
        return user;
    }


    private Chatroom findChatroomById (Long chatroomId, Connection connection) throws SQLException{
        Chatroom chatroom;
        try(PreparedStatement statement = connection.prepareStatement(FIND_CHATROOM_SQL)) {
            statement.setLong(1, chatroomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    User owner = new User(resultSet.getLong("owner_id"), resultSet.getString("owner_login"), null);
                    chatroom = new Chatroom(resultSet.getLong("id"),
                                            resultSet.getString("name"),
                                            owner);
                } else {
                    throw new SQLException("Chatroom not found with id=" + chatroomId);
                }
            }
        }
        return chatroom;
    }

    private boolean existsAuthorById(Long authorId, Connection connection) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_CHATROOM_SQL)) {
            statement.setLong(1, authorId);
            try(ResultSet resSet = statement.executeQuery()) {
                return resSet.next();
            }
        }
    }

    private boolean existsChatroomById(Long authorId, Connection connection) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(FIND_CHATROOM_SQL)) {
            statement.setLong(1, authorId);
            try(ResultSet resSet = statement.executeQuery()) {
                return resSet.next();
            }
        }
    }

}
