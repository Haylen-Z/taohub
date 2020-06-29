package com.github.mrgrtt.taohub.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.github.mrgrtt.taohub.model.CartItem;

import java.util.List;

@Dao
public interface CartItemDao {
    @Insert
    void insert(CartItem... carItems);

    @Query("select * from car_item where user_id = :userId")
    List<CartItem> listByUserId(long userId);

    @Query("delete from car_item where id = :id")
    void delete(long id);
}
