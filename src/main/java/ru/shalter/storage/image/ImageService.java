package ru.shalter.storage.image;

import org.springframework.web.multipart.MultipartFile;
import ru.shalter.model.Image;
import ru.shalter.model.ImageData;

import java.util.Collection;
import java.util.List;

public interface ImageService {

    Collection<Long> getAllImageId();

    List<Image> getPostImages(long postId);

    ImageData getImageData(long imageId);

    List<Image> saveImages(long postId, List<MultipartFile> files);

    Image saveImage(long postId, MultipartFile file);
}
