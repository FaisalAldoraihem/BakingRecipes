package com.faisal.bakingrecipes.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.faisal.bakingrecipes.Adapters.RecipeAdapter;
import com.faisal.bakingrecipes.POJO.Recipe;
import com.faisal.bakingrecipes.R;
import com.faisal.bakingrecipes.ViewModels.RecipesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private final static String LOG_TAG = MainActivity.class.getName();
    public static final String RECIPE = "Recipe";
    private List<Recipe> mRecipe;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instantiateViewModels();

        RecyclerView recyclerView = findViewById(R.id.recipesRV);

        adapter = new RecipeAdapter(this, this);

        int numOfColumns = calculateNoOfColumns(this);

        GridLayoutManager mGridLayoutManager = new
                GridLayoutManager(this, numOfColumns);

        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(adapter);

    }


    private void instantiateViewModels() {
        RecipesViewModel recipesViewModel = ViewModelProviders.of(this)
                .get(RecipesViewModel.class);

        recipesViewModel.getmRecipes().observe(this, recipes -> {
            mRecipe = recipes;
            adapter.setRecipes(mRecipe);
        });
    }

    private int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if (noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, RecipeDetail.class);
        int pos = (int) view.getTag();

        intent.putExtra(RECIPE, mRecipe.get(pos));
        startActivity(intent);
    }
}
