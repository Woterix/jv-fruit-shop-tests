package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitDataCounter;
import core.basesyntax.service.impl.FruitDataCounterImpl;
import core.basesyntax.strategy.BalanceStrategy;
import core.basesyntax.strategy.PurchaseStrategy;
import core.basesyntax.strategy.QuantityCalculationStrategy;
import core.basesyntax.strategy.ReturnStrategy;
import core.basesyntax.strategy.SupplyStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDataCounterImplTest {
    private FruitDataCounter fruitDataCounter;
    private List<FruitTransaction> fruitTransaction;
    private Map<FruitTransaction.Operation,
            QuantityCalculationStrategy> operationsMap;

    @BeforeEach
    void testUp() {
        Map<FruitTransaction.Operation, QuantityCalculationStrategy> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceStrategy());
        strategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseStrategy());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnStrategy());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyStrategy());
        fruitDataCounter = new FruitDataCounterImpl(strategyMap);
        fruitTransaction = new ArrayList<>();
    }

    @Test
    void how_fruit_data_counter_works_OK() {
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
        fruitTransaction.add(first);
        fruitTransaction.add(second);
        fruitTransaction.add(third);
        fruitDataCounter.fruitsCounter(fruitTransaction);
        assertEquals(120, Storage.STORAGE.get("banana"));
        assertEquals(100, Storage.STORAGE.get("apple"));
    }

    @Test
    void if_list_is_empty_error_notOK() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fruitDataCounter.fruitsCounter(fruitTransaction);
        });
        assertTrue(exception.getMessage().contains("Empty fruits list"));
    }
}
