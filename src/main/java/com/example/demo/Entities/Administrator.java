package com.example.demo.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Administrators")
public class Administrator {

    @Id
    private int id;

    private String name;

    private String password;

    public Administrator() {
    }

    public Administrator(int id) {
        this.id = id;
    }

    public Administrator(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    
    
}
