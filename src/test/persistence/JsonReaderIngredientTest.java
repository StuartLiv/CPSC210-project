package persistence;

import model.Ingredient;
import org.junit.jupiter.api.Test;
import persistence.JsonReaderIngredient;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//Modeled after JsonReaderTest in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderIngredientTest {

    @Test
    void testReaderNoFile() {
        JsonReaderIngredient reader = new JsonReaderIngredient("./data/noSuchFile.json");
        try {
            ArrayList<Ingredient> ingredientList = reader.readIngredient();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyIngredientList() {
        JsonReaderIngredient reader = new JsonReaderIngredient("./data/Test/emptyIngredientTest.json");
        try {
            ArrayList<Ingredient> ingredientList = reader.readIngredient();
            assertEquals(new ArrayList<>(), ingredientList);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralIngredientList() {
        JsonReaderIngredient reader = new JsonReaderIngredient("./data/Test/generalIngredientsTest.json");
        try {
            ArrayList<Ingredient> ingredientList = reader.readIngredient();
            ArrayList<Ingredient> expected = new ArrayList<>();
            expected.add(new Ingredient("Oats", 100, 333, 11, 73, 3 ));
            expected.add(new Ingredient("Milk", 250, 90, 9, 12, 0 ));
            assertEquals(expected.get(0).getFields(), ingredientList.get(0).getFields());
            assertEquals(expected.get(1).getFields(), ingredientList.get(1).getFields());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
