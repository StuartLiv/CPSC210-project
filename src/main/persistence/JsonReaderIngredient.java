package persistence;

import model.Ingredient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

//Modeled after JsonReader in JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Reader method for ingredients file
public class JsonReaderIngredient {
    protected String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderIngredient(String source) {
        this.source = source;
    }

    // EFFECTS: reads source file as string and returns it
    protected String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
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
