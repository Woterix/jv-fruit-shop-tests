package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String FILE_HEADER = "fruit,quantity";
    private ReportGenerator reportGenerator;

    @BeforeEach
    void testUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void storage_is_empty_error_notOK() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reportGenerator.reportGenerate();
        });
        assertTrue(exception.getMessage().contains("Storage is empty"));
    }

    @Test
    void report_generator_example() {
        final String except = FILE_HEADER + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "orange,100" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "watermelon,200";
        Storage.STORAGE.put("banana", 100);
        Storage.STORAGE.put("apple", 100);
        Storage.STORAGE.put("orange", 100);
        Storage.STORAGE.put("watermelon", 200);
        assertEquals(reportGenerator.reportGenerate(), except);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }
}
