package com.hotelManagementApp.app.controller;

import com.hotelManagementApp.app.entity.Reservation;
import com.hotelManagementApp.app.entity.Room;
import com.hotelManagementApp.app.service.ReservationService;
import com.hotelManagementApp.app.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
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

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(@RequestParam("roomNo") int roomNo, Model model) {

        Reservation reservation = new Reservation();
        reservation.setCheckIn(Date.valueOf(LocalDate.now()));
        reservation.setCheckOut(Date.valueOf(LocalDate.now().plusDays(1)));
        reservation.setRoom(roomService.findByRoomNo(roomNo));

        model.addAttribute("reservation", reservation);
        return "rooms/reservations/reservation-form-add";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("resId") int resId, Model model) {

        Reservation reservation = reservationService.findById(resId);

        model.addAttribute("reservation", reservation);

        return "rooms/reservations/reservation-form-update";
    }

    @PostMapping("/save")
    public String saveReservation(@ModelAttribute("reservation") Reservation reservation,
                                  RedirectAttributes redirectAttributes) {

        reservationService.save(reservation);

        // using redirect to prevent duplicate submissions
        redirectAttributes.addAttribute("roomNo", reservation.getRoom().getRoomNo());
        return "redirect:/rooms/reservations/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("resId") int resId, @RequestParam("roomNo") int roomNo,
                         RedirectAttributes redirectAttributes) {

        reservationService.deleteById(resId);

        redirectAttributes.addAttribute("roomNo", roomNo);
        return "redirect:/rooms/reservations/list";
    }

}
