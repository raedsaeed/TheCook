package com.example.raed.thecook.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by raed on 4/3/18.
 */

@Entity(tableName = "ingredient", foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId", onDelete = CASCADE))
public class Ingredient implements Parcelable {
    @PrimaryKey
    private double quantity;
    private String measure;
    private String ingredient;
    private int recipeId;

    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    private Ingredient(Parcel parcel) {
        quantity = parcel.readDouble();
        measure = parcel.readString();
        ingredient = parcel.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;

        if (getQuantity() != that.getQuantity()) return false;
        if (getMeasure() != null ? !getMeasure().equals(that.getMeasure()) : that.getMeasure() != null)
            return false;
        return getIngredient() != null ? getIngredient().equals(that.getIngredient()) : that.getIngredient() == null;
    }

    @Override
    public int hashCode() {
        double result = getQuantity();
        result = 31 * result + (getMeasure() != null ? getMeasure().hashCode() : 0);
        result = 31 * result + (getIngredient() != null ? getIngredient().hashCode() : 0);
        return (int) result;
    }
}
