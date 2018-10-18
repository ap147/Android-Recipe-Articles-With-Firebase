package com.parmar.amarjot.android_recipe_book_with_firebase;


public class Recipe {

    private  String name;
    private  String description;
    private  String category;
    private  String article;
    private  String imageID;
    // true if user imported this recipe, otherwise false
    private  String userAdded;

    public Recipe(String _name, String _description, String _category, String _article, String _imageID, String _userAdded) {
        name = _name;
        description = _description;
        category = _category;
        article = _article;
        imageID = _imageID;
        userAdded = _userAdded;
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

    public String getArticle() {
        return article;
    }

    public String getImageID() {
        return imageID;
    }

    public String getUserAdded() {
        return userAdded;
    }
}
