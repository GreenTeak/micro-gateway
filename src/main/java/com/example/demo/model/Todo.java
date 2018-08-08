package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue
    private Integer id;

    private String text;
    private boolean completed;
    private boolean edit;
    private int userid;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "todo_id")
    private List<Task> tasks;


}
