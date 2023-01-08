package com.hotelManagementApp.app.service;

import com.hotelManagementApp.app.dao.RoomRepository;
import com.hotelManagementApp.app.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAllByOrderByRoomNoAsc();
    }

    @Override
    public Room findByRoomNo(int no) {
        Optional<Room> result = roomRepository.findById(no);

        Room room = null;
        if (result.isPresent()) {
            room = result.get();
        } else {
            throw new RuntimeException("Did not find the room no - " + no);
        }
        return room;
    }

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void deleteByRoomNo(int no) {
        roomRepository.deleteById(no);
    }
}
