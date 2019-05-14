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
    public void validateShouldThrowUnknownCoffeeTypeExceptionTest(){
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(null);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeOrder);
    }

    @Test(expected = NoCoffeeBeansException.class)
    public void grindCoffeeShouldThrowNoCoffeeBeansExceptionTest(){
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
    public void testIfMadeCoffeeHasProperAmountOfWaterAndMilkTest(){
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        Map<CoffeeSize, Integer> waterAmount = new HashMap<>();
        waterAmount.put(CoffeeSize.SMALL, 5);
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder().withWaterAmounts(waterAmount).withMilkAmount(5).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffee.setMilkAmout(5);
        coffee.setWaterAmount(5);

        Coffee coffeeMadeByMachine = coffeeMachine.make(coffeOrder);

        assertThat(coffee.getMilkAmout(), is(equalTo(coffeeMadeByMachine.getMilkAmout())));
        assertThat(coffee.getWaterAmount(), is(equalTo(coffeeMadeByMachine.getWaterAmount())));
    }


    @Test
    public void grinderShouldGrindCoffeeBeansOnceTest(){
        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        Map<CoffeeSize, Integer> waterAmount = new HashMap<>();
        waterAmount.put(CoffeeSize.SMALL, 5);
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder().withWaterAmounts(waterAmount).withMilkAmount(5).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeOrder);

        verify(grinder, times(1)).grind(Mockito.any());
    }

    @Test
    public void milkProviderShouldPourMilkOnceTest(){

        int milkAmount = 5;

        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        Map<CoffeeSize, Integer> waterAmount = new HashMap<>();
        waterAmount.put(CoffeeSize.SMALL, milkAmount);
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder().withWaterAmounts(waterAmount).withMilkAmount(milkAmount).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);

        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeOrder);

        verify(milkProvider, times(1)).pour(milkAmount);
    }

    @Test
    public void milkProviderShouldNotPourMilkWhenCoffeeNotContainItTest(){

        int milkAmount = 0;

        CoffeOrder coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        Map<CoffeeSize, Integer> waterAmount = new HashMap<>();
        waterAmount.put(CoffeeSize.SMALL, milkAmount);
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder().withWaterAmounts(waterAmount).withMilkAmount(milkAmount).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);

        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeOrder);

        verify(milkProvider, times(0)).pour(milkAmount);
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}
