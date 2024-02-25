package ru.myapp.dto.request;

public record ToDo(

        Integer userId,

        Integer id,

        String title,

        Boolean completed) {
}