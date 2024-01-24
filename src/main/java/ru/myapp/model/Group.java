package ru.myapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;


@Cacheable
@Entity
@Table(name = "groups")
@Inheritance(strategy = InheritanceType.JOINED)
public class Group extends AbstractEntity {

    @NotBlank
    @Size(min = 6, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    @NotBlank
    @Size(min = 10, max = 255)
    @Column(name = "description", nullable = false)
    protected String description;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    protected Set<User> users = new HashSet<>();

    public Group() {
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

    public Set<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
