package com.github.mrgrtt.taohub;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.github.mrgrtt.taohub.dao.CartItemDao;
import com.github.mrgrtt.taohub.dao.CategoryDao;
import com.github.mrgrtt.taohub.dao.HistoryDao;
import com.github.mrgrtt.taohub.dao.ProductDao;
import com.github.mrgrtt.taohub.dao.UserDao;
import com.github.mrgrtt.taohub.model.CartItem;
import com.github.mrgrtt.taohub.model.Category;
import com.github.mrgrtt.taohub.model.History;
import com.github.mrgrtt.taohub.model.Product;
import com.github.mrgrtt.taohub.model.User;

@Database(entities = {CartItem.class, Category.class, History.class, Product.class,
        User.class}, version = 1)
public abstract class  TaoDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();
    public abstract CartItemDao cartItemDao();
    public abstract HistoryDao historyDao();
}
