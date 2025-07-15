package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @Test
    void unknown_operation_error() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromCode("k");
        });
        Assertions.assertTrue(exception.getMessage().contains("Unknown operation code: "));
    }

    @Test
    void null_operation_error() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromCode(null);
        });
        Assertions.assertTrue(exception.getMessage().contains("Unknown operation code: "));
    }

    @Test
    void operation_ok() {
        FruitTransaction.Operation b = FruitTransaction.Operation.fromCode("b");
        FruitTransaction.Operation s = FruitTransaction.Operation.fromCode("s");
        FruitTransaction.Operation p = FruitTransaction.Operation.fromCode("p");
        FruitTransaction.Operation r = FruitTransaction.Operation.fromCode("r");
        Assertions.assertSame(FruitTransaction.Operation.BALANCE, b);
        Assertions.assertSame(FruitTransaction.Operation.SUPPLY, s);
        Assertions.assertSame(FruitTransaction.Operation.PURCHASE, p);
        Assertions.assertSame(FruitTransaction.Operation.RETURN, r);
    }

    @Test
    void quantity_cant_be_negative() {
        fruitTransaction = new FruitTransaction();
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            fruitTransaction.setQuantity(-5);
        });
        Assertions.assertTrue(exception.getMessage().contains("Quantity can`t be negative"));
    }
}
