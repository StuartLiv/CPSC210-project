package model;

import model.exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MealTest {
    private Meal morningOatmeal;
    private Recipe oatmeal;

    @BeforeEach
    void runBefore() {
        try {
            ArrayList<Portion> ingredients = new ArrayList<>();
            ingredients.add(new Portion(
                    new Ingredient("Oats", 100, 333, 11, 73, 3), 156));
            ingredients.add(new Portion(
                    new Ingredient("Milk", 250, 90, 9, 12, 0), 125));
            oatmeal = new Recipe(ingredients, "Overnight Oats");
            morningOatmeal = new Meal(oatmeal, 281, 7);
        } catch (Exception e) {
            fail("Invalid runBefore initialization");
        }
    }

    //Getter tests, as these will be methods for phase 2 and task 5

    @Test
    void getRecipeTest() {
        assertEquals(oatmeal, morningOatmeal.getRecipe());
    }

    @Test
    void getTimeTest() {
        assertEquals(7, morningOatmeal.getTime());
    }

    @Test
    void getTotalTest() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Overnight Oats");
        expected.add("281");
        expected.add("564");
        expected.add("22");
        expected.add("120");
        expected.add("5");
        assertEquals(expected, morningOatmeal.getTotal().getIngredient().getFields());
    }

    @Test
    void getScaledTotalTest() {
        try {
            Meal halfOatmeal = (new Meal(oatmeal, 140, 8));
            ArrayList<String> expected = new ArrayList<>();
            expected.add("Overnight Oats");
            expected.add("140");
            expected.add("281");
            expected.add("11");
            expected.add("60");
            expected.add("2");
            assertEquals(expected, halfOatmeal.getTotal().getIngredient().getFields());
        } catch (InvalidInputException e) {
            fail("Invalid oatmeal test recipe");
        }
    }

    @Test
    void getMassTest() {
        assertEquals(281, morningOatmeal.getMass());
    }
}