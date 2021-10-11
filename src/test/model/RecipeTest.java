package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    private ArrayList<Portion> ingredients = new ArrayList<>();

    @BeforeEach
    void runBefore() {
        ingredients.add(new Portion (
                new Ingredient("Oats", 100, 333, 11, 73, 3 ), 156));
        ingredients.add(new Portion (
                new Ingredient("Milk", 250, 90, 9, 12, 0 ), 125));

    }

    @Test
    //Tests name retrieval
    void getNameTest() {
        Recipe oatmeal = new Recipe(ingredients, "Overnight Oats");
        assertEquals("Overnight Oats", oatmeal.getName());
    }

    @Test
    //test if loop in constructor, and getToSave
    void getToSaveTest() {
        Recipe temp = new Recipe(ingredients, "temp");
        assertEquals("temp", temp.getName());
        assertFalse(temp.getToSave());
    }

    @Test
    //Tests the getTotal method
    void testGetTotal() {
        Recipe oatmeal = new Recipe(ingredients, "Overnight Oats");
        assertEquals("Overnight Oats", oatmeal.getName());

        Portion sum = oatmeal.getTotal();

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Sum");
        expected.add("281");
        expected.add("564");
        expected.add("22");
        expected.add("120");
        expected.add("5");

        assertEquals(expected, sum.getIngredient().getFields());
    }
}
