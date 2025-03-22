package ru.shalter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.shalter.exeption.ConditionsNotMetException;
import ru.shalter.exeption.ImageFileException;
import ru.shalter.exeption.NotFoundException;
import ru.shalter.model.Image;
import ru.shalter.model.ImageData;
import ru.shalter.model.Post;
import ru.shalter.storage.image.InMemoryImageStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final InMemoryImageStorage inMemoryImageStorage = new InMemoryImageStorage();

    // получение данных об изображениях указанного поста
    public List<Image> getPostImages(long postId) {
        return inMemoryImageStorage.getPostImages(postId);
    }


    // загружаем данные указанного изображения с диска
    public ImageData getImageData(long imageId) {
        if (!inMemoryImageStorage.getAllImageId().contains(imageId)) {
            throw new NotFoundException("Изображение с id = " + imageId + " не найдено");
        }
        return inMemoryImageStorage.getImageData(imageId);
    }


    private byte[] loadFile(Image image) {
        Path path = Paths.get(image.getFilePath());
        if (Files.exists(path)) {
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                throw new ImageFileException("Ошибка чтения файла.  Id: " + image.getId()
                        + ", name: " + image.getOriginalFileName());
            }
        } else {
            throw new ImageFileException("Файл не найден. Id: " + image.getId()
                    + ", name: " + image.getOriginalFileName());
        }
    }


    // сохранение списка изображений, связанных с указанным постом
    public List<Image> saveImages(long postId, List<MultipartFile> files) {
        return files.stream().map(file -> inMemoryImageStorage.saveImage(postId, file)).collect(Collectors.toList());
    }

}
