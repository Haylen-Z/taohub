package com.github.mrgrtt.taohub.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.github.mrgrtt.taohub.model.User;

import java.util.List;


@Dao
public interface UserDao {

    @Query("select * from user where username = :name")
   List<User> listByUsername(String name);

    @Insert
    void addUser(User... users);
}
