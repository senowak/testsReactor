package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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


    @Before
    public void init(){
        coffee = new Coffee();
        grinder = mock(Grinder.class);
        milkProvider = mock(MilkProvider.class);
        coffeeReceipes = mock(CoffeeReceipes.class);
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}
