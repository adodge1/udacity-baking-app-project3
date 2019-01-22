package com.example.android.bakingapp.ui.recipe;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;


public class RecipeStepDetailFragment extends Fragment {

    public static final String KEY_STEP_OBJ = "step_obj";
    public static final String KEY_RECIPE_OBJ = "recipe_obj";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Step stepClicked = getArguments().getParcelable(KEY_STEP_OBJ);
        Recipe recipeClicked = getArguments().getParcelable(KEY_RECIPE_OBJ);

        getActivity().setTitle(recipeClicked.getRecipeName());

        View view = inflater.inflate(R.layout.step_detail_fragment, container, false);

        return view;

    }



}
