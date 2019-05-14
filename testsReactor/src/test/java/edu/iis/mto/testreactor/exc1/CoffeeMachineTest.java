package edu.iis.mto.testreactor.exc1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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
    private CoffeeReceipe coffeeReceipe;
    private Map<CoffeeSize, Integer> waterAmounts = Collections.emptyMap();

    @Before
    public void initialize(){
        waterAmounts = new HashMap<>();
        coffeeMachine = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
        waterAmounts.put(CoffeeSize.STANDARD, retutnNotImportantWaterAmount());

        coffeOrder = CoffeOrder.builder().withType(CoffeType.ESPRESSO).withSize(CoffeeSize.STANDARD).build();
        coffeeReceipe = CoffeeReceipe.builder().withMilkAmount(returnNotImportantMilkAmount()).withWaterAmounts(waterAmounts).build();

    }

    private Integer retutnNotImportantWaterAmount() {
        return 10;
    }

    private int returnNotImportantMilkAmount() {
        return 15;
    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test(expected = NoCoffeeBeansException.class)
    public void grindCoffeeGrinderGrindShouldThrowException() {
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(false);
        coffeeMachine.make(coffeOrder);
    }

    @Test
    public void grindCoffeeGrinderGrindShouldBeCalledAtLeastOnce() {
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(true);
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(coffeeReceipe);
        coffeeMachine.make(coffeOrder);
        verify(grinder, atLeastOnce()).grind(CoffeeSize.STANDARD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCoffeeWithNoRecipeShouldThrowException(){
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(true);
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(null);
        coffeeMachine.make(coffeOrder);
    }

    @Test
    public void makeCoffeeShouldReturnCoffeeWithProperFields(){
        Coffee coffee = new Coffee();
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(true);
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(coffeeReceipe);
        coffee.setMilkAmout(returnNotImportantMilkAmount());
        coffee.setWaterAmount(retutnNotImportantWaterAmount());

        Coffee otherCoffee = coffeeMachine.make(coffeOrder);
        Assert.assertThat(otherCoffee.getMilkAmout(), is(equalTo(coffee.getMilkAmout())));
        Assert.assertThat(otherCoffee.getWaterAmount(), is(equalTo(coffee.getWaterAmount())));
    }

    @Test
    public void milkProviderMethodsShouldBeCalledAtLeastOnce(){
        when(grinder.grind(CoffeeSize.STANDARD)).thenReturn(true);
        when(coffeeReceipes.getReceipe(CoffeType.ESPRESSO)).thenReturn(coffeeReceipe);
        coffeeMachine.make(coffeOrder);
        verify(milkProvider, atLeastOnce()).pour(returnNotImportantMilkAmount());
        verify(milkProvider, atLeastOnce()).heat();
    }
    
}
