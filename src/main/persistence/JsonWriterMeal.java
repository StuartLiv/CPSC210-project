package persistence;

import model.Meal;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonWriterMeal extends JsonWriterRecipe {

    //EFFECTS: constructs writer to write to destination file
    public JsonWriterMeal(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ingredientList to file
    public void writeMeals(ArrayList<Meal> meals) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Meal meal: meals) {
            jsonArray.put(meal.toJson());
        }
        json.put("Meals", jsonArray);
        saveToFile(json.toString(TAB));
    }
}
