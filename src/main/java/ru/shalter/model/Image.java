package ru.shalter.model;

import lombok.Data;

@Data
public class Image {

    Long id;

    long postId;

    String originalFileName;

    String filePath;
}
