package edu.iis.mto.testreactor.exc1;

//import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;

import org.junit.Test;
import org.mockito.verification.VerificationMode;

import java.util.HashMap;
import java.util.Map;

public class CoffeeMachineTest {

    private CoffeeMachine coffeeMachine;
    private Coffee coffee;
    private CoffeeReceipe coffeeReceipe;
    private CoffeOrder coffeOrder;
    private Grinder grinder;
    private MilkProvider milkProvider;
    private CoffeeReceipes receipes;

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Before
    public void init(){


        mock(Grinder.class);
        grinder = mock(Grinder.class);
        when((grinder).grind(CoffeeSize.SMALL)).thenReturn(true);
        mock(MilkProvider.class);
        milkProvider = mock(MilkProvider.class);


        Map<CoffeeSize, Integer> waterAmounts = new HashMap<CoffeeSize, Integer>();
        waterAmounts.put(CoffeeSize.SMALL,1);
        CoffeeReceipe.Builder builder= new CoffeeReceipe.Builder();

        builder.withMilkAmount(2);
        builder.withWaterAmounts(waterAmounts);
        coffeeReceipe= new CoffeeReceipe(builder);

        mock(CoffeeReceipes.class);
        receipes = mock(CoffeeReceipes.class);
        when(receipes.getReceipe(CoffeType.CAPUCCINO)).thenReturn(coffeeReceipe);


        coffeeMachine = new CoffeeMachine(grinder,milkProvider,receipes);
        CoffeOrder.Builder builder1 = new CoffeOrder.Builder();
        builder1.withType(CoffeType.CAPUCCINO);
        builder1.withSize(CoffeeSize.SMALL);
        coffeOrder = builder1.build();
    }

    @Test
    public void choise_coffee_with_2_milkAmount_and_1_waterAmount_should_return_coffe_with_2_milkAmount_and_1_waterAmount(){

        coffee= coffeeMachine.make(coffeOrder);
        assertEquals(2,coffee.getMilkAmout());
        assertEquals(1,coffee.getWaterAmount());
    }


    @Test(expected = edu.iis.mto.testreactor.exc1.NoCoffeeBeansException.class)
    public void when_coffe_is_too_large_then_make_should_throw_error(){
        CoffeOrder.Builder builder1 = new CoffeOrder.Builder();
        builder1.withType(CoffeType.CAPUCCINO);
        builder1.withSize(CoffeeSize.STANDARD);
        coffeOrder = builder1.build();
        coffeeMachine.make(coffeOrder);

    }

}
