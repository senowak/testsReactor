package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Matches;

import java.util.HashMap;
import java.util.Map;

public class CoffeeMachineTest {

    CoffeeMachine coffeeMachine;
    Grinder grinder;
    MilkProvider milkProvider;
    CoffeeReceipes coffeeReceipes;
    Coffee coffee;

    @Before
    public void setup(){
        grinder = Mockito.mock(Grinder.class);
        milkProvider = Mockito.mock(MilkProvider.class);
        coffeeReceipes = Mockito.mock(CoffeeReceipes.class);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);

        coffee = new Coffee();
    }


    @Test (expected = UnknownCofeeTypeException.class)
    public void testShouldThrowUnknownCoffeeTypeExceptionWhenCoffeeTypeIsUnknown(){

        CoffeOrder coffeOrder = CoffeOrder.builder()
                .withSize(CoffeeSize.SMALL)
                .withType(CoffeType.LATTE)
                .build();
        Mockito.when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);
        Mockito.when(coffeeReceipes.getReceipe(CoffeType.LATTE)).thenReturn(null);

        coffeeMachine.make(coffeOrder);

    }

    @Test(expected = NoCoffeeBeansException.class)
    public void testShouldThrowNoCoffeeBeansExceptionWhenThereIsNoCoffeeBeans() {

        CoffeOrder coffeOrder = CoffeOrder.builder()
                .withSize(CoffeeSize.SMALL)
                .withType(CoffeType.LATTE)
                .build();

        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder()
                .withMilkAmount(10)
                .withWaterAmounts(new HashMap<>())
                .build();

        Mockito.when(grinder.grind(CoffeeSize.SMALL)).thenReturn(false);
        Mockito.when(coffeeReceipes.getReceipe(CoffeType.LATTE)).thenReturn(coffeeReceipe);

        coffeeMachine.make(coffeOrder);
    }

    @Test
    public void testShouldReturnCoffeeWithTheSameAmountOfMilkAsOnReceipt() {
        CoffeOrder coffeOrder = CoffeOrder.builder()
                                              .withSize(CoffeeSize.SMALL)
                                              .withType(CoffeType.LATTE)
                                              .build();
        Map<CoffeeSize, Integer> coffeeMap = new HashMap<>();
        coffeeMap.put(CoffeeSize.SMALL, 30);
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder()
                                                   .withMilkAmount(15)
                                                   .withWaterAmounts(coffeeMap)
                                                   .build();

        Mockito.when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);
        Mockito.when(coffeeReceipes.getReceipe(CoffeType.LATTE)).thenReturn(coffeeReceipe);

        Coffee coffee = coffeeMachine.make(coffeOrder);

        assertEquals(coffeeReceipe.getMilkAmount(), coffee.getMilkAmout());
    }




    @Test
    public void testShouldReturnCoffeeWithTheSameAmountOfWaterAsInCoffeeReceipt() {
        CoffeOrder coffeOrder = CoffeOrder.builder()
                                          .withSize(CoffeeSize.SMALL)
                                          .withType(CoffeType.ESPRESSO)
                                          .build();
        Map<CoffeeSize, Integer> coffeeMap = new HashMap<>();
        coffeeMap.put(CoffeeSize.SMALL, 30);
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder()
                                                   .withMilkAmount(15)
                                                   .withWaterAmounts(coffeeMap)
                                                   .build();


        Mockito.when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);
        Mockito.when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(coffeeReceipe);


        Coffee coffee = coffeeMachine.make(coffeOrder);


        assertEquals(coffeeReceipe.getWaterAmount(CoffeeSize.SMALL), (Integer) coffee.getWaterAmount());
    }



}
