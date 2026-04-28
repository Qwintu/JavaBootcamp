package edu.school21.chat.models;

import java.time.Instant;
import java.util.Objects;

public class Message {
    private Long id;
    private final User author;
    private final Chatroom room;
    private String text;
    private Instant created_at;
    private Instant updated_at;

    public Message(Long id, User author, Chatroom room, String text) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.created_at = Instant.now();
        this.updated_at = null;
    }
    public Message(Long id, User author, Chatroom room, String text, Instant updated_at) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.updated_at = updated_at;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;          // проверка на сам объект
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(author, message.author) && Objects.equals(room, message.room) && Objects.equals(text, message.text) && Objects.equals(created_at, message.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, created_at);
    }

    @Override
    public String toString() {
        return "Message {" +
                "\n\tid=" + id +
                ", \n\tauthor=" + (author != null ? author/*.getLogin()*/ : "null") +
                ", \n\troom=" + (room != null ? room/*.getName()*/ : "null") +
                ", \n\ttext='" + text + '\'' +
                ", \n\tdateTime=" + created_at +
                "\n}";
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = this.updated_at;
    }
}

