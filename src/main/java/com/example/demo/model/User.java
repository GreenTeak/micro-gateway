package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Table(name = "user")
public class User {
    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    public User(){}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Id
    @GeneratedValue
    public Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

}
