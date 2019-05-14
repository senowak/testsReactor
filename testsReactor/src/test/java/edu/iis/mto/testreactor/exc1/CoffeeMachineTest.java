package edu.iis.mto.testreactor.exc1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {

    @Mock
    private Grinder grinder;
    @Mock
    private MilkProvider milkProvider;
    @Mock
    private CoffeeReceipes coffeeReceipes;

    private CoffeeMachine coffeeMachine;
    private CoffeOrder coffeOrder;

    @Before
    public void initialize(){
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);

        coffeOrder = CoffeOrder.builder().withType(CoffeType.ESPRESSO).withSize(CoffeeSize.STANDARD).build();

    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test(expected = NoCoffeeBeansException.class)
    public void grindCoffeeGrinderGrindShouldBeCalledOnce() {
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(false);
        coffeeMachine.make(coffeOrder);
        verify(grinder, atLeastOnce()).grind(CoffeeSize.STANDARD);
    }
}
