package com.example.ce216librarymanagementproject;

public class BookInformation {
    String title;
    String subtitle;
    String translators;
    String authors;
    String publisher;
    String date;
    String isbn;
    String  cover;
    String language;
    String category;
    String edition;
    String rating;
    String tags;
    String pictures;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTranslators() {
        return translators;
    }

    public void setTranslators(String translators) {
        this.translators = translators;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public BookInformation() {
        this.title = " ";
        this.subtitle =  " ";
        this.translators =  " ";
        this.authors =  " ";
        this.publisher =  " ";
        this.date =  " ";
        this.isbn =  " ";
        this.cover = " ";
        this.language =  " ";
        this.category =  " ";
        this.edition =  " ";
        this.rating =  " ";
        this.tags =  " ";
        this.pictures =  " ";
    }

    @Override
    public String toString() {
        return "BookInformation{" +
                "Title: ='" + title + '\'' +
                ", Translators:'" + translators + '\'' +
                ", Authors:'" + authors + '\'' +
                ", Publisher'" + publisher + '\'' +
                ", Date:'" + date + '\'' +
                ", ISBN:'" + isbn + '\'' +
                ", Cover:'" + cover + '\'' +
                ", Language:'" + language + '\'' +
                ", Category:'" + category + '\'' +
                ", Edition:'" + edition + '\'' +
                ", Rating:'" + rating + '\'' +
                ", Tags:'" + tags + '\'' +
                ", Pictures:'" + pictures + '\'' +
                +
                        '}';
    }
}
