package com.github.mrgrtt.taohub.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "car_item")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "product_id")
    private Long productId;

    @ColumnInfo(name = "user_id")
    private Long userId;

    @ColumnInfo(name = "count")
    private Integer count;

    @ColumnInfo(name = "sum_price")
    private Long sumPrice;

    public void setId(Long id) {
        this.id = id;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setSumPrice(Long sumPrice) {
        this.sumPrice = sumPrice;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Integer getCount() {
        return count;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getSumPrice() {
        return sumPrice;
    }

    public Long getUserId() {
        return userId;
    }
}
