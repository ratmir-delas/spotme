package com.example.spotme_mvp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spotme_mvp.entities.Parking;

@Database(entities = {Parking.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ParkingDao parkingDao();
}
