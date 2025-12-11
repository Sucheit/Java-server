package ru.myapp.dto.response;

public record ToDo(

        Integer userId,

        Integer id,

        String title,

        Boolean completed) {
}