package com.faisal.bakingrecipes.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.faisal.bakingrecipes.POJO.Recipe;
import com.faisal.bakingrecipes.Utils.API;
import com.faisal.bakingrecipes.Utils.NetworkClient;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipesViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();
    private final static String LOG_TAG = RecipesViewModel.class.getName();


    public RecipesViewModel(@NonNull Application application) {
        super(application);
        getRecipes();
    }

    private void getRecipes() {

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        API api = retrofit.create(API.class);
        Call<List<Recipe>> call = api.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipes.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public MutableLiveData<List<Recipe>> getmRecipes() {
        return mRecipes;
    }
}
