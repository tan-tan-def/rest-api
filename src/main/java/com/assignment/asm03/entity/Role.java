package com.assignment.asm03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @JsonIgnore
    private String name;

//    @JsonIgnore
//    @Column(name="created_at")
//    @Temporal(TemporalType.DATE)
//    private Date createdAt;
//
//    @JsonIgnore
//    @Column(name="updated_at")
//    @Temporal(TemporalType.DATE)
//    private Date updatedAt;
//
//    @JsonIgnore
//    @Temporal(TemporalType.DATE)
//    @Column(name="deleted_at")
//    private Date deletedAt;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
               mappedBy = "role")
    @ToString.Exclude
    private List<User> user;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
}
