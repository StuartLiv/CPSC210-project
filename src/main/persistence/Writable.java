package persistence;

import org.json.JSONObject;

//Class copied from, and package inspired by JsonSerializationDemo
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public interface Writable {

    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}
