package com.faisal.bakingrecipes.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faisal.bakingrecipes.POJO.Step;
import com.faisal.bakingrecipes.R;
import com.faisal.bakingrecipes.UI.Fragments.RecipeDetailMasterFragment;

import java.util.List;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailAdapterViewHolder> {

    private final RecipeDetailMasterFragment.onClickListener mClickHandler;
    private List<Step> mSteps;

    public RecipeDetailAdapter(RecipeDetailMasterFragment.onClickListener mClickHandler,
                               List<Step> mSteps) {
        this.mClickHandler = mClickHandler;
        this.mSteps = mSteps;
    }

    class RecipeDetailAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView mStep;

        RecipeDetailAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mStep = itemView.findViewById(R.id.step);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(view);
        }

        void bind(int pos){
            String step = mSteps.get(pos).getShortDescription();
            mStep.setText(step);
            itemView.setTag(pos);
        }
    }

    @NonNull
    @Override
    public RecipeDetailAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        int layoutId = R.layout.recipe_step_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId,viewGroup,false);

        return  new RecipeDetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailAdapterViewHolder
                                             recipeDetailAdapterViewHolder, int i) {
        recipeDetailAdapterViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        if(mSteps == null){
            return 0;
        }else{
            return mSteps.size();
        }
    }

    public void setStepData(List<Step> steps){
        mSteps = steps;
        notifyDataSetChanged();
    }
}
