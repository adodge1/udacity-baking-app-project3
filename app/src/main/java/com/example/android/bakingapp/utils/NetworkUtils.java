package com.example.android.bakingapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {


    /**
     * Builds the URL used to query JSON Feed
     *
     * @return The URL to use to query the Recipies.
     * https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
     */
    public static URL buildUrl() {
        Uri.Builder builder = new Uri.Builder();

            builder.scheme("https")
                    .authority("d17h27t6h515a5.cloudfront.net")
                    .appendPath("topher")
                    .appendPath("2017")
                    .appendPath("May")
                    .appendPath("59121517_baking")
                    .appendPath("baking.json");
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Determine if there is an internet connection available.
     *
     * @return true if there is an internet connection, false if there isn't.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Creates and displays an alert dialog telling the user
     * there is no internet connection
     *
     * @param context The context
     */
    public static void createNoConnectionDialog(Context context) {

        //Create dialog builder with corresponding settings
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        // Set content
        builder.setTitle(context.getString(R.string.network_dialog_title))
                .setMessage(context.getString(R.string.network_dialog_message));
        // Set button
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        // Create dialog and display it to the user
        AlertDialog dialog = builder.create();

        dialog.show();

        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.RED);
    }



    /**
     * This method parse the JSON response string and changes it into an ArrayList of movies
     * so it can be read easily and get each item
     * @param responseFromHttpUrl
     * @return ArrayList<Movie>
     *
     */
    public static ArrayList<Recipe> extractFeatureFromJson(String responseFromHttpUrl) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(responseFromHttpUrl)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Recipe> recipies = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONArray from the JSON response string

            JSONArray recipesJSON = new JSONArray(responseFromHttpUrl);

            // For each movie in the baseJsonResponse, create a {@link Movie} object
            for (int i = 0; i < recipesJSON.length(); i++) {

                JSONObject recipeJSON = recipesJSON.getJSONObject(i);
                Recipe recipeObject = createRecipeObject(recipeJSON);



                recipies.add(recipeObject);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }

        // Return the list of movies
        return recipies;
    }


    /**
     * Creates a Recipe object
     *
     * @param recipeJSON The recipe's data as a JSONObject
     *
     * @return A Recipe instance
     */
    private static Recipe createRecipeObject(JSONObject recipeJSON) {

        try {

            String recipeName = recipeJSON.getString("name");
            ArrayList<Ingredient> recipeIngredients = createRecipeIngredientsArray(recipeJSON.optJSONArray("ingredients"));

            ArrayList<Step> recipeSteps = createRecipeStepsArray(recipeJSON.optJSONArray("steps"));
            int recipeServings = Integer.parseInt(recipeJSON.getString("servings"));

            String recipeImage = buildRecipeName(recipeJSON);

            Recipe newRecipe = new Recipe(recipeName, recipeIngredients, recipeSteps, recipeServings, recipeImage);
            newRecipe.setRecipeIngredients(recipeIngredients);
            newRecipe.setRecipeSteps(recipeSteps);

            return newRecipe;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }



    private static String buildRecipeName(JSONObject recipeJSON) {
        String recipeImage;

        try {

            if(!recipeJSON.getString("image").equals("")) {
                recipeImage = recipeJSON.getString("image");
            } else {
                recipeImage = "recipe" + recipeJSON.getInt("id");
            }

            return recipeImage;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Creates a Steps array from the data passed as argument
     *
     * @param stepsArray A JSONArray containing the steps data
     *
     * @return An ArrayList of Steps
     */
    private static ArrayList<Step> createRecipeStepsArray(JSONArray stepsArray) {

        ArrayList<Step> recipeStepArray = new ArrayList<Step>();

        for(int i = 0; i < stepsArray.length(); i++) {
            try {
                JSONObject stepJSON = stepsArray.getJSONObject(i);
                Step recipeStep = createRecipeStep(stepJSON);

                // Add the recipe step
                recipeStepArray.add(recipeStep);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return recipeStepArray;
    }

    /**
     * Creates a Recipe Step
     *
     * @param stepJSON The Step's data as a JSONObject
     *
     * @return A Step
     */
    private static Step createRecipeStep(JSONObject stepJSON) {

        try {
            int stepId = Integer.parseInt(stepJSON.getString("id"));
            String stepShortDescription = stepJSON.getString("shortDescription");
            String stepDescription = stepJSON.getString("description");
            String stepVideoURL = stepJSON.getString("videoURL");
            String stepThumbnailURL = stepJSON.getString("thumbnailURL");

            return new Step(stepId, stepShortDescription, stepDescription, stepVideoURL, stepThumbnailURL);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a Recipes Ingredients Array from the data passed as argument
     *
     * @param ingredientsArray The ingredients data as a JSONArray
     *
     * @return An ArrayList of ingredients
     */
    private static ArrayList<Ingredient> createRecipeIngredientsArray(JSONArray ingredientsArray) {

        ArrayList<Ingredient> recipeIngredientsArray = new ArrayList<Ingredient>();

        for(int i = 0; i < ingredientsArray.length(); i++) {
            JSONObject ingredientJSON = null;
            try {
                ingredientJSON = ingredientsArray.getJSONObject(i);
                Ingredient recipeIngredient = createRecipeIngredient(ingredientJSON);

                // Add the recipe step
                recipeIngredientsArray.add(recipeIngredient);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return recipeIngredientsArray;
    };

    /**
     * Creates a Recipe Ingredient
     *
     * @param ingredientJSON The ingredients data as a JSONObject
     *
     * @return An Ingredient
     */
    private static Ingredient createRecipeIngredient(JSONObject ingredientJSON) {
        try {

            String ingredientName = ingredientJSON.getString("ingredient");
            String ingredientQuantity = ingredientJSON.getString("quantity");
            String ingredientMeasure = ingredientJSON.getString("measure");

            return new Ingredient(ingredientName, ingredientQuantity, ingredientMeasure);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }




}
