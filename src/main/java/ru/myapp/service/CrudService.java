package ru.myapp.service;

import java.util.List;

public interface CrudService<Id, RequestDto, ResponseDto, RequestDtoShort> {

    List<RequestDtoShort> getAllEntities();

    ResponseDto getEntityById(Id id);

    ResponseDto createEntity(RequestDto requestDto);

    ResponseDto updateEntity(Id id, RequestDto requestDto);

    void deleteEntityById(Id id);
}
