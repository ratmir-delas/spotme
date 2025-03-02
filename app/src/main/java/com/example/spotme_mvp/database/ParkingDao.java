package com.example.spotme_mvp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.spotme_mvp.entities.Parking;

import java.util.List;
import java.util.Optional;

@Dao
public interface ParkingDao {
    @Query("SELECT * FROM parking")
    List<Parking> getAll();

    @Query("SELECT * FROM parking WHERE userId = :userId")
    List<Parking> getParkingsByUserId(long userId);

    @Query("SELECT * FROM parking WHERE id = :id")
    Parking getById(int id);

    @Query("SELECT * FROM parking WHERE endTime IS NULL")
    Optional<Parking> getCurrent();

    @Insert
    void insert(Parking parking);

    @Update
    void update(Parking parking);

    @Delete
    void delete(Parking parking);
}
