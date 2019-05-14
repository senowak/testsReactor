package edu.iis.mto.testreactor.exc1;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoffeeMachineTest {
    
    private CoffeeMachine coffeeMachine;
    private Grinder grinder;
    private MilkProvider milkProvider;
    private CoffeeReceipes coffeeReceipes;
    private CoffeOrder coffeOrder;
    private Coffee coffee;
    
    //private List<Coffee> coffees;
    
    @Before
    public void init() {
        grinder = mock(Grinder.class);
        milkProvider = mock(MilkProvider.class);
        coffeeReceipes = mock(CoffeeReceipes.class);
        //coffeOrder = mock(CoffeOrder.class);
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        
        //coffee = new Coffee();
        
        //when (coffeeMachine.make(any(CoffeOrder.class))).thenReturn(coffees.add(coffee));
        //when (coffeeMachine.make(any(CoffeOrder.class))).thenReturn(new Coffee());
        
    }

    @Test
    public void espressoTest() {
        coffeOrder.builder().withType(CoffeType.ESPRESSO);
        coffee = coffeeMachine.make(coffeOrder);
        assertThat(coffeOrder.getType(), CoffeType.ESPRESSO);
    }

}
