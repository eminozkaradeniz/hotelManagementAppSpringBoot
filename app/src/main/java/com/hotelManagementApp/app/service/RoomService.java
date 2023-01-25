package com.hotelManagementApp.app.service;

import com.hotelManagementApp.app.entity.Reservation;
import com.hotelManagementApp.app.entity.Room;

import java.sql.Date;
import java.util.List;

public interface RoomService {

    List<Room> findAll();
    Room findByRoomNo(int no);
    boolean save(Room room);
    boolean deleteByRoomNo(int no);
    List<Room> findAllBookableRooms(Date in, Date out);
    boolean isBookable(Room room, Reservation newReservation);
}
