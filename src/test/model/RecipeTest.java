package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    private ArrayList<Portion> portions = new ArrayList<>();

    @BeforeEach
    void runBefore() {
        portions.add(new Portion (
                new Ingredient("Oats", 100, 333, 11, 73, 3 ), 156));
        portions.add(new Portion (
                new Ingredient("Milk", 250, 90, 9, 12, 0 ), 125));

    }

    @Test
    //Tests name retrieval
    void getNameTest() {
        Recipe oatmeal = new Recipe(portions, "Overnight Oats");
        assertEquals("Overnight Oats", oatmeal.getName());
    }

    @Test
    //test if loop in constructor, and getToSave
    void getToSaveTest() {
        Recipe temp = new Recipe(portions, "Unsaved Recipe");
        assertEquals("Unsaved Recipe", temp.getName());
        assertFalse(temp.getToSave());
    }

    @Test

    void getIngredientsTest() {
        Recipe oatmeal = new Recipe(portions, "Overnight Oats");
        assertEquals(oatmeal.getIngredients(), portions);
    }

    @Test
    //Tests the getTotal method
    void testGetTotal() {
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
    }
}
