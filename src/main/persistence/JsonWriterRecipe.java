package persistence;

import model.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//Modeled after JsonWriter in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//A writer handler for the recipeList
public class JsonWriterRecipe extends JsonWriterIngredient {

    //EFFECTS: constructs writer to write to destination file
    public JsonWriterRecipe(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ingredientList to file
    public void writeRecipes(ArrayList<Recipe> recipes) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Recipe recipe: recipes) {
            jsonArray.put(recipe.toJson());
        }
        json.put("Recipes", jsonArray);
        saveToFile(json.toString(TAB));
    }
}
