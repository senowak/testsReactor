package edu.iis.mto.testreactor.exc1;

import static org.mockito.Mockito.mock;

import org.junit.Before;

public class CoffeeMachineTest {

    private CoffeeMachine mach;
    private MilkProvider milkProvider;
    private CoffeeReceipes coffeeReceipes;
    private CoffeOrder kawa;
    private Grinder grinder;
    private CoffeType coffeType;
    private CoffeeSize coffeeSize;

    @Before
    public void init() {
        coffeType = CoffeType.CAPUCCINO;
        coffeeSize = CoffeeSize.DOUBLE;
        kawa = kawa.builder()
                   .withSize(coffeeSize)
                   .withType(coffeType)
                   .build();
        grinder = mock(Grinder.class);
        milkProvider = mock(MilkProvider.class);
        coffeeReceipes = mock(CoffeeReceipes.class);
        mach = new CoffeeMachine(grinder, milkProvider, coffeeReceipes);
    }

    @org.junit.Test(expected = NoCoffeeBeansException.class)
    public void ZiarenNieMaWmaszynie() {
        Coffee kawas = mach.make(kawa);
    }

    /*
     * @Test public void asdasd() { Coffee kawa = mach.make(any(CoffeOrder.class)); verify(grinder,
     * times(0)).grind(any(CoffeeSize.class));
     *
     * }
     */

};
