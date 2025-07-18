package core.basesyntax.modeltest;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @Test
    void unknown_operation_error_notOK() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromCode("k");
        });
        assertTrue(exception.getMessage().contains("Unknown operation code: "));
    }

    @Test
    void null_operation_error_notOK() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromCode(null);
        });
        assertTrue(exception.getMessage().contains("Unknown operation code: "));
    }

    @Test
    void operation_OK() {
        assertSame(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertSame(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertSame(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertSame(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void quantity_cant_be_negative_notOK() {
        fruitTransaction = new FruitTransaction();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fruitTransaction.setQuantity(-5);
        });
        assertTrue(exception.getMessage().contains("Quantity can`t be negative"));
    }
}
