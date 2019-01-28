package com.example.android.bakingapp.ui.recipe;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.bakingapp.database.FavoriteEntry;
import com.example.android.bakingapp.database.FavoriteRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private FavoriteRepository repository;
    private LiveData<List<FavoriteEntry>> mAllFavoriteMovies;


    public RecipeViewModel (Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        mAllFavoriteMovies = repository.getAllFavorites();

    }
    LiveData<List<FavoriteEntry>> getAllFavorites() { return mAllFavoriteMovies; }

    public void insertFavorite(FavoriteEntry fave) {
        repository.insert(fave);
    }

}
