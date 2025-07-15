package core.basesyntax;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReaderService = new FileReaderServiceImpl();
        tempFile = Files.createTempFile("input", ".csv");
        Files.write(tempFile, List.of("b,apple,10", "s,banana,5"));
    }

    @Test
    void check_how_fileReader_works() {
        List<String> lines = fileReaderService.lines(tempFile.toString());
        Assertions.assertEquals("b,apple,10", lines.get(0));
        Assertions.assertEquals("s,banana,5", lines.get(1));
    }

    @Test
    void fake_path_expect_RuntimeException() {
        String fakePath = "fakepath.csv";

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            fileReaderService.lines(fakePath);
        });
        Assertions.assertTrue(exception.getMessage().contains("Can`t read or find file: "));
    }
}
