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
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepDetailFragment extends Fragment {

    public static final String KEY_STEP_OBJ = "step_obj";
    public static final String KEY_RECIPE_OBJ = "recipe_obj";


    ViewPager mViewPager;
    Recipe recipeClicked ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Step stepClicked = getArguments().getParcelable(KEY_STEP_OBJ);
        recipeClicked = getArguments().getParcelable(KEY_RECIPE_OBJ);

        getActivity().setTitle(recipeClicked.getRecipeName());

        View view = inflater.inflate(R.layout.step_detail_fragment, container, false);

        //ViewPager set up
       /* mViewPager = view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return null;
            }

            @Override
            public int getCount() {
                return recipeClicked.getRecipeSteps().size();
            }
        });*/

        return view;

    }



}
