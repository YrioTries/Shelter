package ru.shelter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageData {

    private final byte[] data;

    private final String name;
}
