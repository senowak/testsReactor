package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {
    @Mock
    private Grinder grinder;

    @Mock
    private MilkProvider milkProvider;

    @Mock
    private CoffeeReceipes coffeeReceipes;

    private CoffeOrder coffeOrder;
    private CoffeeMachine coffeeMachine;
    private Coffee coffee;
    private CoffeeReceipe coffeeReceipe;
    private Map<CoffeeSize, Integer> waterAmount;


    @Before
    public void init(){
        coffee = new Coffee();
        grinder = mock(Grinder.class);
        milkProvider = mock(MilkProvider.class);
        coffeeReceipes = mock(CoffeeReceipes.class);
        waterAmount = new HashMap<>();
        waterAmount.put(CoffeeSize.STANDARD, 5);
        coffeeReceipe = CoffeeReceipe.builder().withWaterAmounts(waterAmount).withMilkAmount(5).build();
        coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.STANDARD).withType(CoffeType.ESPRESSO).build();
    }

    @Test(expected = NoCoffeeBeansException.class)
    public void grindCoffeeShouldThrowNoCoffeeBeansException(){
        coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.STANDARD).withType(CoffeType.ESPRESSO).build();
        Map<CoffeeSize, Integer> waterAmount = new HashMap<>();
        waterAmount.put(CoffeeSize.STANDARD, 5);
        coffeeReceipe = CoffeeReceipe.builder().withWaterAmounts(waterAmount).withMilkAmount(5).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(false);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeOrder);
    }

    @Test(expected = UnknownCofeeTypeException.class)
    public void validateShouldThrowUnknownCoffeeTypeException(){
        coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.STANDARD).withType(CoffeType.ESPRESSO).build();
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(null);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeOrder);
    }


    @Test
    public void grinderShouldGrindCoffeeBeansOnce(){
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(true);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeMachine.make(coffeOrder);
        verify(grinder, times(1)).grind(Mockito.any());
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}


