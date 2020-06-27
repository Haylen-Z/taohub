package com.github.mrgrtt.taohub.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "history")
public class History {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "user_id")
    private Long userId;

    @ColumnInfo(name = "product_id")
    private Long productId;

    @ColumnInfo(name = "time")
    private Long time;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getId() {
        return id;
    }

    public Long getTime() {
        return time;
    }
}
