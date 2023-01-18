package com.hotelManagementApp.app.dao;

import com.hotelManagementApp.app.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    public List<Room> findAllByOrderByRoomNoAsc();

    @Query("select r.roomNo from Room r")
    public Set<Integer> findAllRoomNo();
}
