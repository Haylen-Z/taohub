package com.github.mrgrtt.taohub.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "product")
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "category_id")
    private Long categoryId;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "picture")
    private String picture;

    @ColumnInfo(name = "price")
    private Long price;

    @ColumnInfo(name = "detail")
    private String detail;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }

    public String getPicture() {
        return picture;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
