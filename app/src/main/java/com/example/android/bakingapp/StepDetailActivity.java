package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.recipe.RecipeStepDetailFragment;


public class StepDetailActivity extends AppCompatActivity    {
    public static final String STEP_FRAGMENT ="step_fragment";

    public Step stepObj;
    public Recipe recipeObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail_activity);



        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        try {
            stepObj = intent.getParcelableExtra("Step.Details");
            recipeObj = intent.getParcelableExtra("Recipe.Info");


         }catch (Exception e){
        e.printStackTrace();

        }


        RecipeStepDetailFragment  savedFragment = (RecipeStepDetailFragment) getSupportFragmentManager().findFragmentByTag(STEP_FRAGMENT);

        if (savedFragment == null) {

            RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
            Bundle bundle =new Bundle();
            bundle.putParcelable(RecipeStepDetailFragment.KEY_STEP_OBJ,stepObj);
            bundle.putParcelable(RecipeStepDetailFragment.KEY_RECIPE_OBJ,recipeObj);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeholder2,fragment,STEP_FRAGMENT);
            fragmentTransaction.commit();
        }
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


}
