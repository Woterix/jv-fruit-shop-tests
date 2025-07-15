package core.basesyntax;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private FileWriterService fileWriterService;
    private Path path;
    private String content;

    @BeforeEach
    void setUp() throws IOException {
        fileWriterService = new FileWriterServiceImpl();
        path = Files.createTempFile("testoutput", ".csv");
        content = "abc" + "\n" + "efg";
    }

    @Test
    void how_file_writer_works() throws IOException {
        fileWriterService.fileWriterCsv(path.toString(), content);
        String fileContent = Files.readString(path);
        Assertions.assertEquals(fileContent, content);
    }

    @Test
    void fake_path_expect_exception() {
        String fakePath = "faketaph.csv";
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            fileWriterService.fileWriterCsv(fakePath, content);
        });
        Assertions.assertTrue(runtimeException.getMessage().contains("Can`t write data to file: "));
    }
}
