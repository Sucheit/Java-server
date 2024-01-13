package ru.myapp.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Cacheable
@Entity
@DiscriminatorValue("paid_group")
public class PaidGroup extends Group {

    @Column(name = "cost")
    private Integer cost;

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
        return "PaidGroup{" + super.toString() +
                "cost=" + cost +
                '}';
    }
}
