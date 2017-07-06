package bzh.zomzog.zeshop.product.service;

import bzh.zomzog.zeshop.product.exception.StorageException;
import bzh.zomzog.zeshop.product.exception.StorageFileNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Zomzog on 06/07/2017.
 * Test for {@link StorageService}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageServiceTest {

    @Autowired
    private StorageService storageService;

    @Test
    public void emptyFileThrowException() {
        try {
            final byte[] empty = new byte[0];
            this.storageService.store(new MockMultipartFile("pony", empty), "");
            assertThat(false).isTrue();
        } catch (final Exception e) {
            assertThat(e).isInstanceOf(StorageException.class);
            assertThat(e.getMessage()).startsWith("Failed to store empty file ");
        }
    }

    @Test
    public void deleteUnknownFileThrowException() {
        try {
            this.storageService.delete("ThisPonyFileWillNeverExist.jps");
            assertThat(false).isTrue();
        } catch (final Exception e) {
            assertThat(e).isInstanceOf(StorageException.class);
            assertThat(e.getMessage()).startsWith("Failed to delete file");
        }
    }

    @Test
    public void loadInvalidFileNameThrowException() {
        loadInvalidFileNamesThrowException("ThisPonyFileWillNeverExist.jpg", "Could not read file:");
        loadInvalidFileNamesThrowException("ThisPonyFileW*illNeverExist.jpg", ""); // Not same error on windows & linux
        loadInvalidFileNamesThrowException("ThisPonyFileW:illNeverExist.jpg", ""); // Not same error on windows & linux
    }

    private void loadInvalidFileNamesThrowException(final String filename, final String errorMessage) {
        try {
            this.storageService.loadAsResource(filename);
            assertThat(false).isTrue();
        } catch (final Exception e) {
            assertThat(e).isInstanceOf(StorageFileNotFoundException.class);
            assertThat(e.getMessage()).startsWith(errorMessage);
        }
    }
}
