# Day 05 – Java bootcamp
## За следующие дни вам предстоит реализовать функциональность чата.

### SQL/JDBC

*_Сегодня вы освоите ключевые механизмы работы с СУБД PostgreSQL через JDBC._*

# Часть I

###  Таблицы и сущности

В чате пользователь сможет создать или выбрать существующую комнату для общения. В каждой комнате может быть несколько пользователей, обменивающихся сообщениями.

Ключевые доменные модели, для которых необходимо реализовать как SQL-таблицы, так и Java-классы, следующие:

- User
    - User ID
    - Login
    - Password
    - List of created rooms
    - List of chatrooms where a user socializes
- Chatroom
    - Chatroom id
    - Chatroom name
    - Chatroom owner
    - List of messages in a chatroom
- Message
    - Message id
    - Message author
    - Message room
    - Message text
    - Message date/time

Создайте файл schema.sql, в котором вы опишете операции CREATE TABLE для создания таблиц в проекте. Также создайте файл data.sql с текстовыми запросами INSERTS (не менее пяти в каждой таблице).

Важно выполнить следующее требование!
Предположим, что сущность «Курс» имеет отношение «один ко многим» с сущностью «Урок». Тогда их объектно-ориентированное отношение должно выглядеть следующим образом:

```java
class Course {
   private Long id;
   private List<Lesson> lessons;// there are numerous lessons in the course
   ...
}
class Lesson {
   private Long id;
   private Course course; // the lesson contains a course it is linked to
   ...
}
```

Дополнительные требования:

-   Для реализации реляционных связей используйте типы связей «один ко многим» и «многие ко многим».
-   Идентификаторы должны быть числовыми.
-   Идентификаторы должны генерироваться СУБД.
-   Методы equals(), hashCode() и toString() должны быть корректно переопределены внутри Java-классов.


# Часть II
### Read/Find

Объект доступа к данным (DAO, репозиторий) — это популярный шаблон проектирования, позволяющий отделить ключевую бизнес-логику от логики обработки данных в приложении.

Предположим, у нас есть интерфейс под названием CoursesRepository, который предоставляет доступ к урокам курса. Этот интерфейс может выглядеть следующим образом:

```java
public interface CoursesRepository {
  Optional<Course> findById(Long id);
  void delete(Course course);
  void save(Course course);
  void update(Course course);

  List<Course> findAll();
}
```

Необходимо реализовать класс MessagesRepository с помощью ОДНОГО `Optional<Message> findById(Long id)`метода и его реализации MessagesRepositoryJdbcImpl.

Этот метод возвращает объект Message, в котором будут указаны автор и чат-комната. В свою очередь, НЕТ НЕОБХОДИМОСТИ указывать дочерние сущности (список чат-комнат, создатель чат-комнаты и т. д.) для автора и чат-комнаты.

Реализованный код необходимо протестировать в классе Program.java. Пример работы программы приведен ниже (результат может отличаться):

```
$ java Program
Enter a message ID
-> 5
Message : {
  id=5,
  author={id=7,login=“user”,password=“user”,createdRooms=null,rooms=null},
  room={id=8,name=“room”,creator=null,messages=null},
  text=“message”,
  dateTime=01/01/01 15:69
}
```

Класс MessagesRepositoryJdbcImpl должен принимать в качестве параметра конструктора интерфейс DataSource из пакета java.sql. Для реализации DataSource используйте библиотеку HikariCP — пул соединений с базой данных.

# Часть III
### Create/Save


Теперь вам нужно реализовать метод save(Message message) для MessagesRepository.

Таким образом, нам необходимо определить следующие подсущности для сохраняемой сущности — автора сообщения и чата. Также важно присвоить чату и автору идентификаторы, существующие в базе данных.

Пример использования метода сохранения:

```java
public static void main(String args[]) {
	...
  User creator = new User(7L, “user”, “user”, new ArrayList(), new ArrayList());
  User author = creator;
  Room room = new Room(8L, “room”, creator, new ArrayList());
  Message message = new Message(null, author, room, “Hello!”, LocalDateTime.now());
  MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(...);
  messagesRepository.save(message);
  System.out.println(message.getId()); // ex. id == 11
}
```

Таким образом, метод save должен присвоить значение ID входящей модели после сохранения данных в базе данных. Если у автора и комнаты нет присвоенных в базе данных ID, или эти ID равны null, следует выбросить исключение RuntimeException NotSavedSubEntityException (реализуйте это исключение самостоятельно).

# Часть IV
### Update


Теперь нам нужно реализовать метод обновления в MessageRepository. Этот метод должен полностью обновить существующую сущность в базе данных. Если новое значение поля в обновляемой сущности равно null, это значение должно быть сохранено в базе данных.

Пример использования метода обновления:

```java
public static void main(String args[]) {
  MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(...);
  Optional<Message> messageOptional = messagesRepository.findById(11);
  if (messageOptinal.isPresent()) {
    Message message = messageOptional.get();
    message.setText(“Bye”);
    message.setDateTime(null);
    messagesRepository.update(message);
  }
  ...
}
```

В этом примере значение столбца, хранящего текст сообщения, будет изменено, а время сообщения будет иметь значение null.

# Часть V
### Find All


Теперь вам необходимо реализовать интерфейс UsersRepository и класс UsersRepositoryJdbcImpl, используя соответствующий `SINGLE List<User> findAll(int page, int size)`метод.

Этот метод должен возвращать значение size — количество пользователей, отображаемых на странице, с указанием номера страницы. Такой «пошаговый» поиск данных называется пагинацией. Таким образом, СУБД делит общий набор данных на страницы, каждая из которых содержит значение size. Например, если набор содержит 20 записей со значением page = 3 и size = 4, вы получите пользователей с 12 по 15 (нумерация пользователей и страниц начинается с 0).

Наиболее сложная ситуация при преобразовании реляционных связей в объектно-ориентированные возникает, когда вы получаете набор сущностей вместе с их подсущностями. В этой задаче каждый пользователь в полученном списке должен иметь включенные зависимости — список чатов, созданных этим пользователем, а также список чатов, в которых пользователь участвует.

Каждая подсистема пользователя НЕ ДОЛЖНА включать свои зависимости, то есть список сообщений внутри каждой комнаты должен быть пустым.

Реализованное действие метода следует продемонстрировать в файле Program.java.

**Примечания** :

-   Метод findAll(int page, int size) должен быть реализован ОДНИМ запросом к базе данных. Использование дополнительных SQL-запросов для получения информации о каждом пользователе не допускается.
-   Мы рекомендуем использовать CTE PostgreSQL.
-   Метод UsersRepositoryJdbcImpl должен принимать интерфейс DataSource из пакета java.sql в качестве параметра конструктора.