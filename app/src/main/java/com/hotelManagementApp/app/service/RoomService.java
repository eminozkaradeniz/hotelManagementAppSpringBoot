package com.hotelManagementApp.app.service;

import com.hotelManagementApp.app.entity.Room;

import java.sql.Date;
import java.util.List;

public interface RoomService {

    public List<Room> findAll();
    public Room findByRoomNo(int no);
    public boolean save(Room room);
    public boolean deleteByRoomNo(int no);
    public List<Room> findAvail(Date in, Date out);
}
