package com.parmar.amarjot.android_recipe_book_with_firebase;

public class Recipe {

    private  String name;
    private  String description;
    private  String category;
    private  String ingredients;
    private  String directions;
    private  String imageID;

    public Recipe(String _name, String _description, String _category, String _ingredients, String _directions, String _imageID) {
        name = _name;
        description = _description;
        category = _category;
        ingredients = _ingredients;
        directions = _directions;
        imageID = _imageID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public String getImageID() {
        return imageID;
    }
}
