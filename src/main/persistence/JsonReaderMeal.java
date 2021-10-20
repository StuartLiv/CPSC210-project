package persistence;

import model.Meal;
import model.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

//Modeled after JsonReader in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Reader method for meals file
public class JsonReaderMeal extends  JsonReaderRecipe {

    // EFFECTS: constructs reader to read from source file
    public JsonReaderMeal(String source) {
        super(source);
    }

    // EFFECTS: reads mealList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Meal> readMeal() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMeals(jsonObject);
    }

    //EFFECTS: returns mealList from jsonObject
    private ArrayList<Meal> parseMeals(JSONObject jsonObject) {
        ArrayList<Meal> tracker = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("Meals");
        for (Object json : jsonArray) {
            JSONObject nextMeal = (JSONObject) json;
            tracker.add(parseMeal(nextMeal));
        }
        return tracker;
    }

    //EFFECTS: returns meal from jsonObject
    private Meal parseMeal(JSONObject jsonObject) {
        Recipe mealRecipe = parseRecipe(jsonObject.getJSONObject("Recipe"));
        int mass = jsonObject.getInt("mass");
        int time = jsonObject.getInt("time");
        return new Meal(mealRecipe, mass, time);
    }
}
