package com.example.raed.thecook.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by raed on 4/3/18.
 */

public class Recipe implements Parcelable{
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;

        Recipe recipe = (Recipe) o;

        if (getId() != recipe.getId()) return false;
        if (getServings() != recipe.getServings()) return false;
        if (getName() != null ? !getName().equals(recipe.getName()) : recipe.getName() != null)
            return false;
        if (getIngredients() != null ? !getIngredients().equals(recipe.getIngredients()) : recipe.getIngredients() != null)
            return false;
        if (getSteps() != null ? !getSteps().equals(recipe.getSteps()) : recipe.getSteps() != null)
            return false;
        return getImage() != null ? getImage().equals(recipe.getImage()) : recipe.getImage() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getIngredients() != null ? getIngredients().hashCode() : 0);
        result = 31 * result + (getSteps() != null ? getSteps().hashCode() : 0);
        result = 31 * result + getServings();
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        return result;
    }
}
