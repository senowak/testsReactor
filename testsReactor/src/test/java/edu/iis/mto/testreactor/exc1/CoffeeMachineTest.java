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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class) public class CoffeeMachineTest {

    private CoffeeMachine coffeeMachine;
    private CoffeOrder coffeOrder;
    @Mock Grinder grinder;
    @Mock MilkProvider milkProvider;
    @Mock CoffeeReceipes coffeeReceipes;

    @Before
    public void init(){

        HashMap waterAmounts = new HashMap();
        waterAmounts.put(CoffeeSize.STANDARD, 10);

        CoffeeReceipe.Builder coffeReeceipeBuilder = CoffeeReceipe.builder()
                                                                  .withWaterAmounts(waterAmounts)
                                                                  .withMilkAmount(5);

        CoffeOrder.Builder coffeOrderBuilder = CoffeOrder.builder()
                                                         .withSize(CoffeeSize.STANDARD)
                                                         .withType(CoffeType.ESPRESSO);

        coffeOrder = coffeOrderBuilder.build();
        CoffeeReceipe coffeeReceipe = coffeReeceipeBuilder.build();

        when(grinder.grind(any(CoffeeSize.class))).thenReturn(true);
        when(coffeeReceipes.getReceipe(any(CoffeType.class))).thenReturn(coffeeReceipe);

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

        Coffee coffee = coffeeMachine.make(coffeOrder);

        assertThat(coffee.getMilkAmout(), Is.is(5));

    }

    @Test
    public void methodMakeShouldCallMethodGrindOnce(){

        coffeeMachine.make(coffeOrder);

        verify(grinder, times(1)).grind(any(CoffeeSize.class));
        
    }

}
