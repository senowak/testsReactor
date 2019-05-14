package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class CoffeeMachineTest {

    HashMap<CoffeeSize, Integer> map = new HashMap();
    Grinder grinder;
    CoffeeReceipes coffeeReceipes;
    MilkProvider milkProvider;

    @Before public void initialise() {
        map.put(CoffeeSize.STANDARD, 100);
        map.put(CoffeeSize.DOUBLE, 200);
        map.put(CoffeeSize.SMALL, 80);
        grinder = mock(Grinder.class);
        coffeeReceipes = mock(CoffeeReceipes.class);
        milkProvider = mock(MilkProvider.class);
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test public void testCoffeMachineMakeMilkAmount() {
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder().withMilkAmount(100).withWaterAmounts(map).build();
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(true);
        CoffeOrder coffeOrder = CoffeOrder.builder().withType(CoffeType.ESPRESSO).withSize(CoffeeSize.STANDARD).build();
        CoffeeMachine coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        Coffee result = coffeeMachine.make(coffeOrder);
        assertThat(result.getMilkAmout(), is(100));
    }

    @Test public void testCoffeMachineMakeWaterAmount() {
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder().withMilkAmount(100).withWaterAmounts(map).build();
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(true);
        CoffeOrder coffeOrder = CoffeOrder.builder().withType(CoffeType.ESPRESSO).withSize(CoffeeSize.STANDARD).build();
        CoffeeMachine coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        Coffee result = coffeeMachine.make(coffeOrder);
        assertThat(result.getWaterAmount(), is(100));
    }

    @Test public void testCoffeMachineMakeWrongCoffeeType() {
        CoffeeReceipe coffeeReceipe = CoffeeReceipe.builder().withMilkAmount(100).withWaterAmounts(map).build();
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(true);
        CoffeOrder coffeOrder = CoffeOrder.builder().withType(CoffeType.CAPUCCINO).withSize(CoffeeSize.STANDARD).build();
        CoffeeMachine coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        assertThrows(UnknownCofeeTypeException.class, () -> coffeeMachine.make(coffeOrder));
    }
}
