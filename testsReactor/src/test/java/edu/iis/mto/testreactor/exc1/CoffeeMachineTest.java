package edu.iis.mto.testreactor.exc1;
import static org.mockito.Mockito.*;



import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CoffeeMachineTest {

	CoffeeMachine coffeMachineToBeTested;
	
    Grinder grinder = Mockito.mock(Grinder.class);
    MilkProvider milkProvider =  Mockito.mock(MilkProvider.class);
    CoffeeReceipes receipes = Mockito.mock(CoffeeReceipes.class);
	
	@Before
	public void setUpMethod(){
		coffeMachineToBeTested = new CoffeeMachine(grinder, milkProvider, receipes);
		
		
	}
	
    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }
    

}
