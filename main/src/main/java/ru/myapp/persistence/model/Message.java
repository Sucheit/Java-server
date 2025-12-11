package ru.myapp.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@BatchSize(size = 5) // не работает без LazyLoad
public class Message extends AbstractEntity {

  @Column(name = "message_id")
  private String messageId;

  @Column(name = "message")
  private String message;
}
