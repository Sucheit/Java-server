package ru.myapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "items")
public class Item extends AbstractEntity implements Serializable {

    @Size(min = 3, max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 5, max = 255)
    @Column(name = "description", nullable = false)
    private String description;

    @Positive
    @Column(name = "amount", nullable = false)
    private Integer amount;
}
