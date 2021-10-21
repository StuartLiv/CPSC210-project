package model;

import model.exceptions.InvalidInputException;
import model.exceptions.NoIngredientsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    private final ArrayList<Portion> portions = new ArrayList<>();

    @BeforeEach
    void runBefore() {
        try {
            portions.add(new Portion(
                    new Ingredient("Oats", 100, 333, 11, 73, 3), 156));
            portions.add(new Portion(
                    new Ingredient("Milk", 250, 90, 9, 12, 0), 125));
        } catch (InvalidInputException e) {
            fail("Invalid runBefore");
        }

    }

    @Test
    //Tests name retrieval
    void getNameTest() {
        try {
            Recipe oatmeal = new Recipe(portions, "Overnight Oats");
            assertEquals("Overnight Oats", oatmeal.getName());
        } catch (NoIngredientsException e) {
            fail("portions is uninitialized");
        }
    }

    @Test
    //test if loop in constructor, and getToSave
    void getToSaveTest() {
        try {
            Recipe temp = new Recipe(portions, "Unsaved Recipe");
            assertEquals("Unsaved Recipe", temp.getName());
            assertFalse(temp.getToSave());
        } catch (NoIngredientsException e) {
            fail("portions is uninitialized");
        }
    }

    @Test
    //test ingredients getter
    void getIngredientsTest() {
        try {
            Recipe oatmeal = new Recipe(portions, "Overnight Oats");
            assertEquals(oatmeal.getIngredients(), portions);
        } catch (NoIngredientsException e) {
            fail("portions is uninitialized");
        }
    }

    @Test
    //Tests the getTotal method
    void testGetTotal() {
        try {
            Recipe oatmeal = new Recipe(portions, "Overnight Oats");
            assertEquals("Overnight Oats", oatmeal.getName());
            Portion sum = oatmeal.getTotal();

            ArrayList<String> expected = new ArrayList<>();
            expected.add("Overnight Oats");
            expected.add("281");
            expected.add("564");
            expected.add("22");
            expected.add("120");
            expected.add("5");

            assertEquals(expected, sum.getIngredient().getFields());
        } catch (NoIngredientsException e) {
            fail("portions is uninitialized");
        }
    }
}
