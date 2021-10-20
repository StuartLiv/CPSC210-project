package persistence;

import model.Ingredient;
import org.json.*;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

//Modeled after JsonWriter in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//A writer handler for the ingredientList
public class JsonWriterIngredient {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriterIngredient(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ingredientList to file
    public void write(ArrayList<Ingredient> ingredients) {
        JSONObject json = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for (Ingredient ingredient: ingredients) {
            jsonArray.put(ingredient.toJson());
        }
        json.put("Ingredients", jsonArray);
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

