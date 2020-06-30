package com.example.proto_4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ScrapDao {

    @Query("SELECT * FROM Scrap")
    Client getAll();

    @Query("SELECT * FROM scrap WHERE UID LIKE :uid")
    Scrap loadScrap(String uid);

    @Insert
    void insert(Scrap client);

    @Update
    void update(Scrap client);

    @Delete
    void delete(Scrap client);
}
