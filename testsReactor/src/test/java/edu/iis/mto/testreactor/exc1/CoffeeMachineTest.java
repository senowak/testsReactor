package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {

    private CoffeeMachine coffeeMachine;
    private Coffee coffee;
    private CoffeOrder coffeOrder;
    private CoffeeReceipe coffeeReceipe;
    private CoffeeReceipes coffeeReceipes = mock(CoffeeReceipes.class);
    private MilkProvider milkProvider = mock(MilkProvider.class);
    private Grinder grinder = mock(Grinder.class);


    @Before public void setup(){
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        coffeeReceipe.getMilkAmount();
        coffeeReceipe.getWaterAmount(CoffeeSize.DOUBLE);
        coffeeReceipe.withMilk();

        coffeOrder.builder().withType(CoffeType.ESPRESSO);
        coffeOrder.builder().withType(CoffeType.LATTE);
        coffeOrder.builder().withSize(CoffeeSize.DOUBLE);
    }

    @Test public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test public void receipIsNullTest(){
        CoffeOrder.Builder builder = new CoffeOrder.Builder();
        coffeOrder = new CoffeOrder(builder);
        Coffee coffee = coffeeMachine.make(coffeOrder);

        assertEquals(null, coffee);
    }

    @Test public void grindDoubleCoffeTest(){
        CoffeeSize doubleCoffe = CoffeeSize.DOUBLE;
        Mockito.doReturn(true).when(grinder).grind(doubleCoffe);

        CoffeType coffeType = CoffeType.ESPRESSO;

        assertEquals(grinder.grind(doubleCoffe), equals(true));
    }

    @Test public void isMilkTest(){
        CoffeType coffeType = CoffeType.ESPRESSO;
        coffeeReceipe.getMilkAmount();
        Coffee coffee = new Coffee();
    }

}
