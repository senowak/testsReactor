package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class) public class CoffeeMachineTest {

    CoffeOrder coffeOrder;
    CoffeeMachine coffeeMachine;
    CoffeeReceipe coffeeReceipe;

    @Mock CoffeeReceipes coffeeReceipes;

    @Mock Grinder grinder;

    @Mock MilkProvider milkProvider;

    @Before public void setup() {
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
    }

    @Test(expected = NoCoffeeBeansException.class) public void shouldThrowNoCoffeeBeansExceptionWhenThereIsNoCoffee() {
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(false);
        coffeeMachine.make(coffeOrder);
    }

    @Test public void shouldReturnCoffeeWithTheSameAmountOfWaterAsInCoffeeReceipt() {
        Map<CoffeeSize, Integer> coffeeMap = new HashMap<>();
        coffeeMap.put(CoffeeSize.SMALL, 30);
        coffeeReceipe = CoffeeReceipe.builder().withMilkAmount(15).withWaterAmounts(coffeeMap).build();
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(coffeeReceipe);
        Coffee coffee = coffeeMachine.make(coffeOrder);
        assertThat(coffeeReceipe.getWaterAmount(CoffeeSize.SMALL), is(coffee.getWaterAmount()));
    }

    @Test public void methodGrindShouldNeverCalled() {
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(false);
        verify(grinder, times(0)).grind(CoffeeSize.SMALL);
    }

    @Test(expected = IllegalArgumentException.class) public void shouldThrowIllegalArgumentExceptionWhenThereIsNoReceiptForOrder() {
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(null);
        coffeeMachine.make(coffeOrder);
    }

    @Test public void methodWithTypeShouldReturnCorrectTypeAndSize() {
        CoffeOrder coffeOrderTest = CoffeOrder.builder().withSize(CoffeeSize.STANDARD).withType(CoffeType.CAPUCCINO).build();
        Assert.assertEquals(coffeOrderTest.getType(), CoffeType.CAPUCCINO);
        Assert.assertEquals(coffeOrderTest.getSize(), CoffeeSize.STANDARD);
    }

    @Test(expected = NoCoffeeBeansException.class) public void whenTheCoffeeSizeIsDifferentThrowNoCoffeeBeansException() {
        Map<CoffeeSize, Integer> coffeeMap = new HashMap<>();
        coffeeMap.put(CoffeeSize.SMALL, 30);
        coffeeReceipe = CoffeeReceipe.builder().withMilkAmount(20).withWaterAmounts(coffeeMap).build();
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(true);
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(coffeeReceipe);
        Coffee coffee = coffeeMachine.make(coffeOrder);
        System.out.println(coffee.getMilkAmout());
        System.out.println(coffee.getWaterAmount());
    }
}

