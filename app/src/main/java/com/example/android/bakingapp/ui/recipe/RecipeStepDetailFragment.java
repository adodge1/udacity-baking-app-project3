package com.example.android.bakingapp.ui.recipe;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepDetailFragment extends Fragment {

    public static final String KEY_STEP_OBJ = "step_obj";
    public static final String KEY_RECIPE_OBJ = "recipe_obj";

    @BindView(R.id.step_description_title_tv) TextView mTitleTv;
    @BindView(R.id.step_description_tv) TextView mDescriptionTv;
    @BindView(R.id.prev_step_btn) Button mPrevBtn;
    @BindView(R.id.next_step_btn) Button mNextBtn;



    Recipe mSelectedRecipe ;
    Step mSelectedStep;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

     


        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            mSelectedStep = bundle.getParcelable(KEY_STEP_OBJ);

            mTitleTv.setText(mSelectedStep.getStepShortDescription());
            mDescriptionTv.setText(mSelectedStep.getStepDescription());


            // If the RecipeStep is the first or last, hide the Previous or Next button
            final int stepId = mSelectedStep.getStepId();
            if (stepId == 0){
                mPrevBtn.setVisibility(View.GONE);
            }
            if (bundle.getParcelable(KEY_RECIPE_OBJ) != null) {
                mSelectedRecipe = bundle.getParcelable(KEY_RECIPE_OBJ);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mSelectedRecipe.getRecipeName());

                if (stepId == mSelectedRecipe.getRecipeSteps().size() - 1){
                    mNextBtn.setVisibility(View.GONE);
                }
            }

            // Open a fragment with the previous step
            mPrevBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepId > 0) {
                        mSelectedStep = mSelectedRecipe.getRecipeSteps().get(stepId - 1);
                        launchNewFragment(mSelectedRecipe, mSelectedStep);
                    }
                }
            });

            // Open a fragment with the next step
            mNextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepId < mSelectedRecipe.getRecipeSteps().size() - 1) {
                        mSelectedStep = mSelectedRecipe.getRecipeSteps().get(stepId + 1);
                        launchNewFragment(mSelectedRecipe, mSelectedStep);
                    }
                }
            });


        }


        return view;

    }



    // Create a new StepDetailFragment
    private void launchNewFragment(Recipe recipe, Step step){
        int layoutId;
        // Inflate the layout in half the screen if it's a tablet in landscape
       // if (getResources().getBoolean(R.bool.isTablet) &&
         //      getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
           // layoutId = R.id.recipe_step_container;
        //} else { // Otherwise use the full screen
            layoutId = R.id.placeholder2;
       // }

        RecipeStepDetailFragment stepDetailFragment = new RecipeStepDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(KEY_STEP_OBJ, step);
        args.putParcelable(KEY_RECIPE_OBJ, recipe);

        stepDetailFragment.setArguments(args);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(layoutId, stepDetailFragment)
                .commit();
    }



}
