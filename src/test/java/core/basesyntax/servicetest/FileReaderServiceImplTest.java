package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReaderService = new FileReaderServiceImpl();
        tempFile = Path.of("src/test/resourcestest/input.csv");
        Files.write(tempFile, List.of("b,apple,10", "s,banana,5"));
    }

    @Test
    void how_fileReader_works_OK() {
        List<String> lines = fileReaderService.lines(tempFile.toString());
        assertEquals("b,apple,10", lines.get(0));
        assertEquals("s,banana,5", lines.get(1));
    }

    @Test
    void fake_path_expect_RuntimeException_notOK() {
        String fakePath = "fakepath.csv";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReaderService.lines(fakePath);
        });
        assertTrue(exception.getMessage().contains("Can`t read or find file: "));
    }
}
