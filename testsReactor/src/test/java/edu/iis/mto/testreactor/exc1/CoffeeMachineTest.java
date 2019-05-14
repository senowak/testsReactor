package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class CoffeeMachineTest {

    private Coffee coffee;

    private CoffeeMachine coffeeMachine;
    private CoffeeReceipes coffeeReceipes;
    private Grinder grinder;
    private MilkProvider milkProvider;

    @Before
    public void init(){
        coffee = new Coffee();
        grinder = mock(Grinder.class);
        milkProvider = mock(MilkProvider.class);
        coffeeReceipes = mock(CoffeeReceipes.class);
    }

    @Test(expected = UnknownCofeeTypeException.class)
    public void validateShouldThrowUnknownCofeeTypeExceptionTest(){
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(null);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeOrder);
    }

    @Test(expected = NoCoffeeBeansException.class)
    public void grinderShouldThrowNoCoffeeBeansExceptionTest(){
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        Map<CoffeeSize, Integer> waterAmount = new HashMap<>();
        waterAmount.put(CoffeeSize.SMALL, 5);
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder().withWaterAmounts(waterAmount).withMilkAmount(5).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(false);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeOrder);
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}
