package com.example.android.bakingapp;

import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ItemRecyclerViewHolder> {

    private ArrayList<Recipe> mRecipies;
   // private final ItemRecyclerAdapterOnClickHandler mClickHandler;

    public interface ItemRecyclerAdapterOnClickHandler {
        void onClick(Recipe selectedRecipe);
    }

   // public RecipeListAdapter(ItemRecyclerAdapterOnClickHandler clickHandler) {
   //     mClickHandler = clickHandler;
   // }



    public class ItemRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mRecipeImageView;
        public final TextView mRecipeTextView;

        public ItemRecyclerViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mRecipeImageView = view.findViewById(R.id.recipeItemImage);
            mRecipeTextView = view.findViewById(R.id.recipeItemText);
        }



        @Override
        public void onClick(View view) {
            // Get the item clicked
           // Recipe myMovieSelected = mRecipies.get(getAdapterPosition());
            // Then you can do any actions on it, for example:
          //  mClickHandler.onClick(myMovieSelected);
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ItemRecyclerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ItemRecyclerViewHolder holder, int position) {
        Recipe recipe = mRecipies.get(position);
        TextView textViewName =  holder.mRecipeTextView;
        textViewName.append(recipe.getRecipeName());
    }


    @Override
    public int getItemCount() {
        if (null == mRecipies) return 0;
        return mRecipies.size();
    }

    public void setRecipeData (ArrayList<Recipe> recipeData){
        mRecipies = recipeData;
        notifyDataSetChanged();
    }

}
