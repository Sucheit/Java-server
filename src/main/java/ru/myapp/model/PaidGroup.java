package ru.myapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;


@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "paid_groups")
public class PaidGroup extends Group {

    @Range(min = 10, max = 1000)
    @Column(name = "cost", nullable = false)
    protected Integer cost;
}
