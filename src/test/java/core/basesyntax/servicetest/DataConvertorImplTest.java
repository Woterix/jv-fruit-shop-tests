package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConvertor;
import core.basesyntax.service.impl.DataConvertorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConvertorImplTest {
    private DataConvertor dataConvertor;
    private List<String> lines;
    private List<FruitTransaction> fruitTransactions;

    @BeforeEach
    void testUp() {
        dataConvertor = new DataConvertorImpl();
        lines = new ArrayList<>();
    }

    @Test
    void how_data_convertor_expected_OK() {
        fruitTransactions = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        FruitTransaction first = new FruitTransaction("banana",
                FruitTransaction.Operation.fromCode("b"), 20);
        FruitTransaction second = new FruitTransaction("apple",
                FruitTransaction.Operation.fromCode("b"), 100);
        FruitTransaction third = new FruitTransaction("banana",
                FruitTransaction.Operation.fromCode("s"), 100);
        fruitTransactions.add(first);
        fruitTransactions.add(second);
        fruitTransactions.add(third);
        List<FruitTransaction> fruitTransactions1 = dataConvertor.dataConvert(lines);
        assertEquals(fruitTransactions, fruitTransactions1);
    }

    @Test
    void not_enough_lines_error_notOK() {
        lines.add("type,fruit,quantity");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dataConvertor.dataConvert(lines);
        });
        assertTrue(exception.getMessage().contains("Empty lines"));
    }

    @Test
    void invalid_line_size_notOK() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple");
        lines.add("s,banana,100");
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataConvertor.dataConvert(lines);
        });
        assertTrue(exception.getMessage().contains("Invalid line: "));
    }
}
