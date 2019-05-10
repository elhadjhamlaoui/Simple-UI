package com.app_republic.test_app.model;

public class Card {
    private String name;
    private String description;
    private int likes;
    private int views;
    private boolean isLiked;
    private int color;

    public Card(String name, String description, int likes, int views, boolean isLiked, int color) {
        this.name = name;
        this.description = description;
        this.likes = likes;
        this.views = views;
        this.isLiked = isLiked;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
