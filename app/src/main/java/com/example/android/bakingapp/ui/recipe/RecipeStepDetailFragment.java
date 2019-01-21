package com.example.android.bakingapp.ui.recipe;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;


public class RecipeStepDetailFragment extends Fragment {


    public static final String KEY_STEP_INDEX = "step_index";
    public static final String KEY_STEP_OBJ = "step_obj";
    public static final String KEY_RECIPE_OF_STEP_OBJ = "recipe_of_step_obj";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        int index =getArguments().getInt(KEY_STEP_INDEX);
        Step mStepClicked = getArguments().getParcelable(KEY_STEP_OBJ);
        final Recipe mMinRecipe = getArguments().getParcelable(KEY_RECIPE_OF_STEP_OBJ);


        getActivity().setTitle(mStepClicked.getStepShortDescription());//set the title but put it back the old one onStop()

        RecipeFragment.OnRecipeSelectedInterface listener = (RecipeFragment.OnRecipeSelectedInterface) getActivity();

        View view = inflater.inflate(R.layout.step_detail_fragment, container, false);
    
        return view;

    }


    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(getResources().getString(R.string.app_name));
    }


}
