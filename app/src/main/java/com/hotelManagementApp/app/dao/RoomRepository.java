package com.hotelManagementApp.app.dao;

import com.hotelManagementApp.app.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    public List<Room> findAllByOrderByRoomNoAsc();
}
