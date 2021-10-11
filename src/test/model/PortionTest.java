package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortionTest {

    @Test
    //tests scaling of ingredient, and getters
    void scaleIngredientTest() {
        Ingredient oat = new Ingredient("Oats", 100, 333, 11, 73, 3 );
        Portion OatsScaled = new Portion(oat, 50);
        OatsScaled.scaleIngredient();

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Oats");
        expected.add("50");
        expected.add("167");
        expected.add("6");
        expected.add("37");
        expected.add("2");

        assertEquals(expected, OatsScaled.getIngredient().getFields());
    }
}
