package com.hotelManagementApp.app.service;

import com.hotelManagementApp.app.dao.ReservationRepository;
import com.hotelManagementApp.app.dao.RoomRepository;
import com.hotelManagementApp.app.entity.Reservation;
import com.hotelManagementApp.app.entity.Room;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;
    private ReservationRepository reservationRepository;

    public RoomServiceImpl(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
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
    public boolean save(Room room) {

        // if another room with same number exists than return false
        if (roomRepository.existsById(room.getRoomNo())) {
            return false;
        }

        // save the room
        roomRepository.save(room);
        return true;
    }

    @Override
    public boolean deleteByRoomNo(int no) {

        // get the room
        Optional<Room> optionalRoom = roomRepository.findById(no);

        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();

            // if room hasn't any reservations than delete the room
            if (room.getReservations().isEmpty()){
                roomRepository.delete(room);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Room> findAvail(Date in, Date out) {

        List<Reservation> reservations = reservationRepository.findAll();

        // all room numbers
        Set<Integer> ids = roomRepository.findAllRoomNo();

        for (Reservation r : reservations) {
            if (out.compareTo(r.getCheckIn()) > 0 && in.compareTo(r.getCheckOut()) < 0) {
                Integer roomNo = r.getRoom().getRoomNo();

                // remove room number if one of the reservations date coincides with new one
                if (ids.contains(r.getRoom().getRoomNo())) {
                    ids.remove(roomNo);
                }
            }
        }

        return roomRepository.findAllById(ids);
    }
}

