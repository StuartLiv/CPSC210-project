package model;

import model.exceptions.InvalidMassException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PortionTest {
    private Ingredient oat;

    @BeforeEach
    void runBefore() {
        oat = new Ingredient(new String[]{"Oats", "100", "333", "11", "73", "3"});
    }

    @Test
    //tests scaling of ingredient, and getters
    void scaleIngredientTest() {
        try {
            Portion OatsScaled = new Portion(oat, 50);
            OatsScaled.scaleIngredient();
            ArrayList<String> expected = new ArrayList<>();
            expected.add("Oats");
            expected.add("50");
            expected.add("167");
            expected.add("6");
            expected.add("37");
            expected.add("2");

            assertEquals(OatsScaled.getMass(), OatsScaled.getIngredient().getServingSize());
            assertEquals(expected, OatsScaled.getIngredient().getFields());
        } catch (InvalidMassException e) {
            fail("Invalid Ingredient");
        }
    }

    @Test
    //test overloaded scaleIngredient method
    void scaleIngredientFactorTest() {
        try {
            Portion OatsScaled = new Portion(oat, 50);
            OatsScaled.scaleIngredient(0.333);
            ArrayList<String> expected = new ArrayList<>();
            expected.add("Oats");
            expected.add("33");
            expected.add("111");
            expected.add("4");
            expected.add("24");
            expected.add("1");

            assertEquals(33, OatsScaled.getIngredient().getServingSize());
            assertEquals(expected, OatsScaled.getIngredient().getFields());
        } catch (InvalidMassException e) {
            fail("Invalid Ingredient");
        }
    }

    @Test
    void invalidMassExceptionTest() {
        try {
            new Portion(oat, -50);
            fail("Exception not triggered");
        } catch (InvalidMassException e) {
            //pass
        }
    }
}
