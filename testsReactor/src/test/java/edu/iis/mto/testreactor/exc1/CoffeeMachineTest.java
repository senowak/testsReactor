package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class CoffeeMachineTest {

    Grinder grinder;
    MilkProvider milkProvider;
    CoffeeReceipes coffeeReceipes;
    CoffeeReceipe questionableReceipe;
    CoffeeMachine coffeeMachine;
    CoffeOrder myLittleOrder;
    Coffee javaCoffee;

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
    public void coffeOrderBuilderWithSizeAndTypeTest() {
        myLittleOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        assertThat(myLittleOrder.getType(),is(equalTo(CoffeType.ESPRESSO)));
        assertThat(myLittleOrder.getSize(),is(equalTo(CoffeeSize.SMALL)));
    }

    @Test
    public void coffeeReceipeBuilderTest() {
        Map<CoffeeSize, Integer> holyWater = new TreeMap<>();
        holyWater.put(CoffeeSize.SMALL, 1);
        questionableReceipe = CoffeeReceipe.builder().withWaterAmounts(holyWater).build();
        assertThat(questionableReceipe,is(not(equalTo(null))));
    }

//    @Test
//    public void noCoffeeBeansExceptionTest(){
//
//    }

    @Test
    public void coffeeMachineMakeCoffeeWithCorrectOrderTest(){
        grinder = new GenericGrinder();
        milkProvider = new GenericMilkProvider();
        coffeeReceipes = new CasualCoffeeReceipes();
        try {
            coffeeMachine = new CoffeeMachine(grinder, milkProvider,coffeeReceipes);
            //success
        }catch(NullPointerException e) {
            fail();
        }

        myLittleOrder = CoffeOrder.builder().withSize(CoffeeSize.SMALL).withType(CoffeType.ESPRESSO).build();
        javaCoffee = coffeeMachine.make(myLittleOrder);
        assertThat(javaCoffee,is(not(equalTo(null))));
    }

    class GenericGrinder implements Grinder {
        boolean beBeansAroundHere = true;
        @Override public boolean grind(CoffeeSize size) {
            if(beBeansAroundHere)
                return true;
            else
                return false;
        }
        public void beansBegone()
        {
            beBeansAroundHere = false;
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
        CoffeeReceipe questionableReceipe;
        @Override public CoffeeReceipe getReceipe(CoffeType type) {
            Map<CoffeeSize, Integer> holyWater = new TreeMap<>();
            holyWater.put(CoffeeSize.SMALL, 1);
            questionableReceipe = CoffeeReceipe.builder().withWaterAmounts(holyWater).build();
            return questionableReceipe;
        }
    }
}
