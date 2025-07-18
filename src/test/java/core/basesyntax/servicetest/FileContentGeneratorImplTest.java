package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileContentGenerator;
import core.basesyntax.service.impl.FileContentGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileContentGeneratorImplTest {
    private static final String FILE_HEADER = "fruit,quantity";
    private FileContentGenerator fileContentGenerator;

    @BeforeEach
    void testUp() {
        fileContentGenerator = new FileContentGeneratorImpl();
    }

    @Test
    void storage_is_empty_error_notOK() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileContentGenerator.contentGen();
        });
        assertTrue(exception.getMessage().contains("Storage is empty, can`t generate content"));
    }

    @Test
    void how_file_content_generator_works_OK() {
        final String except = FILE_HEADER + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "orange,100" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "watermelon,200";
        Storage.STORAGE.put("banana", 100);
        Storage.STORAGE.put("apple", 100);
        Storage.STORAGE.put("orange", 100);
        Storage.STORAGE.put("watermelon", 200);
        assertEquals(fileContentGenerator.contentGen(), except);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }
}
