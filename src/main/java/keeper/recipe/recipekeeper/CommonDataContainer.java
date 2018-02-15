package keeper.recipe.recipekeeper;

import java.util.ArrayList;

/*
* Stores values that need to be accessed repeatedly across multiple activities using
* setters and getters.
* */

public class CommonDataContainer {
    static ArrayList<String> recipeList;
    static String currentRecipeIngredients;
    static String currentRecipeDetails;
    static String popupConversionValue = "result";

    public void setRecipeList(ArrayList<String> updatedRecipeList){
        recipeList = updatedRecipeList;
    }

    public void setCurrentRecipeIngredients(String updatedRecipeIngredients){
        currentRecipeIngredients = updatedRecipeIngredients;
    }

    public void setCurrentRecipeDetails(String updatedRecipeDetails){
        currentRecipeDetails = updatedRecipeDetails;
    }

    public void setPopupConversionValue(String newValue){
        popupConversionValue = newValue;
    }

    public ArrayList<String> getRecipeList() {
        return recipeList;
    }

    public String getCurrentRecipeIngredients() {
        return currentRecipeIngredients;
    }

    public String getCurrentRecipeDetails() {
        return currentRecipeDetails;
    }
    public String getPopupConversionValue(){ return popupConversionValue;}
}
