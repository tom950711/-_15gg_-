package com.example.proto_4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClientDao {
    @Query("SELECT * FROM Client")
    Client getAll();

    @Query("SELECT * FROM Client WHERE UID LIKE :uid")
    Client loadClients(String uid);

    @Insert
    void insert(Client client);

    @Update
    void update(Client client);

    @Delete
    void delete(Client client);
}
