package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

public class CoffeeMachineTest {

    Grinder grinder;
    MilkProvider milkProvider;
    CoffeeReceipes coffeeReceipes;
    CoffeeMachine coffeeMachine;
    CoffeOrder myLittleOrder;

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void aCoffeeMachineWithNullTools() {
        try {
            coffeeMachine = new CoffeeMachine(grinder, milkProvider,coffeeReceipes);
            fail();
        }catch(NullPointerException e) {
            //success
        }
    }

    @Test
    public void aCoffeeMachineWithGoodToolsTest(){
        grinder = new GenericGrinder();
        milkProvider = new GenericMilkProvider();
        coffeeReceipes = new CasualCoffeeReceipes();
        try {
            coffeeMachine = new CoffeeMachine(grinder, milkProvider,coffeeReceipes);
            //success
        }catch(NullPointerException e) {
            fail();
        }
    }

    @Test
    public void CoffeOrderBuilderWithSizeAndTypeTest() {
        myLittleOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        assertThat(myLittleOrder.getType(),is(equalTo(CoffeType.ESPRESSO)));
        assertThat(myLittleOrder.getSize(),is(equalTo(CoffeeSize.SMALL)));
    }

//    @Test
//    public void CoffeeMachineMakeCoffeWithCorrectOrderTest(){
//
//    }

    class GenericGrinder implements Grinder {

        @Override public boolean grind(CoffeeSize size) {
            if(size == CoffeeSize.SMALL)
                return false;
            else
                return true;
        }
    }

    class GenericMilkProvider implements MilkProvider {

        @Override public void heat() {
            System.out.println("Burn baby burn!");
        }

        @Override public void pour(int milkAmount) {
            System.out.println("One Milk with Coffee please!");
        }
    }

    class CasualCoffeeReceipes implements CoffeeReceipes {

        @Override public CoffeeReceipe getReceipe(CoffeType type) {
            return null;
        }
    }
}
