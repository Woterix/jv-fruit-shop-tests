package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConvertor;
import core.basesyntax.service.impl.DataConvertorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
    void how_data_convertor_works() {
        fruitTransactions = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        FruitTransaction first = new FruitTransaction();
        first.setOperation(FruitTransaction.Operation.fromCode("b"));
        first.setFruit("banana");
        first.setQuantity(20);
        FruitTransaction second = new FruitTransaction();
        second.setOperation(FruitTransaction.Operation.fromCode("b"));
        second.setFruit("apple");
        second.setQuantity(100);
        FruitTransaction third = new FruitTransaction();
        third.setOperation(FruitTransaction.Operation.fromCode("s"));
        third.setFruit("banana");
        third.setQuantity(100);
        fruitTransactions.add(first);
        fruitTransactions.add(second);
        fruitTransactions.add(third);
        List<FruitTransaction> fruitTransactions1 = dataConvertor.dataConvert(lines);
        Assertions.assertEquals(fruitTransactions, fruitTransactions1);
    }

    @Test
    void not_enough_lines_error() {
        lines.add("type,fruit,quantity");
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            dataConvertor.dataConvert(lines);
        });
        Assertions.assertTrue(exception.getMessage().contains("Empty lines"));
    }

    @Test
    void invalid_line_size() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple");
        lines.add("s,banana,100");
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            dataConvertor.dataConvert(lines);
        });
        Assertions.assertTrue(exception.getMessage().contains("Invalid line: "));
    }
}
