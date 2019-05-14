package edu.iis.mto.testreactor.exc1;



import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CoffeeMachineTest {

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }
    
    @Test
    public void testIfCreateMethodReturnsCoffe() {
        Coffee coffee = new Coffee();
        CoffeeReceipe receipe = receipes.getReceipe(order.getType());
        
    }
}
