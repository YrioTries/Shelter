package ru.shalter.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Post {

    Long id;
    long authorId;
    String description;
    Instant postDate;
}
