package org.example;

public class Client {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
