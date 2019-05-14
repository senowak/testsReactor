package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
    }


}
