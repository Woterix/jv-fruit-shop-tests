package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceStrategy;
import core.basesyntax.strategy.PurchaseStrategy;
import core.basesyntax.strategy.QuantityCalculationStrategy;
import core.basesyntax.strategy.ReturnStrategy;
import core.basesyntax.strategy.SupplyStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityCalculationStrategyTest {
    private QuantityCalculationStrategy quantityCalculationStrategy;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void testUp() {
        fruitTransaction = new FruitTransaction();
        Storage.STORAGE.put("banana", 100);
        Storage.STORAGE.put("apple", 20);
        Storage.STORAGE.put("pineapple", 50);
    }

    @Test
    void how_balance_strategy_works() {
        quantityCalculationStrategy = new BalanceStrategy();
        fruitTransaction.setQuantity(40);
        fruitTransaction.setFruit("watermelon");
        quantityCalculationStrategy.calculate(fruitTransaction);
        Assertions.assertTrue(Storage.STORAGE.containsKey("watermelon"));
        Assertions.assertTrue(Storage.STORAGE.get("watermelon") == 40);
    }

    @Test
    void how_return_strategy_work() {
        quantityCalculationStrategy = new ReturnStrategy();
        fruitTransaction.setQuantity(20);
        fruitTransaction.setFruit("banana");
        quantityCalculationStrategy.calculate(fruitTransaction);
        Assertions.assertTrue(Storage.STORAGE.containsKey("banana"));
        Assertions.assertTrue(Storage.STORAGE.get("banana") == 120);
    }

    @Test
    void how_supply_strategy_works() {
        quantityCalculationStrategy = new SupplyStrategy();
        fruitTransaction.setQuantity(40);
        fruitTransaction.setFruit("pineapple");
        quantityCalculationStrategy.calculate(fruitTransaction);
        Assertions.assertTrue(Storage.STORAGE.containsKey("pineapple"));
        Assertions.assertTrue(Storage.STORAGE.get("pineapple") == 90);
    }

    @Test
    void purchase_strategy_error() {
        quantityCalculationStrategy = new PurchaseStrategy();
        fruitTransaction.setQuantity(30);
        fruitTransaction.setFruit("apple");
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            quantityCalculationStrategy.calculate(fruitTransaction);
        });
        Assertions.assertTrue(exception.getMessage().contains("Not enough "));
        Assertions.assertTrue(exception.getMessage().contains("in storage"));
    }

    @Test
    void how_purchase_strategy_works() {
        quantityCalculationStrategy = new PurchaseStrategy();
        fruitTransaction.setQuantity(20);
        fruitTransaction.setFruit("apple");
        quantityCalculationStrategy.calculate(fruitTransaction);
        Assertions.assertTrue(Storage.STORAGE.containsKey("apple"));
        Assertions.assertTrue(Storage.STORAGE.get("apple") == 0);
    }
}
