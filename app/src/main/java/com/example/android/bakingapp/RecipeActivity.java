package com.example.android.bakingapp;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.recipe.RecipeFragment;
import com.example.android.bakingapp.ui.recipe.RecipeDetailFragment;



public class RecipeActivity extends AppCompatActivity  implements RecipeFragment.OnRecipeSelectedInterface{

    public static final String RECIPE_FRAGMENT ="recipe_fragment";
    public static final String VIEWPAGER_FRAGMENT ="viewpager_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);

        RecipeFragment savedFragment = (RecipeFragment) getSupportFragmentManager().findFragmentByTag(RECIPE_FRAGMENT);

        if (savedFragment == null) {
            /*RecipeFragment fragment = new RecipeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.placeholder,fragment)
                    .commitNow();*/
            RecipeFragment fragment = new RecipeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeholder,fragment,RECIPE_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onListRecipeSelected(int index,  Recipe recipeClicked) {


        /*RecipeDetailFragment detailFragment = new RecipeDetailFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.placeholder, detailFragment).addToBackStack(null)
                .commitNow();*/
        RecipeDetailFragment detailFragment = new RecipeDetailFragment();

        Bundle bundle =new Bundle();
        bundle.putInt(RecipeDetailFragment.KEY_RECIPE_INDEX,index);
        bundle.putParcelable(RecipeDetailFragment.KEY_RECIPE_OBJ,recipeClicked);
        detailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder,detailFragment,VIEWPAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);// null because we are going back one transaction at the time
        fragmentTransaction.commit();
    }



}
