package model;

import model.exceptions.InvalidNutritionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {
    private Ingredient oat;

    @BeforeEach
    void runBefore() {
        try {
            oat = new Ingredient("Oats", 100, 333, 11, 73, 3 );
        } catch (InvalidNutritionException e) {
            fail("Invalid test ingredient");
        }
    }


    @Test
    //Test getFields, indirectly testing all the other getters
    void getFieldsTest() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Oats");
        expected.add("100");
        expected.add("333");
        expected.add("11");
        expected.add("73");
        expected.add("3");

        ArrayList<String> actual = oat.getFields();

        assertEquals(expected, actual);
    }

    @Test
    //Exception test
    void listIngredientExceptionTest() {
        try {
            new Ingredient(new String[]{"Oats", "100", "333", "no", "73", "3"});
            fail("NumberFormatException not triggered");
        } catch (NumberFormatException ignore) {
            //pass
        } catch (InvalidNutritionException e) {
            fail("Wrong exception");
        }
    }

    @Test
    void invalidNutritionException() {
        try {
            new Ingredient("Test", -100, 100, 100, 100, 100);
            fail("Exception not thrown");
        } catch (InvalidNutritionException e) {
            //pass
        }
    }

    @Test
    void invalidNutritionExceptionOverload() {
        try {
            new Ingredient(new String[]{"Oats", "-100", "333", "100", "73", "3"});
            fail("Exception not thrown");
        } catch (InvalidNutritionException e) {
            //pass
        }
    }
}