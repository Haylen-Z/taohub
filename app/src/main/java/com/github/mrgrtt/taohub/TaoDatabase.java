package com.github.mrgrtt.taohub;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.github.mrgrtt.taohub.dao.UserDao;
import com.github.mrgrtt.taohub.model.CarItem;
import com.github.mrgrtt.taohub.model.Category;
import com.github.mrgrtt.taohub.model.History;
import com.github.mrgrtt.taohub.model.Product;
import com.github.mrgrtt.taohub.model.User;

@Database(entities = {CarItem.class, Category.class, History.class, Product.class,
        User.class}, version = 1)
public abstract class  TaoDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
