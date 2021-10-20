package persistence;

import model.Ingredient;
import model.Portion;
import model.Recipe;
import ui.TrackerApp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

//Modeled after JsonReader in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Reader method for ingredients file
public class JsonReaderRecipe extends JsonReaderIngredient {

    // EFFECTS: constructs reader to read from source file
    public JsonReaderRecipe(String source) {
        super(source);
    }

    // EFFECTS: reads recipeList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Recipe> readRecipe() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecipes(jsonObject);
    }

    // EFFECTS: parses recipeList from JSON object and returns it
    private ArrayList<Recipe> parseRecipes(JSONObject jsonObject) {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("Recipes");
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            recipeList.add(parseRecipe(nextIngredient));
        }
        return recipeList;
    }

    // EFFECTS: parses recipe from JSONObject
    protected Recipe parseRecipe(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ArrayList<Portion> ingredients;
        ingredients = parsePortions(jsonObject);
        return new Recipe(ingredients, name);
    }

    //EFFECTS: parses portion fields from JSONObject
    private ArrayList<Portion> parsePortions(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("portions");
        ArrayList<Portion> ingredients = new ArrayList<>();
        for (Object json: jsonArray) {
            JSONObject nextPortion = (JSONObject) json;
            ingredients.add(parsePortion(nextPortion));
        }
        return ingredients;
    }

    //EFFECTS: parses individual portion from JsonObject
    private Portion parsePortion(JSONObject jsonObject) {
        Ingredient ingredient = parseIngredient(jsonObject);
        int mass = (jsonObject.getInt("mass"));
        return new Portion(ingredient, mass);
    }
}
