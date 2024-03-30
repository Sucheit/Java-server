package ru.myapp.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
@Cacheable
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
@ToString(callSuper = true)
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

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    protected Set<User> users = new HashSet<>();
}
