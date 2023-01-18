package com.hotelManagementApp.app.service;

import com.hotelManagementApp.app.dao.ReservationRepository;
import com.hotelManagementApp.app.entity.Reservation;
import com.hotelManagementApp.app.entity.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findByRoom(Room room) {
        return reservationRepository.findByRoom(room);
    }

    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(int resId) {
        Optional<Reservation> optional = reservationRepository.findById(resId);

        return optional.orElse(null);
    }

    @Override
    public void deleteById(int resId) {
        reservationRepository.deleteById(resId);
    }
}
