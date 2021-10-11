package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MealTest {
    private Meal morningOatmeal;
    private Recipe oatmeal;

    //Getter tests, as these will be methods for phase 2 and task 5

    @BeforeEach
    void runBefore() {
        ArrayList<Portion> ingredients = new ArrayList<>();
        ingredients.add(new Portion (
                new Ingredient("Oats", 100, 333, 11, 73, 3 ), 156));
        ingredients.add(new Portion (
                new Ingredient("Milk", 250, 90, 9, 12, 0 ), 125));
        oatmeal = new Recipe(ingredients, "Overnight Oats");
        morningOatmeal = new Meal(oatmeal, 281, 7);
    }

    @Test
    void getRecipeTest() {
        assertEquals(oatmeal, morningOatmeal.getRecipe());
    }

    @Test
    void getTime() {
        assertEquals(7, morningOatmeal.getTime());
    }

    @Test
    void getTotal() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Sum");
        expected.add("281");
        expected.add("564");
        expected.add("22");
        expected.add("120");
        expected.add("5");
        assertEquals(expected, morningOatmeal.getTotal().getIngredient().getFields());
    }

    @Test
    void getMass() {
        assertEquals(281, morningOatmeal.getMass());
    }
}