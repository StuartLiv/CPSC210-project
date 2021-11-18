package model;

import model.exceptions.InvalidInputException;
import model.exceptions.InvalidMassException;
import model.exceptions.NoRecipeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
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
            morningOatmeal = new Meal(oatmeal, 281, "today", "07:30");
        } catch (Exception e) {
            fail("Invalid runBefore initialization");
        }
    }

    @Test
    void invalidMassTest() {
        try {
            morningOatmeal = new Meal(oatmeal, -5, "today", "00:00");
            fail("No exception");
        } catch (InvalidMassException e) {
            //pass
        } catch (Exception e) {
            fail("Wrong exception");
        }
    }

    @Test
    void invalidRecipeTest() {
        try {
            new Meal(null, 281, "today", "00:00");
            fail("No exception");
        } catch (NoRecipeException e) {
            //pass
        } catch (Exception e) {
            fail("Wrong exception");
        }
    }

    @Test
    void invalidDateTest() {
        try {
            morningOatmeal = new Meal(oatmeal, 281, "yesterday", "00:00");
            fail("No exception");
        } catch (InvalidInputException e) {
            fail("Mass exception not dateTime");
        } catch (DateTimeException e) {
            //pass
        }
    }

    @Test
    void invalidTimeTest() {
        try {
            morningOatmeal = new Meal(oatmeal, 281, "today", "25:62");
            fail("No exception");
        } catch (InvalidInputException e) {
            fail("Mass exception not dateTime");
        } catch (DateTimeException e) {
            //pass
        }
    }

    @Test
    void getRecipeTest() {
        assertEquals(oatmeal, morningOatmeal.getRecipe());
    }

    @Test
    void getTimeStringTest() {
        assertEquals("07:30", morningOatmeal.getTimeString());
    }


    @Test
    void getDateStringTest() {
        try {
            morningOatmeal = new Meal(oatmeal, 281, "2020-01-01", "07:00");
            assertEquals("2020-01-01", morningOatmeal.getDateString());
        } catch (InvalidInputException e) {
            fail("Inappropriate mass exception");
        }

    }

    @Test
    void getDateTimeString() {
        try {
            morningOatmeal = new Meal(oatmeal, 281, "2020-01-01", "07:00");
            assertEquals("2020-01-01T07:00", morningOatmeal.getDateTimeString());
        } catch (InvalidInputException e) {
            fail("Inappropriate mass exception");
        }

    }

    @Test
    void getNameTest() {
        try {
            morningOatmeal = new Meal(oatmeal, 281, "2020-01-01", "07:00");
            assertEquals("Overnight Oats at 2020-01-01T07:00", morningOatmeal.getName());
        } catch (InvalidInputException e) {
            fail("Inappropriate mass exception");
        }
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
            Meal halfOatmeal = (new Meal(oatmeal, 140, "today", "07:00"));
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