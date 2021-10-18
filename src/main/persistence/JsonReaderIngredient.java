package persistence;

import model.Ingredient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

//Modeled after JsonReader in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Reader method for ingredients file
public class JsonReaderIngredient extends JsonReader {

    // EFFECTS: constructs reader to read from source file
    public JsonReaderIngredient(String source) {
        super(source);
    }

    // EFFECTS: reads ingredientList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Ingredient> readIngredient() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseIngredients(jsonObject);
    }

    // EFFECTS: parses ingredientList from JSON object and returns it
    private ArrayList<Ingredient> parseIngredients(JSONObject jsonObject) {
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("Ingredients");
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            ingredientList.add(parseIngredient(nextIngredient));
        }
        return ingredientList;
    }

    // EFFECTS: parses ingredient from JSONObject
    protected Ingredient parseIngredient(JSONObject jsonObject) {
        String fieldString = jsonObject.get("Ingredient").toString();
        String[] fields = fieldString.substring(2,fieldString.length() - 2).split("\",\"");
        return new Ingredient(fields);
    }
}
