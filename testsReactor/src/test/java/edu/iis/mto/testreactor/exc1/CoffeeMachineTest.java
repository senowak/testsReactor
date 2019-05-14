package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)public class CoffeeMachineTest {
    Coffee coffee;
    CoffeOrder coffeOrder;
    CoffeeMachine coffeeMachine;


    @Mock
    CoffeeReceipes coffeeReceipes;

    @Mock
    Grinder grinder;

    @Mock
    MilkProvider milkProvider;

    @Before public void setup() {
        coffee = new Coffee();
        coffeeMachine = new CoffeeMachine(grinder,milkProvider,coffeeReceipes);
    }


    @Test(expected = NoCoffeeBeansException.class)

    public void shouldThrowNoCoffeeBeansExceptionWhenThereIsNoCoffee(){
         coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO)
                                          .build();
        Mockito.when(grinder.grind(CoffeeSize.SMALL)).thenReturn(false);
        coffeeMachine.make(coffeOrder);
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}

