package com.app_republic.test_app.model;

public class Post {
    private String name;
    private String description;
    private int image;
    private int avatar;
    private boolean isLiked;
    private int likes;
    private int views;

    public Post(String name, String description, int image, int avatar, boolean isLiked, int likes, int views) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.avatar = avatar;
        this.isLiked = isLiked;
        this.views = views;
        this.likes = likes;

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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
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
}
