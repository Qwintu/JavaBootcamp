package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chatroom {
    private final Long id;
    private final String name;
    private final User owner;
    private List<Message> massages;

    public Chatroom(Long id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.massages = new ArrayList<>();
    }

    public Chatroom(Long id, String name, User owner, List<Message> massagesList) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.massages = massagesList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;          // проверка на сам объект
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return Objects.equals(id, chatroom.id) && Objects.equals(name, chatroom.name) && Objects.equals(owner, chatroom.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner='" + owner.getLogin() + '\'' +
                ", NumbersOfMassages=" + (massages != null ? massages.size() : 0) +
                '}';
    }

    public void setMassages(List<Message> massages) {
        this.massages = massages;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public List<Message> getMassages() {
        return massages;
    }
}


