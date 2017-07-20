package bzh.zomzog.zeshop.product.service;

import bzh.zomzog.zeshop.exception.BadParameterException;
import bzh.zomzog.zeshop.exception.NotFoundException;
import bzh.zomzog.zeshop.product.domain.Image;
import bzh.zomzog.zeshop.product.exception.StorageException;
import bzh.zomzog.zeshop.product.exception.StorageFileNotFoundException;
import bzh.zomzog.zeshop.product.repository.ImageRepository;
import bzh.zomzog.zeshop.product.service.dto.ImageDTO;
import bzh.zomzog.zeshop.product.service.mapper.ImageMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Zomzog on 01/07/2017.
 */
@Service
public class ImageService {

    private static final int IMAGE_NAME_SIZE = 20;

    private final ImageRepository imageRepository;
    private final StorageService storageService;
    private final ImageMapper imageMapper;


    public ImageService(final ImageRepository imageRepository, final StorageService storageService, final ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.storageService = storageService;
        this.imageMapper = imageMapper;
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


    /**
     * @param file file
     * @return image informations
     * @throws StorageException
     */
    public ImageDTO save(final MultipartFile file) throws StorageException, BadParameterException {
        final String imageName = RandomStringUtils.randomAlphabetic(IMAGE_NAME_SIZE);
        this.storageService.store(file, imageName);
        final Image image = new Image().name(imageName);
        this.imageRepository.save(image);
        return this.imageMapper.map(image);
    }
}
