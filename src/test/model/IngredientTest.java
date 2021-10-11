package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {
    private Ingredient oat;

    @BeforeEach
    void runBefore() {
        oat = new Ingredient("Oats", 100, 333, 11, 73, 3 );
    }

    //Test getFields, indirectly testing all the other getters
    @Test
    void getFieldsTest() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Oats");
        expected.add("100");
        expected.add("333");
        expected.add("11");
        expected.add("73");
        expected.add("3");

        ArrayList<String> actual = oat.getFields();

        assertEquals(expected, actual);
    }

}