package edu.iis.mto.testreactor.exc1;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {

    @Mock
    private Grinder grinder;
    @Mock
    private MilkProvider milkProvider;
    @Mock
    private CoffeeReceipes coffeeReceipes;

    private CoffeeMachine coffeeMachine;
    private CoffeeReceipe coffeeReceipe;
    private CoffeeReceipe.Builder builder;
    private CoffeOrder coffeOrder;

    @Before
    public void init(){
        grinder = mock(Grinder.class);
        milkProvider = mock(MilkProvider.class);
        coffeeReceipes = mock(CoffeeReceipes.class);
        coffeeMachine = new CoffeeMachine(grinder,milkProvider,coffeeReceipes);
        coffeOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.CAPUCCINO).build();

    }

    @Test
    public void testIfCoffeTypeAndSizeIsCorrect(){
        Assert.assertEquals(coffeOrder.getType(),CoffeType.CAPUCCINO);
        Assert.assertEquals(coffeOrder.getSize(),CoffeeSize.SMALL);
    }


}
