package com.example.android.bakingapp.ui.recipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.android.bakingapp.R;

import com.example.android.bakingapp.model.Recipe;

import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.utils.IngredientsListAdapter;
import com.example.android.bakingapp.utils.StepsListAdapter;


public class RecipeDetailFragment extends Fragment {

    ////FRAGMENT INTERFACE TO ADD CLICK this needs to be implemented on the activity
    public interface OnStepSelectedInterface{
        //method called to handle when a recipe is selected
        void onListStepSelected(int index , Step steps,Recipe mainRecipe);

    }

    ///AFTER CLICK on RECIPE WE DO THIS

    //put arguments in a bundle
    public static final String KEY_RECIPE_INDEX = "recipe_index";
    public static final String KEY_RECIPE_OBJ = "recipe_obj";



    private IngredientsListAdapter mIgredientListAdapter;
    private StepsListAdapter mStepListAdapter;


   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {




        int index =getArguments().getInt(KEY_RECIPE_INDEX);
        Recipe recipeClicked = getArguments().getParcelable(KEY_RECIPE_OBJ);

        //   Toast.makeText(getActivity(),recipeClicked.getRecipeName(), Toast.LENGTH_SHORT).show();
        getActivity().setTitle(recipeClicked.getRecipeName());//set the title but put it back the old one onStop()


       View view = inflater.inflate(R.layout.ingredients_steps_fragment,container,false);

       //INGREDIENTS
       RecyclerView recyclerViewIngredients = view.findViewById(R.id.rv_ingredients);
       mIgredientListAdapter = new IngredientsListAdapter(getContext(),recipeClicked);
       recyclerViewIngredients.setAdapter(mIgredientListAdapter);
       // use a grid layout manager
       LinearLayoutManager layoutManager
               = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
       recyclerViewIngredients.setLayoutManager(layoutManager);
       mIgredientListAdapter.setIngredientsData(recipeClicked.getRecipeIngredients());

       //STEPS
       RecyclerView recyclerViewSteps = view.findViewById(R.id.rv_steps);
       mStepListAdapter = new StepsListAdapter(getContext(),recipeClicked);
       recyclerViewSteps.setAdapter(mStepListAdapter);
       LinearLayoutManager layoutManagerSteps
               = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
       recyclerViewSteps.setLayoutManager(layoutManagerSteps);
         mStepListAdapter.setStepData(recipeClicked.getRecipeSteps());
        return view;
    }





    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(getResources().getString(R.string.app_name));
    }
}
