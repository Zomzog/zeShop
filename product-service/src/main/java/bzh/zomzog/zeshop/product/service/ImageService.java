package bzh.zomzog.zeshop.product.service;

import bzh.zomzog.zeshop.exception.NotFoundException;
import bzh.zomzog.zeshop.product.domain.Image;
import bzh.zomzog.zeshop.product.exception.StorageFileNotFoundException;
import bzh.zomzog.zeshop.product.repository.ImageRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Created by Zomzog on 01/07/2017.
 */
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final StorageService storageService;

    public ImageService(final ImageRepository imageRepository, final StorageService storageService) {
        this.imageRepository = imageRepository;
        this.storageService = storageService;
    }

    /**
     * Serve an image
     *
     * @param id
     * @return
     * @throws StorageFileNotFoundException
     * @throws NotFoundException
     */
    public Resource serveImage(final Long id) throws StorageFileNotFoundException, NotFoundException {
        final Image image = this.imageRepository.findOne(id);
        if (null == image) {
            throw new NotFoundException("Image not found");
        }
        return this.storageService.loadAsResource(image.getName());
    }
}
