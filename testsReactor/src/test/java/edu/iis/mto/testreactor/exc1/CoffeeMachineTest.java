package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class) public class CoffeeMachineTest {

    private CoffeeMachine coffeeMachine;
    private CoffeOrder coffeOrder;
    private CoffeeReceipe coffeeWithMilkReceipe;
    private CoffeeReceipe coffeeWithoutMilkReceipe;
    @Mock Grinder grinder;
    @Mock MilkProvider milkProvider;
    @Mock CoffeeReceipes coffeeReceipes;

    @Before
    public void init(){

        HashMap waterAmounts = new HashMap();
        waterAmounts.put(CoffeeSize.STANDARD, 10);

        CoffeeReceipe.Builder coffeeWithMilkReeceipeBuilder = CoffeeReceipe.builder()
                                                                  .withWaterAmounts(waterAmounts)
                                                                  .withMilkAmount(5);

        CoffeeReceipe.Builder coffeWithoutMilkReeceipeBuilder = CoffeeReceipe.builder()
                                                                  .withWaterAmounts(waterAmounts)
                                                                  .withMilkAmount(0);

        CoffeOrder.Builder coffeOrderBuilder = CoffeOrder.builder()
                                                         .withSize(CoffeeSize.STANDARD)
                                                         .withType(CoffeType.ESPRESSO);

        coffeOrder = coffeOrderBuilder.build();
        coffeeWithMilkReceipe = coffeeWithMilkReeceipeBuilder.build();
        coffeeWithoutMilkReceipe = coffeWithoutMilkReeceipeBuilder.build();

        when(grinder.grind(any(CoffeeSize.class))).thenReturn(true);
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeWithoutMilkReceipe);

        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);

    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void methodMakeShouldReturnCoffeeWithCorrectWaterAmount(){

        Coffee coffee = coffeeMachine.make(coffeOrder);

        assertThat(coffee.getWaterAmount(), Is.is(10));

    }

    @Test
    public void methodMakeShouldReturnCoffeeWithCorrectMilkAmount(){

        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeWithMilkReceipe);

        Coffee coffee = coffeeMachine.make(coffeOrder);

        assertThat(coffee.getMilkAmout(), Is.is(5));

    }

    @Test
    public void methodMakeShouldCallMethodGrindOnce(){

        coffeeMachine.make(coffeOrder);

        verify(grinder, times(1)).grind(any(CoffeeSize.class));
        
    }

    @Test
    public void methodMakeShouldCallMethodHeatWhenCoffeeIsWithMilk(){

        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeWithMilkReceipe);

        coffeeMachine.make(coffeOrder);

        verify(milkProvider, times(1)).heat();

    }

    @Test
    public void methodMakeShouldCallMethodPourWhenCoffeeIsWithMilk(){

        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeWithMilkReceipe);

        coffeeMachine.make(coffeOrder);

        verify(milkProvider, times(1)).pour(any(Integer.class));

    }

    @Test
    public void methodMakeShouldNotCallMethodHeatWhenCoffeeIsNotWithMilk(){

        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeWithoutMilkReceipe);

        coffeeMachine.make(coffeOrder);

        verify(milkProvider, times(0)).heat();

    }

    @Test
    public void methodMakeShouldNotCallMethodPourWhenCoffeeIsNotWithMilk(){

        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeWithoutMilkReceipe);

        coffeeMachine.make(coffeOrder);

        verify(milkProvider, times(0)).pour(any(Integer.class));

    }

    @Test
    public void methodMakeShouldCallMethodPourWithCorrectParameter(){

        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeWithMilkReceipe);

        coffeeMachine.make(coffeOrder);

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(milkProvider).pour(argument.capture());

        assertThat(argument.getValue(), Is.is(5));

    }

}
