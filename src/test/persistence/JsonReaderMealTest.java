package persistence;

import model.Meal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Modeled after JsonReaderTest in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderMealTest {

    @Test
    void testReaderNoFile() {
        JsonReaderMeal reader = new JsonReaderMeal("./data/noSuchFile.json");
        try {
            reader.readMeal();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMealList() {
        JsonReaderMeal reader = new JsonReaderMeal("./data/Test/emptyMealTest.json");
        try {
            ArrayList<Meal> mealList = reader.readMeal();
            assertEquals(new ArrayList<>(), mealList);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMealList() {
        JsonReaderMeal reader = new JsonReaderMeal("./data/Test/generalMealsTest.json");
        try {
            ArrayList<Meal> mealList = reader.readMeal();

            ArrayList<String> expected = new ArrayList<>();
            expected.add("Overnight Oats");
            expected.add("281");
            expected.add("564");
            expected.add("22");
            expected.add("120");
            expected.add("5");

            assertEquals(expected, mealList.get(0).getRecipe().getTotal().getIngredient().getFields());
            assertEquals(281, mealList.get(0).getMass());
            assertEquals("07:00", mealList.get(0).getTimeString());
            assertEquals("2020-01-01", mealList.get(0).getDateString());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
