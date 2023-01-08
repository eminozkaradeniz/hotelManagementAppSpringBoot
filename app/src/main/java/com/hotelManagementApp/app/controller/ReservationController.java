package com.hotelManagementApp.app.controller;

import com.hotelManagementApp.app.entity.Reservation;
import com.hotelManagementApp.app.entity.Room;
import com.hotelManagementApp.app.service.ReservationService;
import com.hotelManagementApp.app.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("rooms/reservations")
public class ReservationController {

    private ReservationService reservationService;
    private RoomService roomService;

    public ReservationController(ReservationService reservationService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    @GetMapping("/list")
    public String listReservations(@RequestParam("roomNo") Integer roomNo, Model model) {

        Room room = roomService.findByRoomNo(roomNo);

        // get reservations from db
        List<Reservation> reservations = reservationService.findByRoom(room);

        // add to the spring ui model
        model.addAttribute("reservations", reservations);
        model.addAttribute("roomNo", roomNo);

        return "rooms/reservations/list-reservations";
    }
}
