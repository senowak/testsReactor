package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {

    @Mock
    Grinder grinder;
    @Mock
    CoffeeReceipes receipes;
    @Mock
    MilkProvider milkProvider;

    private Map<CoffeeSize, Integer> coffeeSizes;
    private CoffeeMachine machine;

    @Before
    public void setUp() {
        coffeeSizes = new HashMap<>();
        coffeeSizes.put(CoffeeSize.SMALL, 1);
        coffeeSizes.put(CoffeeSize.STANDARD, 2);
        coffeeSizes.put(CoffeeSize.DOUBLE, 4);
        machine = new CoffeeMachine(grinder, milkProvider, receipes);
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void OrderForStandardCoffeeShouldReturnStandardCoffee() {
        CoffeOrder order = createOrderAndSetDefaultMockConfiguration(1, CoffeeSize.STANDARD, CoffeType.CAPUCCINO);
        Coffee result = machine.make(order);

        Assert.assertThat(result.getMilkAmout(), is(1));
        Assert.assertThat(result.getWaterAmount(), is(2));
    }

    @Test
    public void coffeeMachineShouldUseGrinderAndMilkProviderAndCoffeeReceipes() {
        machine.make(createOrderAndSetDefaultMockConfiguration(1, CoffeeSize.STANDARD, CoffeType.CAPUCCINO));

        verify(grinder).grind(CoffeeSize.STANDARD);
        verify(receipes, atLeastOnce()).getReceipe(CoffeType.CAPUCCINO);
        verify(milkProvider).pour(1);
    }

    @Test(expected = UnknownCofeeTypeException.class)
    public void orderWithoutReceipeShouldThrowException() {
        CoffeOrder order = createOrderAndSetDefaultMockConfiguration(1, CoffeeSize.STANDARD, CoffeType.CAPUCCINO);
        when(receipes.getReceipe(CoffeType.CAPUCCINO)).thenReturn(null);
        machine.make(order);
    }

    @Test(expected = NoCoffeeBeansException.class)
    public void coffeePreparationShouldFailWhenGrindingFails() {
        CoffeOrder order = createOrderAndSetDefaultMockConfiguration(1, CoffeeSize.STANDARD, CoffeType.CAPUCCINO);
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(false);
        machine.make(order);
    }

    private CoffeOrder createOrderAndSetDefaultMockConfiguration(int milkAmount, CoffeeSize coffeeSize, CoffeType coffeType) {
        when(grinder.grind(coffeeSize)).thenReturn(true);
        CoffeeReceipe receipe = CoffeeReceipe.builder()
                .withMilkAmount(milkAmount)
                .withWaterAmounts(this.coffeeSizes)
                .build();
        when(receipes.getReceipe(coffeType)).thenReturn(receipe);

        return CoffeOrder.builder().withSize(coffeeSize).withType(coffeType).build();
    }
}
