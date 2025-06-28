package ru.myapp.persistence.model;


import org.springframework.beans.factory.annotation.Value;

/**
 * Open Projection
 */
public interface ItemFullInfo {

  @Value("#{'name: ' + target.name + ', description: ' + target.description + ', amount: ' + target.amount + '.'}")
  String getFullInfo();
}
