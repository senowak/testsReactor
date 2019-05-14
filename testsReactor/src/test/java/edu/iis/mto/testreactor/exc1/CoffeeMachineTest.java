package edu.iis.mto.testreactor.exc1;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoffeeMachineTest {

    private CoffeeReceipe receipe;
    private Grinder grinder;
    private MilkProvider milkProvider;
    private CoffeeReceipe receipes;
    private CoffeeReceipe.Builder builder;
    private CoffeOrder order;
    private Coffee coffee;
    private CoffeeMachine machine;

    @Before
    public void initialize(){

        coffee = new Coffee();

        receipes = mock(CoffeeReceipe.class);
        when(receipes.getReceipe(order.getType()).thenReturn(order.getSize().SMALL));

        order = new CoffeeOrder();
        machine = new CoffeeMachine(CoffeeSize.SMALL, milkProvider.heat(), receipes);

    }

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}
