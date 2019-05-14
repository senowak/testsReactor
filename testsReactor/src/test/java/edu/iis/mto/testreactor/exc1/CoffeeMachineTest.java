package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

public class CoffeeMachineTest {

    Grinder grinder;// = (Grinder) new Object();
    MilkProvider milkProvider;// = (MilkProvider) new Object();
    CoffeeReceipes coffeeReceipe;// = (CoffeeReceipe) new Object();

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void aCoffeeMachineWithNullTools() {
        CoffeeMachine coffeeMachine;
        try {
            coffeeMachine = new CoffeeMachine(grinder, milkProvider,coffeeReceipe);
            fail();
        }catch(NullPointerException e) {
            //success
        }
    }


}
