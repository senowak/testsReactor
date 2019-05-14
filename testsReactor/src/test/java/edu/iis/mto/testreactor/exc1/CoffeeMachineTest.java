package edu.iis.mto.testreactor.exc1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMachineTest {

    @Mock
    Grinder grinder;
    @Mock
    CoffeeReceipes receipes;
    @Mock
    MilkProvider milkProvider;

    private Map<CoffeeSize, Integer> coffeeSizes;
    private CoffeeMachine machine;

    @Before
    public void setUp() {
        coffeeSizes = new HashMap<>();
        coffeeSizes.put(CoffeeSize.SMALL, 1);
        coffeeSizes.put(CoffeeSize.STANDARD, 2);
        coffeeSizes.put(CoffeeSize.DOUBLE, 4);
        machine = new CoffeeMachine(grinder, milkProvider, receipes);
    }
    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

}
