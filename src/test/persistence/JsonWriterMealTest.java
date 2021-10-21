package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Modeled after JsonWriterTest in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterMealTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriterMeal writer = new JsonWriterMeal("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyList() {
        try {
            ArrayList<Meal> tracker = new ArrayList<>();
            JsonWriterMeal writer = new JsonWriterMeal("./data/Test/testWriterEmptyMealList.json");
            writer.open();
            writer.writeMeals(tracker);
            writer.close();

            JsonReaderMeal reader = new JsonReaderMeal("./data/Test/testWriterEmptyMealList.json");
            ArrayList<Meal> result = reader.readMeal();
            assertEquals(tracker, result);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralIngredientList() {
        try {
            ArrayList<Meal> tracker = new ArrayList<>();
            ArrayList<Portion> portions = new ArrayList<>();
            portions.add(new Portion(
                    new Ingredient("Oats", 100, 333, 11, 73, 3 ), 156));
            portions.add(new Portion (
                    new Ingredient("Milk", 250, 90, 9, 12, 0 ), 125));
            tracker.add(new Meal ((new Recipe(portions, "Overnight Oats")), 281, 7));
            JsonWriterMeal writer = new JsonWriterMeal("./data/Test/testWriterGeneralMealsList.json");
            writer.open();
            writer.writeMeals(tracker);
            writer.close();

            JsonReaderMeal reader = new JsonReaderMeal("./data/Test/testWriterGeneralMealsList.json");
            ArrayList<Meal> result = reader.readMeal();

            assertEquals(tracker.get(0).getTotal().getIngredient().getFields(),
                    result.get(0).getTotal().getIngredient().getFields());
            assertEquals(tracker.get(0).getMass(), result.get(0).getMass());
            assertEquals(tracker.get(0).getTime(), result.get(0).getTime());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
