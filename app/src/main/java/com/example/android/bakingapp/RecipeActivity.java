package com.example.android.bakingapp;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.recipe.RecipeFragment;
import com.example.android.bakingapp.ui.recipe.ViewPagerFragment;



public class RecipeActivity extends AppCompatActivity  implements RecipeFragment.OnRecipeSelectedInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);

        RecipeFragment savedFragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.placeholder);

        if (savedFragment == null) {
            RecipeFragment fragment = new RecipeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeholder,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onListRecipeSelected(int index,  Recipe recipeClicked) {
        Toast.makeText(this,recipeClicked.getRecipeName(), Toast.LENGTH_SHORT).show();

        ViewPagerFragment detailFragment = new ViewPagerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder,detailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
