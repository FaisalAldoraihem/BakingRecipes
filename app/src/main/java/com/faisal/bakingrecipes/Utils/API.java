package com.faisal.bakingrecipes.Utils;


import com.faisal.bakingrecipes.POJO.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
