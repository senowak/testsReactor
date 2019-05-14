package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {

    private CoffeeMachine coffeeMachine;
    private CoffeeSize small = CoffeeSize.SMALL;
    private CoffeType capuccino = CoffeType.CAPUCCINO;
    private int milkAmount = 2;
    private int waterAmount = 5;

    @Mock
    private Coffee coffee;
    @Mock
    private Grinder grinder;
    @Mock
    private MilkProvider milkProvider;
    @Mock
    private CoffeeReceipes coffeeReceipes;
    @Mock
    private CoffeOrder coffeOrder;
    @Mock
    private CoffeeReceipe coffeeReceipe;

    @Before
    public void initialize() {
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        when(grinder.grind(any())).thenReturn(true);

        HashMap hm = new HashMap();
        hm.put(small, waterAmount);

        CoffeeReceipe.Builder coffeeReceipeBuilder = new CoffeeReceipe.Builder();
        coffeeReceipeBuilder.withMilkAmount(milkAmount);
        coffeeReceipeBuilder.withWaterAmounts(hm);
        coffeeReceipe = coffeeReceipeBuilder.build();

        when(coffeeReceipes.getReceipe(any())).thenReturn(coffeeReceipe);
    }
    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test public void make() {

        CoffeOrder.Builder coffeOrderBuilder = new CoffeOrder.Builder();
        coffeOrderBuilder.withSize(small);
        coffeOrderBuilder.withType(capuccino);

        coffeOrder = coffeOrderBuilder.build();
        when(coffeeReceipes.getReceipe(any())).thenReturn(coffeeReceipe);

        Coffee coffee = coffeeMachine.make(coffeOrder);
    }


    @Test public void makeReturnCoffeeThatMilkAmountEqualsTo2() {

        CoffeOrder.Builder coffeOrderBuilder = new CoffeOrder.Builder();
        coffeOrderBuilder.withSize(small);
        coffeOrderBuilder.withType(capuccino);

        coffeOrder = coffeOrderBuilder.build();
        when(coffeeReceipes.getReceipe(any())).thenReturn(coffeeReceipe);

        Coffee coffee = coffeeMachine.make(coffeOrder);
        Assert.assertThat(coffee.getMilkAmout(), Matchers.equalTo(milkAmount));
    }

    @Test public void makeReturnCoffeeThatWaterAmountEqualsToGivenValue() {

        CoffeOrder.Builder coffeOrderBuilder = new CoffeOrder.Builder();
        coffeOrderBuilder.withSize(small);
        coffeOrderBuilder.withType(capuccino);

        coffeOrder = coffeOrderBuilder.build();
        when(coffeeReceipes.getReceipe(any())).thenReturn(coffeeReceipe);

        Coffee coffee = coffeeMachine.make(coffeOrder);
        Assert.assertThat(coffee.getWaterAmount(), Matchers.equalTo(waterAmount));
    }


    @Test public void MilkProviderInterfaceMethodShouldBeCalledOneTime() {

        CoffeOrder.Builder coffeOrderBuilder = new CoffeOrder.Builder();
        coffeOrderBuilder.withSize(small);
        coffeOrderBuilder.withType(capuccino);

        coffeOrder = coffeOrderBuilder.build();
        when(coffeeReceipes.getReceipe(any())).thenReturn(coffeeReceipe);

        Coffee coffee = coffeeMachine.make(coffeOrder);
        verify(milkProvider, times(1)).pour(milkAmount);

    }

}
