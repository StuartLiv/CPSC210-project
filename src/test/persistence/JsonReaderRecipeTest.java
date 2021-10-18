package persistence;

import model.Recipe;
import org.junit.jupiter.api.Test;
import persistence.JsonReaderRecipe;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//Modeled after JsonReaderTest in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderRecipeTest {

    @Test
    void testReaderNoFile() {
        JsonReaderRecipe reader = new JsonReaderRecipe("./data/noSuchFile.json");
        try {
            ArrayList<Recipe> recipeList = reader.readRecipe();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReaderRecipe reader = new JsonReaderRecipe("./data/Test/emptyRecipeTest.json");
        try {
            ArrayList<Recipe> recipeList = reader.readRecipe();
            assertEquals(new ArrayList<>(), recipeList);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReaderRecipe reader = new JsonReaderRecipe("./data/Test/generalRecipesTest.json");
        try {
            ArrayList<Recipe> recipeList = reader.readRecipe();

            ArrayList<String> expected = new ArrayList<>();
            expected.add("Overnight Oats");
            expected.add("281");
            expected.add("564");
            expected.add("22");
            expected.add("120");
            expected.add("5");

            assertEquals(recipeList.get(0).getTotal().getIngredient().getFields(), expected);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
