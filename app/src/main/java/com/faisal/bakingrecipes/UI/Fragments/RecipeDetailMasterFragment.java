package com.faisal.bakingrecipes.UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faisal.bakingrecipes.Adapters.RecipeDetailAdapter;
import com.faisal.bakingrecipes.POJO.Ingredient;
import com.faisal.bakingrecipes.POJO.Recipe;
import com.faisal.bakingrecipes.POJO.Step;
import com.faisal.bakingrecipes.R;
import com.faisal.bakingrecipes.UI.MainActivity;

import java.util.List;


public class RecipeDetailMasterFragment extends Fragment {

    private final static String LOG_TAG = RecipeDetailMasterFragment.class.getName();
    private Recipe mRecipe;

    private onClickListener mOnClick;
    private TextView mIngredients;
    private View rootView;

    public interface onClickListener {
        void onClick(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mOnClick = (onClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement onClickListener");
        }
    }

    public RecipeDetailMasterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_detail_list,
                    container, false);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView mRecipeSteps = rootView.findViewById(R.id.steps_rv);
        mIngredients = rootView.findViewById(R.id.ingredients);

        if (getArguments() != null) {

            mRecipe = getArguments().getParcelable(MainActivity.RECIPE);

            if (mRecipe != null) {

                List<Step> mSteps = mRecipe.getSteps();

                RecipeDetailAdapter adapter = new RecipeDetailAdapter(mOnClick, null);

                adapter.setStepData(mSteps);

                mRecipeSteps.setLayoutManager(layoutManager);

                mRecipeSteps.setAdapter(adapter);

                populateIngredients();
            }
        }

        return rootView;
    }

    private void populateIngredients() {
        List<Ingredient> ingredient = mRecipe.getIngredients();
        StringBuilder myString = new StringBuilder();
        for (int i = 0; i < ingredient.size(); i++) {
            myString.append(ingredient.get(i).getIngredient()).append(" ");
            myString.append(ingredient.get(i).getQuantity()).append(" ");
            myString.append(ingredient.get(i).getMeasure()).append(" ").append("\n");
        }
        String ingredients = myString.toString();

        mIngredients.setText(ingredients);
    }
}
