package com.example.android.bakingapp.ui.recipe;

import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeListAdapter;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;




public class RecipeFragment extends Fragment {

    private RecipeViewModel mViewModel;

    public static ArrayList<Recipe> mRecipesArray = new ArrayList<Recipe>();
    private static final String RECIPES_ARRAY_INTENT_KEY = "recipesArray";


    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_recipeList);

        RecipeListAdapter recipeListAdapter = new RecipeListAdapter();

        recyclerView.setAdapter(recipeListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_ARRAY_INTENT_KEY)) {
            mRecipesArray = savedInstanceState.getParcelableArrayList(RECIPES_ARRAY_INTENT_KEY);
        }



        ///TESTING
            Ingredient ingredient1 = new Ingredient("Graham Cracker crumbs","2","cup");
            Ingredient ingredient2 = new Ingredient("unsalted butter, melted","6","TBLSP");
            ArrayList<Ingredient> mIngredientsArray = new ArrayList<Ingredient>();
            mIngredientsArray.add(ingredient1);
            mIngredientsArray.add(ingredient2);
            Step step1 = new Step(0,"Recipe Introduction","Recipe Introduction","https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4","");
            Step step2 = new Step(1,"Starting prep","1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.","","");
            ArrayList<Step> mStepsArray = new ArrayList<Step>();
            mStepsArray.add(step1);
            mStepsArray.add(step2);
            Recipe myTestRecipe = new Recipe("Nutella Pie",mIngredientsArray,mStepsArray,8,"");
            mRecipesArray.add(myTestRecipe);

            //getting the data



        recipeListAdapter.setRecipeData(mRecipesArray);
        return view;

    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // Save data
        outState.putParcelableArrayList(RECIPES_ARRAY_INTENT_KEY, mRecipesArray);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        // TODO: Use the ViewModel


    }

}
