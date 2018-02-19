package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static List<String> alsoKnownAsList;
    private static List<String> ingredientsList;
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOW_AS ="alsoKnownAs";
    private static final String INGREDIENTS ="ingredients";

    public static Sandwich parseSandwichJson(String json) {


        alsoKnownAsList = new ArrayList<>();
        ingredientsList = new ArrayList<>();
        Sandwich sandwich = new Sandwich();
        try {

            JSONObject jsonObject = new JSONObject(json);

            sandwich.setPlaceOfOrigin(jsonObject.getString(PLACE_OF_ORIGIN));
            sandwich.setDescription(jsonObject.getString(DESCRIPTION));
            sandwich.setImage(jsonObject.getString(IMAGE));

            String nameStringObject= jsonObject.getString(NAME);
            JSONObject nameJsonObject = new JSONObject(nameStringObject);

            sandwich.setMainName(nameJsonObject.getString(MAIN_NAME));


            String alsoKnownAsString = nameJsonObject.getString(ALSO_KNOW_AS);
            JSONArray alsoKnownAs = new JSONArray(alsoKnownAsString);
            if(alsoKnownAs.length() >0){
                for(int i=0; i<alsoKnownAs.length(); i++){
                    alsoKnownAsList.add(alsoKnownAs.getString(i));
                }
            }
            else{
                alsoKnownAsList.add("");
            }
            sandwich.setAlsoKnownAs(alsoKnownAsList);

            String ingredientsStringObject = jsonObject.getString(INGREDIENTS);
            JSONArray ingredientsArray = new JSONArray(ingredientsStringObject);
            if(ingredientsArray.length() >0){
                for(int i=0; i<ingredientsArray.length(); i++){
                    ingredientsList.add(ingredientsArray.getString(i));
                }
            }
            else{
                ingredientsList.add("");
            }
            sandwich.setIngredients(ingredientsList);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
