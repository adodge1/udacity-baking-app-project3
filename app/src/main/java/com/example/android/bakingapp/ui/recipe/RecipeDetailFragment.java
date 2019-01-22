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


import com.example.android.bakingapp.utils.IngredientsListAdapter;
import com.example.android.bakingapp.utils.StepsListAdapter;


public class RecipeDetailFragment extends Fragment {



    ///AFTER CLICK on RECIPE WE DO THIS

    //put arguments in a bundle
    public static final String KEY_RECIPE_OBJ = "recipe_obj";

    private IngredientsListAdapter mIgredientListAdapter;
    private StepsListAdapter mStepListAdapter;
    private Recipe mRecipeClicked;

   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



       //getArguments
       mRecipeClicked = getArguments().getParcelable(KEY_RECIPE_OBJ);

        //   Toast.makeText(getActivity(),recipeClicked.getRecipeName(), Toast.LENGTH_SHORT).show();
        getActivity().setTitle(mRecipeClicked.getRecipeName());//set the title but put it back the old one onStop()


       View view = inflater.inflate(R.layout.ingredients_steps_fragment,container,false);

       //INGREDIENTS
       RecyclerView recyclerViewIngredients = view.findViewById(R.id.rv_ingredients);
       mIgredientListAdapter = new IngredientsListAdapter(getContext(),mRecipeClicked);
       recyclerViewIngredients.setAdapter(mIgredientListAdapter);
       // use a grid layout manager
       LinearLayoutManager layoutManager
               = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
       recyclerViewIngredients.setLayoutManager(layoutManager);
       mIgredientListAdapter.setIngredientsData(mRecipeClicked.getRecipeIngredients());

       //STEPS
       RecyclerView recyclerViewSteps = view.findViewById(R.id.rv_steps);
       mStepListAdapter = new StepsListAdapter(getContext(),mRecipeClicked);
       recyclerViewSteps.setAdapter(mStepListAdapter);
       LinearLayoutManager layoutManagerSteps
               = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
       recyclerViewSteps.setLayoutManager(layoutManagerSteps);
         mStepListAdapter.setStepData(mRecipeClicked.getRecipeSteps());
        return view;
    }



    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(mRecipeClicked.getRecipeName());
    }
}
