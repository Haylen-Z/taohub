package com.github.mrgrtt.taohub.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.github.mrgrtt.taohub.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("select * from category")
    List<Category> getAll();
}
