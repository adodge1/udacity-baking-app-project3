package com.example.android.bakingapp.ui.recipe;

import android.arch.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
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
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;




public class RecipeFragment  extends Fragment {

    ////FRAGMENT INTERFACE TO ADD CLICK this needs to be inmplemented on the activity
    public interface OnRecipeSelectedInterface{
        //method called to handle when a recipe is selected
        void onListRecipeSelected(int index ,Recipe recipes);

    }


    private RecipeViewModel mViewModel;
    private RecipeListAdapter mRecipeListAdapter;


    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        OnRecipeSelectedInterface listener = (OnRecipeSelectedInterface) getActivity();


        View view = inflater.inflate(R.layout.recipe_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_recipeList);

        mRecipeListAdapter = new RecipeListAdapter(listener);

        recyclerView.setAdapter(mRecipeListAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Build URL
        URL recipeUrl = NetworkUtils.buildUrl();
        //Do HTTPS request on background Thread
        new HTTPrequestBackgroundThread().execute(recipeUrl);
        return view;

    }

    public class HTTPrequestBackgroundThread extends AsyncTask<URL, Void,  ArrayList<Recipe>> {
        @Override
        protected ArrayList<Recipe> doInBackground(URL... params) {
            URL searchUrl = params[0];
            String responseFromHttpUrl = null;
            ArrayList<Recipe> recipes =null;

            try {
                responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                recipes = NetworkUtils.extractFeatureFromJson(responseFromHttpUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return recipes;

        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> results) {

            if (results != null) {

                mRecipeListAdapter.setRecipeData(results);
                //mMovieRecyclerView.getLayoutManager().onRestoreInstanceState(mSavedRecyclerLayoutState);
            } else {

            }
        }

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        // TODO: Use the ViewModel


    }

}
