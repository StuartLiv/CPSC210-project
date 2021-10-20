package persistence;

import model.Ingredient;
import model.Portion;
import model.Recipe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterRecipeTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriterRecipe writer = new JsonWriterRecipe("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyList() {
        try {
            ArrayList<Recipe> recipeBook = new ArrayList<>();
            JsonWriterRecipe writer = new JsonWriterRecipe("./data/Test/testWriterEmptyRecipeList.json");
            writer.open();
            writer.writeRecipes(recipeBook);
            writer.close();

            JsonReaderRecipe reader = new JsonReaderRecipe("./data/Test/testWriterEmptyRecipeList.json");
            ArrayList<Recipe> result = reader.readRecipe();
            assertEquals(recipeBook, result);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralIngredientList() {
        try {
            ArrayList<Recipe> recipeBook = new ArrayList<>();
            ArrayList<Portion> portions = new ArrayList<>();
            portions.add(new Portion(
                    new Ingredient("Oats", 100, 333, 11, 73, 3 ), 156));
            portions.add(new Portion (
                    new Ingredient("Milk", 250, 90, 9, 12, 0 ), 125));
            recipeBook.add(new Recipe(portions, "Overnight Oats"));
            JsonWriterRecipe writer = new JsonWriterRecipe("./data/Test/testWriterGeneralRecipesList.json");
            writer.open();
            writer.writeRecipes(recipeBook);
            writer.close();
            JsonReaderRecipe reader = new JsonReaderRecipe("./data/Test/testWriterGeneralRecipesList.json");
            ArrayList<Recipe> result = reader.readRecipe();

            assertEquals(recipeBook.get(0).getTotal().getIngredient().getFields(),
                    result.get(0).getTotal().getIngredient().getFields());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
