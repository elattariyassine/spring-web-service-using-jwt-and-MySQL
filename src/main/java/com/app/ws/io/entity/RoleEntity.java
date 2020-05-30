package com.app.ws.io.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long role_id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> employees = new HashSet<>();

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<UserEntity> employees) {
        this.employees = employees;
    }
}
