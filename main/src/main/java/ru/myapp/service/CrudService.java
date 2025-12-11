package ru.myapp.service;

import java.util.List;

public interface CrudService<I, R1, R2, D> {

  List<D> getAllEntities();

  R2 getEntityById(I id);

  R2 createEntity(R1 requestDto);

  R2 updateEntity(I id, R1 requestDto);

  void deleteEntityById(I id);
}
