package com.example.android.bakingapp;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.android.bakingapp.database.FavoriteEntry;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.recipe.RecipeDetailFragment;
import com.example.android.bakingapp.ui.recipe.RecipeFragment;
import com.example.android.bakingapp.ui.recipe.RecipeViewModel;
import com.facebook.stetho.Stetho;

import java.util.List;


public class MainActivity extends AppCompatActivity  implements RecipeFragment.OnRecipeSelectedInterface  {

    public static final String RECIPE_FRAGMENT ="recipe_fragment";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Stetho.initializeWithDefaults(this);

        setContentView(R.layout.main_activity);

        RecipeFragment savedFragment = (RecipeFragment) getSupportFragmentManager().findFragmentByTag(RECIPE_FRAGMENT);

        if (savedFragment == null) {

            RecipeFragment fragment = new RecipeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeholder,fragment,RECIPE_FRAGMENT);
            fragmentTransaction.commit();
        }




    }

    @Override
    public void onListRecipeSelected(int index,  Recipe recipeClicked) {


        Intent intentToStartRecipeDetailActivity = new Intent(this, RecipeDetailActivity.class);
        intentToStartRecipeDetailActivity.putExtra("Recipe.Details",recipeClicked);
        intentToStartRecipeDetailActivity.putExtra("Step.Details",recipeClicked.getRecipeSteps());
        this.startActivity(intentToStartRecipeDetailActivity);




    }





}
