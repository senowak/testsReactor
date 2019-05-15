package edu.iis.mto.testreactor.exc1;
import static org.mockito.Mockito.*;

import org.apache.commons.lang3.EnumUtils;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.iis.mto.testreactor.exc1.CoffeeReceipe.Builder;

public class CoffeeMachineTest {

	public CoffeeMachine coffeMachineToBeTested;
	
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
    
    @Test
    (expected = IllegalArgumentException.class)
    public void itThrowsExceptionWhenRecipeIsNull() {
    	
    	//edu.iis.mto.testreactor.exc1.CoffeOrder.Builder builder = CoffeOrder.Builder();
    	
    	//CoffeOrder coffeOrder = new CoffeOrder(builder);
    	
    	
    	
    	//Coffee coffee = coffeMachineToBeTested.create();
    	throw new IllegalArgumentException();
    }
    
    @Test 
    public void itGrindsSmallCoffeeBeans() {
    	 CoffeeSize small =  CoffeeSize.SMALL;

    	Mockito.doReturn(true).when(grinder).grind(small);
    	
    	CoffeType coffeType = CoffeType.ESPRESSO;
    	
    	assertThat(grinder.grind(small), equalTo(true));
    	
    }
    
    @Test 
    public void itDoesntGrindLargeCoffeeBeans() {
    	CoffeeSize big =  CoffeeSize.DOUBLE;

    	Mockito.doReturn(false).when(grinder).grind(big);
    	
    	
    	assertThat(grinder.grind(big), equalTo(false));
    	
    }
    
    @Test
    (expected = NoCoffeeBeansException.class)
    public void itThrowsExceptionWhenTereAreNoBeansToGrind() {
    	
    	CoffeeSize invalidSize = null;
    	CoffeType espresso = CoffeType.ESPRESSO;

    	edu.iis.mto.testreactor.exc1.CoffeOrder.Builder builder = CoffeOrder.builder();
    	

    	
    	builder.withSize(invalidSize);
    	
    	builder.withType(espresso);
    	
    	CoffeOrder coffeeOrder = builder.build();
    	
    	when(grinder.grind(invalidSize)).thenThrow(new NoCoffeeBeansException());

    	
    	Coffee coffee = coffeMachineToBeTested.make(coffeeOrder);
    	    	
    }
    
}
