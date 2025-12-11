package ru.myapp.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemProjection {

  private String name;

  private String description;
}
