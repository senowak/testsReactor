package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    @Before
    public void init(){
        coffee = new Coffee();
        grinder = mock(Grinder.class);
        milkProvider = mock(MilkProvider.class);
        coffeeReceipes = mock(CoffeeReceipes.class);
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

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}
