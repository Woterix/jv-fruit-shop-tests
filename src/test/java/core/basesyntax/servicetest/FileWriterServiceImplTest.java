package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private FileWriterService fileWriterService;
    private Path path;
    private String content;

    @BeforeEach
    void setUp() throws IOException {
        fileWriterService = new FileWriterServiceImpl();
        path = Path.of("src/test/resourcestest/output.csv");
        content = "abc" + "\n" + "efg";
    }

    @Test
    void file_writer_example_OK() throws IOException {
        fileWriterService.fileWriterCsv(path.toString(), content);
        String fileContent = Files.readString(path);
        assertEquals(fileContent, content);
    }

    @Test
    void fake_path_expect_exception_notOK() {
        String fakePath = "faketaph.csv";
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fileWriterService.fileWriterCsv(fakePath, content);
        });
        assertTrue(runtimeException.getMessage().contains("Can`t write data to file: "));
    }
}
