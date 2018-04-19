package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/4/17 21:36
 * Email：120760202@qq.com
 * FileName :
 */

public class BookDetail {
    String author, bookImg, isbn, publisher, summary, title, price;
    Integer bookId, pages;
    float average;

    @Override
    public String toString() {
        return "BookDetail{" +
                "author='" + author + '\'' +
                ", bookImg='" + bookImg + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", summary='" + summary + '\'' +
                ", title='" + title + '\'' +
                ", average=" + average +
                ", bookId=" + bookId +
                ", pages=" + pages +
                ", price=" + price +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
