package edu.iis.mto.testreactor.exc1;

import static edu.iis.mto.testreactor.exc1.CoffeeSize.SMALL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static sun.misc.MessageUtils.where;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {

    @Mock
    Grinder grinder;
    @Mock
    MilkProvider milkProvider;
    @Mock
    CoffeeReceipes receipes;
    CoffeeMachine coffeeMachine= new CoffeeMachine(grinder,milkProvider,receipes);
    Coffee coffee=new Coffee();
    CoffeOrder.Builder coffeeBuilder;
    CoffeeSize   coffeeSize;
    CoffeType coffeType;

//    @Before
//    public void setUp() {
//        coffeeMachine= new CoffeeMachine(grinder,milkProvider,receipes);
//
//    }

    @Test
    public void testmakeCoffee(){
        coffee=coffeeMachine.make(CoffeOrder.builder().build());
        when(grinder.grind(CoffeeSize.SMALL)).thenReturn(true);
//        Assert.assertThat(coffee.getWaterAmount(),is(-1));
//        Assert.assertThat(coffee.getMilkAmout(),is(-1));



    }



    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}
