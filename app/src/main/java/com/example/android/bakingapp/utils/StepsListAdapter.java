package com.example.android.bakingapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.bakingapp.R;

import com.example.android.bakingapp.RecipeActivity;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.recipe.RecipeStepDetailFragment;

import java.util.ArrayList;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepRecyclerViewHolder> {

    private ArrayList<Step> mSteps;

    private Recipe mRecipe;
    private Context mContext;




    public StepsListAdapter(Context context, Recipe item) {
        this.mContext = context;
        this.mRecipe = item;
        this.mSteps = mRecipe.getRecipeSteps();
    }



    public class StepRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final Button mStepButton;

        public StepRecyclerViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            mStepButton = view.findViewById(R.id.btn_step);
            //Since this is a Button we need to set this!
            mStepButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the item clicked
             Step myStepSelected = mSteps.get(getAdapterPosition());

            //call click interface here because this are buttons
            ((RecipeActivity)mContext).onListStepSelected(getAdapterPosition(),myStepSelected,mRecipe);


        }


    }

    /* The adapter provides access to our data. It also provides the views for the displayed items.
   We create our custom recycler adapter by extending the RecyclerView.Adapter class.
   There are three methods that you must implement:
   onCreateViewHolder() – creates a new ViewHolder containing our image
   onBindViewHolder() – displays our image at the specified position in the list
   getItemCount() – gets the number of items in the adapter*/

    @NonNull
    @Override
    public StepsListAdapter.StepRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.step_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new StepsListAdapter.StepRecyclerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsListAdapter.StepRecyclerViewHolder stepRecyclerViewHolder, int position) {

        mSteps = mRecipe.getRecipeSteps();


        for(int i = 0; i < mSteps.size(); i++) {
            String stepShortName = mSteps.get(position).getStepShortDescription();
            Button button = stepRecyclerViewHolder.mStepButton;
            button.setText(stepShortName);
        }

    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    public void setStepData (ArrayList<Step> stepData){
        mSteps = stepData;
        notifyDataSetChanged();
    }
}
