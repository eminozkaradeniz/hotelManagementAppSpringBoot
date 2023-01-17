package com.hotelManagementApp.app.service;

import com.hotelManagementApp.app.entity.Reservation;
import com.hotelManagementApp.app.entity.Room;

import java.util.List;

public interface ReservationService {

    public List<Reservation> findByRoom(Room room);
    public Reservation findById(int resId);
    public void save(Reservation reservation);
    public void deleteById(int resId);
}
