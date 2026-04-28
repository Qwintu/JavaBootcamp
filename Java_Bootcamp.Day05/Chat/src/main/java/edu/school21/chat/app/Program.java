package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.NotFoundException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;
import edu.school21.chat.exceptions.NotSavedSubEntityException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.*;

public class Program {
    public static void main( String[] args ) {
        try(HikariDataSource dataSource = initDataSource()) {
            MessagesRepository messageRepo = new MessagesRepositoryJdbcImpl(dataSource);
            UsersRepository usersRepo = new UsersRepositoryJdbcImpl(dataSource);

//            System.out.println("Testing FindMsgById func");
//            testFindMsgById(messageRepo);
//            System.out.println("end of testing");
//
//            System.out.println("Testing Save func");
//            testSaveMsg(messageRepo);
//            System.out.println("end of testing");

//            System.out.println("Testing Update func");
//            testUpdateMsg(messageRepo);
//            System.out.println("end of testing");

            System.out.println("Testing Update func");
            testFindAll(usersRepo, 0, 40);
            System.out.println("end of testing");

        } catch (com.zaxxer.hikari.pool.HikariPool.PoolInitializationException e) {
            System.err.println("Ошибка инициализации пула соединений: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static HikariDataSource initDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl( "jdbc:postgresql://localhost:5432/chat_db" );
        config.setUsername( "postgres_user" );
        config.setPassword( "csFlj5r8o" );
        return new HikariDataSource(config);
    }


    private static void testFindMsgById(MessagesRepository messageRepo) {
        Optional<Message> message;
        try(Scanner scanner = new Scanner(System.in)) {
            Long messageId = readMessageId(scanner);
            message = messageRepo.findById(messageId);
            if(message.isPresent()){
                System.out.println(message.get());
            } else {
                System.out.println("Message not found");
            }
        } catch (SQLException e){
            System.out.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static long readMessageId(Scanner scanner) {
        System.out.print("Enter message ID: ");
        while (!scanner.hasNextLong()) {
            System.out.print("Wrong ID\nEnter message ID: ");
            scanner.next();
        }
        return scanner.nextLong();
    }

    private static void testSaveMsg(MessagesRepository messageRepo) {
        User creator = new User(13L, "user", "user", new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(12L, "room", creator, new ArrayList<>());
        Message message = new Message(null, creator, room, "Hello!_3", Instant.now());
        try {
            messageRepo.save(message);
        } catch (NotSavedSubEntityException e) {
            System.out.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(message.getId()); // ex. id == 11
    }

//    private static void testUpdateMsg(MessagesRepository messageRepo) {
//        try {
//            Optional<Message> messageOptional = messageRepo.findById(11L);
//            if (messageOptional.isPresent()) {
//                Message message = messageOptional.get();
//                message.setText("Bye / Hi Alice!");
////                message.setUpdated_at();
//                messageRepo.update(message);
//            } else {
//                throw new NotFoundException("message not found");
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL error: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//    }

    private static void testUpdateMsg(MessagesRepository messageRepo) throws SQLException {
        Long massageNo = 2L;
        Message message = messageRepo.findById(massageNo).orElseThrow(() -> new NotFoundException("Message not found #" + massageNo));
        message.setText("Bye / Hi Alice!");
        messageRepo.update(message);
    }


    private static void testFindAll(UsersRepository usersRepo, int page, int size) throws SQLException {
        List<User> allUsers = usersRepo.findAll(page, size);
        System.out.println(allUsers);
    }
}
