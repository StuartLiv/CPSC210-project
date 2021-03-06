package persistence;

import model.Recipe;
import model.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

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
            reader.readRecipe();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRecipeList() {
        JsonReaderRecipe reader = new JsonReaderRecipe("./data/Test/emptyRecipeTest.json");
        try {
            ArrayList<Recipe> recipeList = reader.readRecipe();
            assertEquals(new ArrayList<>(), recipeList);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRecipeList() {
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

        } catch (IOException | InvalidInputException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testInvalidRecipeList() {
        JsonReaderRecipe reader = new JsonReaderRecipe("./data/Test/invalidRecipesTest.json");
        try {
             reader.readRecipe();
             fail("Exception not triggered");
        } catch (IOException e) {
             fail("Couldn't read from file");
        } catch (RuntimeException e) {
             //pass
        }
    }
}
