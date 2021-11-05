package model;

import model.exceptions.InvalidInputException;
import model.exceptions.InvalidMassException;
import model.exceptions.InvalidNutritionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {
    private Profile profile;
    private final ArrayList<Portion> portions = new ArrayList<>();

    @BeforeEach
    void runBefore() {
        try {
            profile = new Profile("./data/demo/");
            portions.add(new Portion(
                    new Ingredient("Oats", 100, 333, 11, 73, 3), 156));
            portions.add(new Portion(
                    new Ingredient("Milk", 250, 90, 9, 12, 0), 125));
        } catch (IOException | InvalidNutritionException | InvalidMassException e) {
            fail();
        }
    }

    @Test
    void ingredientTests() {
        try {
            assertEquals(0, profile.getIngredientList().size());
            profile.addIngredient(new Ingredient("Test", 100, 100, 100, 100, 100));
            assertEquals("Test", profile.findIngredient("Test").getIngredientName());
            profile.deleteIngredient("Test");
            assertEquals(0, profile.getIngredientList().size());
        } catch (InvalidNutritionException e) {
            fail();
        }
    }

    @Test
    void recipeTests() {
        try {
            assertEquals(0, profile.getRecipeBook().size());
            profile.addRecipe(new Recipe(portions, "Overnight Oats"));
            assertEquals("Overnight Oats", profile.findRecipe("Overnight Oats").getName());
            profile.deleteRecipe("Overnight Oats");
            assertEquals(0, profile.getRecipeBook().size());
        } catch (InvalidInputException e) {
            fail();
        }
    }

    @Test
    void mealTests() {
        try {
            Meal testMeal = new Meal((new Recipe(portions, "Test")), 281, "today", "00:00");
            assertEquals(0, profile.getTracker().size());
            profile.addMeal(testMeal);
            assertEquals(testMeal, profile.getTracker().get(0));
            profile.deleteMeal(testMeal);
            assertEquals(0, profile.getTracker().size());
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
    }
}
