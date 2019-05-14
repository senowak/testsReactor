package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class CoffeeMachineTest {

    private final int WATER_AMOUNT = 5;
    private final int MILK_AMOUNT = 5;

    private Coffee coffee;
    private CoffeeMachine coffeeMachine;
    private CoffeeReceipes coffeeReceipes;
    private Grinder grinder;
    private MilkProvider milkProvider;
    private CoffeeReceipe coffeeReceipe;
    private Map<CoffeeSize, Integer> waterAmount;
    private CoffeOrder coffeeOrder;

    @Before
    public void init(){
        coffee = new Coffee();
        grinder = mock(Grinder.class);
        milkProvider = mock(MilkProvider.class);
        coffeeReceipes = mock(CoffeeReceipes.class);
        waterAmount = new HashMap<>();
        waterAmount.put(CoffeeSize.SMALL, WATER_AMOUNT);
        coffeeReceipe = CoffeeReceipe.builder().withWaterAmounts(waterAmount).withMilkAmount(MILK_AMOUNT).build();
        coffeeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
    }

    @Test(expected = UnknownCofeeTypeException.class)
    public void validateShouldThrowUnknownCoffeeTypeExceptionTest(){
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(null);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeeOrder);
    }

    @Test(expected = NoCoffeeBeansException.class)
    public void grindCoffeeShouldThrowNoCoffeeBeansExceptionTest(){
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(false);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeeOrder);
    }


    @Test
    public void testIfMadeCoffeeHasProperAmountOfWaterAndMilkTest(){
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);

        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffee.setMilkAmout(MILK_AMOUNT);
        coffee.setWaterAmount(WATER_AMOUNT);

        Coffee coffeeMadeByMachine = coffeeMachine.make(coffeeOrder);

        assertThat(coffee.getMilkAmout(), is(equalTo(coffeeMadeByMachine.getMilkAmout())));
        assertThat(coffee.getWaterAmount(), is(equalTo(coffeeMadeByMachine.getWaterAmount())));
    }


    @Test
    public void grinderShouldGrindCoffeeBeansOnceTest(){
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeeOrder);

        verify(grinder, times(1)).grind(Mockito.any());
    }

    @Test
    public void milkProviderShouldPourMilkOnceTest(){
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);

        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeeOrder);

        verify(milkProvider, times(1)).pour(MILK_AMOUNT);
    }

    @Test
    public void milkProviderShouldNotPourMilkWhenCoffeeNotContainItTest(){

        int milkAmount = 0;

        coffeeReceipe = CoffeeReceipe.builder().withWaterAmounts(waterAmount).withMilkAmount(milkAmount).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);

        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeeOrder);

        verify(milkProvider, times(0)).pour(milkAmount);
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}
