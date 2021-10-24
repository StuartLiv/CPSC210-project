package persistence;

import model.Ingredient;
import model.exceptions.InvalidNutritionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Modeled after JsonWriterTest in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterIngredientTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriterIngredient writer = new JsonWriterIngredient("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyList() {
        try {
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            JsonWriterIngredient writer = new JsonWriterIngredient("./data/Test/testWriterEmptyIngredientList.json");
            writer.open();
            writer.writeIngredients(ingredients);
            writer.close();

            JsonReaderIngredient reader = new JsonReaderIngredient("./data/Test/testWriterEmptyIngredientList.json");
            ArrayList<Ingredient> result = reader.readIngredient();
            assertEquals(ingredients, result);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralIngredientList() {
        try {
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(new Ingredient("Oats", 100, 333, 11, 73, 3 ));
            ingredients.add(new Ingredient("Milk", 250, 90, 9, 12, 0 ));
            JsonWriterIngredient writer = new JsonWriterIngredient("./data/Test/testWriterGeneralIngredientList.json");
            writer.open();
            writer.writeIngredients(ingredients);
            writer.close();

            JsonReaderIngredient reader = new JsonReaderIngredient("./data/Test/testWriterGeneralIngredientList.json");
            ArrayList<Ingredient> result = reader.readIngredient();

            assertEquals(ingredients.get(0).getFields(), result.get(0).getFields());
            assertEquals(ingredients.get(1).getFields(), result.get(1).getFields());
        } catch (IOException | InvalidNutritionException e) {
            fail("Exception should not have been thrown");
        }
    }
}
