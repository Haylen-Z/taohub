package com.github.mrgrtt.taohub.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.github.mrgrtt.taohub.model.History;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("select * from history where user_id = :userId order by time desc limit 100")
    List<History> getAllByUserId(long userId);

    @Insert
    void addHistory(History... histories);
}
