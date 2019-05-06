package com.faisal.bakingrecipes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.faisal.bakingrecipes.POJO.Ingredient;
import com.faisal.bakingrecipes.POJO.Recipe;
import com.faisal.bakingrecipes.UI.MainActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId, Recipe recipe) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        Intent intent = new Intent(context, RecipeChangeService.class);
        intent.setAction(RecipeChangeService.ACTION_GET_RECIPES);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.widget_description, pendingIntent);

        Intent mainIntent = new Intent(context,MainActivity.class);

        PendingIntent pendingIntentMain = PendingIntent.getActivity(context,0,mainIntent,0);

        views.setOnClickPendingIntent(R.id.widget_recipe,pendingIntentMain);

        if (recipe != null) {
            String ingrediants = populateIngredients(recipe);
            views.setTextViewText(R.id.widget_recipe, recipe.getName());
            views.setTextViewText(R.id.widget_description, ingrediants);
        }else{
            views.setTextViewText(R.id.widget_recipe, "Nope");
            views.setTextViewText(R.id.widget_description, "Nope");
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RecipeChangeService.startActionGetRecipes(context);
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds, Recipe recipe) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static String populateIngredients(Recipe recipe) {
        List<Ingredient> ingredient = recipe.getIngredients();
        StringBuilder myString = new StringBuilder();
        for (int i = 0; i < ingredient.size(); i++) {
            myString.append(ingredient.get(i).getIngredient()).append(" ");
            myString.append(ingredient.get(i).getQuantity()).append(" ");
            myString.append(ingredient.get(i).getMeasure()).append(" ").append("\n");
        }
        return myString.toString();

    }
}

