package com.faisal.bakingrecipes;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.faisal.bakingrecipes.POJO.Recipe;
import com.faisal.bakingrecipes.Utils.API;
import com.faisal.bakingrecipes.Utils.NetworkClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeChangeService extends IntentService {
    public static final String ACTION_GET_RECIPES = "com.faisal.bakingrecipes.action_get_recipe";
    private List<Recipe> mRecipe;
    private static int pos = 0;
    public RecipeChangeService() {
        super("RecipeChangeService");

    }

    public static void startActionGetRecipes(Context context) {
        Intent intent = new Intent(context, RecipeChangeService.class);
        intent.setAction(ACTION_GET_RECIPES);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_RECIPES.equals(action)) {
                handleActionUpdateWidgets();
            }
        }
    }

    private void handleActionUpdateWidgets() {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        API api = retrofit.create(API.class);
        Call<List<Recipe>> call = api.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipe = response.body();
                update(mRecipe);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void update(List<Recipe> recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidget.class));
        if (recipe != null) {
            BakingWidget.updateBakingWidgets(this, appWidgetManager, appWidgetIds, recipe.get(pos));
            pos++;
            if(pos > recipe.size() - 1){
                pos = 0;
            }
            Log.v("Update",""+pos);
        } else {
            BakingWidget.updateBakingWidgets(this, appWidgetManager, appWidgetIds, null);
        }

    }


}
