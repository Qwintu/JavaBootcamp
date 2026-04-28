package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private final Long id;
    private final String login;
    private String passward;
    private List<Chatroom> listOfCreatedRooms;
    private List<Chatroom> socializedRooms;

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.passward = password;
        this.listOfCreatedRooms = new ArrayList<>();
        this.socializedRooms = new ArrayList<>();
    }

    public User(Long id, String login, String password, List<Chatroom> listOfCreatedRooms, List<Chatroom> socializedRooms) {
        this.id = id;
        this.login = login;
        this.passward = password;
        this.listOfCreatedRooms = listOfCreatedRooms;
        this.socializedRooms = socializedRooms;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;          // проверка на сам объект
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", passward='" + passward + '\'' +
                ", NumbersOfCreatedRooms=" + (listOfCreatedRooms != null ? listOfCreatedRooms.size() : 0) +  // для избегания рекурсии
                ", NumbersOfsocializedRooms=" + (socializedRooms != null ? socializedRooms.size() : 0) +
                "}";
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public void setListOfCreatedRooms(List<Chatroom> listOfCreatedRooms) {
        listOfCreatedRooms = listOfCreatedRooms;
    }

    public void setSocializedRooms(List<Chatroom> socializedRooms) {
        this.socializedRooms = socializedRooms;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return passward;
    }

    public List<Chatroom> getListOfCreatedRooms() {
        return listOfCreatedRooms;
    }

    public List<Chatroom> getSocializedRooms() {
        return socializedRooms;
    }
}


