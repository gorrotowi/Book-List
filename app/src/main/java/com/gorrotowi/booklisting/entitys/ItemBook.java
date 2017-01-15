package com.gorrotowi.booklisting.entitys;

/**
 * Created by Gorro on 14/01/17.
 */

public class ItemBook {

    private String imgUrl;
    private String title;
    private String authors;
    private String year;
    private int pCount;
    private String urlBook;

    public ItemBook() {
    }

    public ItemBook(String imgUrl, String title, String authors, String year, int pCount, String urlBook) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.pCount = pCount;
        this.urlBook = urlBook;
    }

    @Override
    public String toString() {
        return "ItemBook{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", year='" + year + '\'' +
                ", pCount=" + pCount +
                ", urlBook='" + urlBook + '\'' +
                '}';
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getpCount() {
        return pCount;
    }

    public void setpCount(int pCount) {
        this.pCount = pCount;
    }

    public String getUrlBook() {
        return urlBook;
    }

    public void setUrlBook(String urlBook) {
        this.urlBook = urlBook;
    }
}
