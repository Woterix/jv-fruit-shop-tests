package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceStrategy;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseStrategy;
import core.basesyntax.strategy.QuantityCalculationStrategy;
import core.basesyntax.strategy.ReturnStrategy;
import core.basesyntax.strategy.SupplyStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, QuantityCalculationStrategy> strategyMap;

    @BeforeEach
    void testUp() {
        strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceStrategy());
        strategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseStrategy());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnStrategy());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyStrategy());
        operationStrategy = new OperationStrategyImpl(strategyMap);
    }

    @Test
    void should_return_correct_strategy_for_balance_operation_OK() {
        assertInstanceOf(BalanceStrategy.class,
                operationStrategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void should_return_correct_strategy_for_purchase_operation_OK() {
        assertInstanceOf(PurchaseStrategy.class,
                operationStrategy.get(FruitTransaction.Operation.PURCHASE));
    }

    @Test
    void should_return_correct_strategy_for_return_operation_OK() {
        assertInstanceOf(ReturnStrategy.class,
                operationStrategy.get(FruitTransaction.Operation.RETURN));
    }

    @Test
    void should_return_correct_strategy_for_supply_operation_OK() {
        assertInstanceOf(SupplyStrategy.class,
                operationStrategy.get(FruitTransaction.Operation.SUPPLY));
    }

}
