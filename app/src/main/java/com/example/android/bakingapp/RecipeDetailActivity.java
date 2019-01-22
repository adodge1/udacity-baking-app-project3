package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.recipe.RecipeDetailFragment;


public class RecipeDetailActivity extends AppCompatActivity {
    public static final String RECIPE_DETAIL_FRAGMENT ="ingredients_steps_fragment";
    Recipe recipeObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);


        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        try {

            recipeObj = intent.getParcelableExtra("Recipe.Details");
            this.setTitle(recipeObj.getRecipeName());

        }catch (Exception e){

            e.printStackTrace();
        }


        RecipeDetailFragment savedFragment = (RecipeDetailFragment) getSupportFragmentManager().findFragmentByTag(RECIPE_DETAIL_FRAGMENT);

        if (savedFragment == null) {

            RecipeDetailFragment fragment = new RecipeDetailFragment();
            Bundle bundle =new Bundle();
            bundle.putParcelable(RecipeDetailFragment.KEY_RECIPE_OBJ,recipeObj);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeholder1,fragment,RECIPE_DETAIL_FRAGMENT);
            fragmentTransaction.commit();
        }
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }



}
