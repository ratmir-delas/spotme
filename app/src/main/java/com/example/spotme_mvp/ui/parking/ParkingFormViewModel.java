package com.example.spotme_mvp.ui.parking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.spotme_mvp.database.AppDatabase;
import com.example.spotme_mvp.entities.Parking;

public class ParkingFormViewModel extends ViewModel {
    private final MutableLiveData<Parking> parkingLiveData = new MutableLiveData<>();

    public LiveData<Parking> getParking() {
        return parkingLiveData;
    }

    public void setParking(Parking parking) {
        parkingLiveData.setValue(parking);
    }

}