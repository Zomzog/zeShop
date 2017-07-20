package bzh.zomzog.zeshop.product.web.rest;

import bzh.zomzog.zeshop.exception.BadParameterException;
import bzh.zomzog.zeshop.exception.NotFoundException;
import bzh.zomzog.zeshop.product.exception.StorageException;
import bzh.zomzog.zeshop.product.exception.StorageFileNotFoundException;
import bzh.zomzog.zeshop.product.service.ImageService;
import bzh.zomzog.zeshop.product.service.dto.ImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by Zomzog on 01/07/2017.
 */
@RestController
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    private final ImageService imageService;

    public ImageResource(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable final Long id) throws NotFoundException, StorageFileNotFoundException {

        final Resource file = this.imageService.serveImage(id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDTO> addImageToProduct(@RequestParam("file") final MultipartFile file) throws StorageException, URISyntaxException, BadParameterException {
        this.log.debug("REST request to add image {} ", file.getOriginalFilename());
        final ImageDTO result = this.imageService.save(file);
        return ResponseEntity.created(new URI("/api/images/" + +result.getId())).body(result);
    }
}
