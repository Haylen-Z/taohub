package com.github.mrgrtt.taohub.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.github.mrgrtt.taohub.model.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("select * from product where category_id = :cId")
    List<Product> getByCategoryId(long cId);

    @Query("select * from product where id = :id")
    Product getById(long id);
}
