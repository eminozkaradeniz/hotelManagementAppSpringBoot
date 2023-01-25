package com.hotelManagementApp.app.controller;

import com.hotelManagementApp.app.entity.Reservation;
import com.hotelManagementApp.app.entity.Room;
import com.hotelManagementApp.app.service.ReservationService;
import com.hotelManagementApp.app.service.RoomService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("rooms/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;

    public ReservationController(ReservationService reservationService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    @GetMapping("/list")
    public String listReservations(@RequestParam("roomNo") int roomNo, Model model) {

        Room room = roomService.findByRoomNo(roomNo);

        // get reservations from db
        List<Reservation> reservations = reservationService.findByRoom(room);

        // add to the spring ui model
        model.addAttribute("reservations", reservations);
        model.addAttribute("roomNo", roomNo);

        return "rooms/reservations/list-reservations";
    }

    @RequestMapping(value = "/showFormForAdd", method = RequestMethod.GET, params = {"roomNo"})
    public String showFormForAdd(@RequestParam("roomNo") int roomNo, Model model) {

        Reservation reservation = new Reservation();
        reservation.setCheckIn(Date.valueOf(LocalDate.now()));
        reservation.setCheckOut(Date.valueOf(LocalDate.now().plusDays(1)));
        reservation.setRoom(roomService.findByRoomNo(roomNo));

        model.addAttribute("reservation", reservation);
        model.addAttribute("isUpdate", false);
        setReservationFormModel(model, false);

        return "rooms/reservations/reservation-form";
    }

    /**
     * Returns reservation form page based on role_employee's choice of searchDate
     * @param roomNo    room no for reservation to be added
     * @param out       check out date for reservation to be added
     * @param model     springframework ui model for prepopulate the form
     * @return          reservation page url
     */
    @RequestMapping(value = "/showFormForAdd", method = RequestMethod.GET, params = {"roomNo", "searchDate"})
    public String showFormForAdd(@RequestParam("roomNo") int roomNo, @RequestParam("searchDate") Date out, Model model) {

        Reservation reservation = new Reservation();
        reservation.setCheckIn(Date.valueOf(LocalDate.now()));
        reservation.setCheckOut(out);
        reservation.setRoom(roomService.findByRoomNo(roomNo));

        model.addAttribute("reservation", reservation);
        model.addAttribute("isUpdate", false);
        setReservationFormModel(model, true);

        return "rooms/reservations/reservation-form";
    }

    @RequestMapping(value = "/showFormForUpdate", method = RequestMethod.GET, params = {"resId"})
    public String showFormForUpdate(@RequestParam("resId") int resId, Model model) {

        Reservation reservation = reservationService.findById(resId);

        model.addAttribute("reservation", reservation);
        model.addAttribute("isUpdate", true);
        setReservationFormModel(model, false);

        return "rooms/reservations/reservation-form";
    }

    @PostMapping("/save")
    public String saveReservation(@ModelAttribute("reservation") @Valid Reservation reservation,
                                  @RequestParam("isUpdate") boolean isUpdate,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "rooms/reservations/reservation-form";
        }
        else {

            // check if room is bookable between checkin and checkout dates
            if (roomService.isBookable(reservation.getRoom(), reservation)) {

                // save the reservation
                reservationService.save(reservation);

                // using redirect to prevent duplicate submissions
                redirectAttributes.addAttribute("roomNo", reservation.getRoom().getRoomNo());
                return "redirect:/rooms/reservations/list";
            } else {

                redirectAttributes.addFlashAttribute("notBookable",
                        String.format("Room %d is not bookable between these dates",
                                reservation.getRoom().getRoomNo()));

                if (isUpdate) {
                    redirectAttributes.addAttribute("resId", reservation.getResId());
                    return "redirect:/rooms/reservations/showFormForUpdate";
                } else {
                    redirectAttributes.addAttribute("roomNo", reservation.getRoom().getRoomNo());
                    return "redirect:/rooms/reservations/showFormForAdd";
                }
            }
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("resId") int resId, @RequestParam("roomNo") int roomNo,
                         RedirectAttributes redirectAttributes) {

        reservationService.deleteById(resId);

        redirectAttributes.addAttribute("roomNo", roomNo);
        return "redirect:/rooms/reservations/list";
    }

    public void setReservationFormModel(Model model, boolean dateFieldReadonly) {
        model.addAttribute("minCheckInDate", Date.valueOf(LocalDate.now()));
        model.addAttribute("minCheckOutDate", Date.valueOf(LocalDate.now().plusDays(1)));
        model.addAttribute("dateReadonly", dateFieldReadonly);
    }

}
