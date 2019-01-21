package com.example.android.bakingapp;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.recipe.RecipeFragment;
import com.example.android.bakingapp.ui.recipe.RecipeDetailFragment;
import com.example.android.bakingapp.ui.recipe.RecipeStepDetailFragment;


public class RecipeActivity extends AppCompatActivity  implements RecipeFragment.OnRecipeSelectedInterface, RecipeDetailFragment.OnStepSelectedInterface {

    public static final String RECIPE_FRAGMENT ="recipe_fragment";
    public static final String RECIPE_DETAIL_FRAGMENT ="ingredients_steps_fragment";
    public static final String RECIPE_STEP_DETAIL_FRAGMENT ="step_detail_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);

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

        RecipeDetailFragment detailFragment = new RecipeDetailFragment();

        Bundle bundle =new Bundle();
        bundle.putInt(RecipeDetailFragment.KEY_RECIPE_INDEX,index);
        bundle.putParcelable(RecipeDetailFragment.KEY_RECIPE_OBJ,recipeClicked);
        detailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder,detailFragment,RECIPE_DETAIL_FRAGMENT);
        fragmentTransaction.addToBackStack(null);// null because we are going back one transaction at the time
        fragmentTransaction.commit();
    }

    @Override
    public void onListStepSelected(int index,  Step stepClicked, Recipe mainRecipeObj) {

        RecipeStepDetailFragment detailFragment = new RecipeStepDetailFragment();

        Bundle bundle =new Bundle();
        bundle.putInt(RecipeStepDetailFragment.KEY_STEP_INDEX,index);
        bundle.putParcelable(RecipeStepDetailFragment.KEY_STEP_OBJ,stepClicked);
        bundle.putParcelable(RecipeStepDetailFragment.KEY_RECIPE_OF_STEP_OBJ,mainRecipeObj);
        detailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder,detailFragment,RECIPE_STEP_DETAIL_FRAGMENT);
        fragmentTransaction.addToBackStack(null);// null because we are going back one transaction at the time
        fragmentTransaction.commit();
    }





}
