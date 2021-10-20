package persistence;

import model.Ingredient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
            writer.write(ingredients);
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
            JsonWriterIngredient writer = new JsonWriterIngredient("./data/Test/testWriterGeneralIngredientList.json");
            writer.open();
            writer.write(ingredients);
            writer.close();

            JsonReaderIngredient reader = new JsonReaderIngredient("./data/Test/testWriterGeneralIngredientList.json");
            ArrayList<Ingredient> result = reader.readIngredient();

            assertEquals(ingredients.get(0).getFields(), ingredients.get(0).getFields());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
