package ru.myapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;


@Entity
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

    public Item() {
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", desc='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}
