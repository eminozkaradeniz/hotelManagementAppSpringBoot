package com.hotelManagementApp.app.service;

import com.hotelManagementApp.app.entity.Room;

import java.sql.Date;
import java.util.List;

public interface RoomService {

    public List<Room> findAll();
    public Room findByRoomNo(int no);
    public void save(Room room);
    public void deleteByRoomNo(int no);
    public List<Room> findAvail(Date in, Date out);
}
