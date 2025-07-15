package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String FILE_HEADER = "fruit,quantity";
    private ReportGenerator reportGenerator;

    @BeforeEach
    void testUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.STORAGE.clear();
    }

    @Test
    void storage_is_empty_error() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            reportGenerator.reportGenerate();
        });
        Assertions.assertTrue(exception.getMessage().contains("Storage is empty"));
    }

    @Test
    void how_report_generator_works() {
        final String except = FILE_HEADER + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "orange,100" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "watermelon,200";
        Storage.STORAGE.put("banana", 100);
        Storage.STORAGE.put("apple", 100);
        Storage.STORAGE.put("orange", 100);
        Storage.STORAGE.put("watermelon", 200);
        Assertions.assertEquals(reportGenerator.reportGenerate(), except);
    }
}
