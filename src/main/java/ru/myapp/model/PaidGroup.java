package ru.myapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name = "paid_groups")
public class PaidGroup extends Group {

    @Range(min = 10, max = 1000)
    @Column(name = "cost", nullable = false)
    protected Integer cost;

    public PaidGroup() {
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "PaidGroup{" +
                "cost=" + cost +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
