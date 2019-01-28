package com.example.android.bakingapp;

import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.recipe.RecipeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ItemRecyclerViewHolder> {

    private  RecipeFragment.OnRecipeSelectedInterface mListener;
    private ArrayList<Recipe> mRecipes;

    LayoutInflater mInflater;


    public RecipeListAdapter(RecipeFragment.OnRecipeSelectedInterface listener) {
        mListener = listener;
    }

     
    public class ItemRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_recipeItemText)
        TextView mRecipeTextView;

        @BindView(R.id.iv_recipePhoto)
        ImageView mRecipeImageView;




        private int mIndex;
        private Recipe recipe;

        private ItemRecyclerViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            mIndex = position;
            recipe = mRecipes.get(position);
            String mRecipeName = recipe.getRecipeName();
            mRecipeTextView.setText(mRecipeName);

            String mRecipeNameTrimmed = mRecipeName.replaceAll("\\s+","");


            int ImageIdentifier = mRecipeImageView.getContext().getResources().getIdentifier(mRecipeNameTrimmed.toLowerCase(), "drawable", mRecipeImageView.getContext().getPackageName());


            Picasso.with(mRecipeImageView.getContext())
                    .load(ImageIdentifier)
                    .into(mRecipeImageView);

        }

        @Override
        public void onClick(View view) {
            mListener.onListRecipeSelected(mIndex,recipe);
        }


    }

    /* The adapter provides access to our data. It also provides the views for the displayed items.
    We create our custom recycler adapter by extending the RecyclerView.Adapter class.
    There are three methods that you must implement:
    onCreateViewHolder() – creates a new ViewHolder containing our image
    onBindViewHolder() – displays our image at the specified position in the list
    getItemCount() – gets the number of items in the adapter*/

    @Override
    public ItemRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        mInflater = LayoutInflater.from(context);
        View mView = mInflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ItemRecyclerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ItemRecyclerViewHolder holder, int position) {
          holder.bindView(position);



    }


    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.size();
    }

    public void setRecipeData (ArrayList<Recipe> recipeData){
        mRecipes = recipeData;
        notifyDataSetChanged();
    }

}
