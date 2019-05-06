package com.faisal.bakingrecipes.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.faisal.bakingrecipes.POJO.Recipe;
import com.faisal.bakingrecipes.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private List<Recipe> mRecipes;
    private final RecipeAdapterOnClickHandler mClickHandler;
    private final Context context;

    public interface RecipeAdapterOnClickHandler {
        void onClick(View view);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler mClickHandler, Context context) {
        this.mClickHandler = mClickHandler;
        this.context = context;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView recipe;
        private final TextView servings;
        private final ImageView recipeImage;

        RecipeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe = itemView.findViewById(R.id.recipe);
            servings = itemView.findViewById(R.id.servings);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(view);
        }

        void bind(int pos) {
            String mServings = context.getString(R.string.servings) + mRecipes.get(pos).getServings();
            recipe.setText(mRecipes.get(pos).getName());
            servings.setText(mServings);
            String image = mRecipes.get(pos).getImage();

            if (!image.isEmpty()) {
                Glide.with(context)
                        .load(image)
                        .centerCrop()
                        .into(recipeImage);
            }

            itemView.setTag(pos);
        }
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        int layoutForListItem = R.layout.recipe_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForListItem, viewGroup, false);

        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder recipeAdapterViewHolder, int i) {
        recipeAdapterViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) {
            return 0;
        } else {
            return mRecipes.size();
        }
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }
}
