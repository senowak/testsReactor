package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    CoffeeReceipes coffeeReceipes = mock(CoffeeReceipes.class);
    MilkProvider milkProvider = mock(MilkProvider.class);
    Grinder grinder = mock(Grinder.class);


    @Before public void setup(){
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);

        coffeeReceipe = new CoffeeReceipe(CoffeeReceipe.builder());
        coffeeReceipe.getMilkAmount();
        coffeeReceipe.getWaterAmount(CoffeeSize.DOUBLE);
        coffeeReceipe.withMilk();

        when(coffeeReceipes.getReceipe(CoffeType.LATTE)).thenReturn(coffeeReceipe);

        coffeOrder.builder().build();
    }

    @Test public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test public void sizeTest(){
        coffee = coffeeMachine.make(coffeOrder);

        assertEquals(2, coffee.getMilkAmout());
        assertEquals(1, coffee.getWaterAmount());
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
        CoffeType coffeType = CoffeType.LATTE;
        Mockito.doReturn(true).when(milkProvider).pour(2);

        assertEquals(true, coffeType);
    }

}
