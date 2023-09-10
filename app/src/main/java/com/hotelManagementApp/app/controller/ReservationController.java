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

/**
 * The ReservationController class handles HTTP request related to room reservations.
 * It provides endpoints for listing, creating, updating, and deleting reservations.
 */
@Controller
@RequestMapping("rooms/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;

    /**
     * Constructor injection to initialize the ReservationController with a RoomService, and a Reservation Service.
     *
     * @param reservationService The service for managing reservations.
     * @param roomService        The service for managing rooms.
     */
    public ReservationController(ReservationService reservationService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    /**
     * Handles GET requests for listing reservations.
     *
     * @param roomNo    The room number for which reservations are listed.
     * @param model     The Spring UI model to store reservation data for rendering.
     * @return The view name for displaying the list of reservations.
     */
    @GetMapping("/list")
    public String listReservations(@RequestParam("roomNo") int roomNo, Model model) {
        Room room = roomService.findByRoomNo(roomNo);
        List<Reservation> reservations = reservationService.findByRoom(room);

        model.addAttribute("reservations", reservations);
        model.addAttribute("roomNo", roomNo);

        return "rooms/reservations/list-reservations";
    }

    /**
     * Handles GET requests for showing the reservation form for adding a new reservation.
     *
     * @param roomNo The room number for which a reservation is being added.
     * @param model  The Spring UI model to prepopulate the form.
     * @return The view name for displaying the reservation form.
     */
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
     * Handles GET requests for showing the reservation form for adding a new reservation with a specified check-out date.
     *
     * @param roomNo   The room number for which a reservation is being added.
     * @param out      The check-out date for the reservation.
     * @param model    The Spring UI model to prepopulate the form.
     * @return The view name for displaying the reservation form.
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

    /**
     * Handles POST requests for saving or updating a reservation.
     *
     *  This method processes the submission of a reservation form, validates the input, and either saves or updates
     *  the reservation based on the form data.
     *  It also checks if the room is bookable between the check-in and check-out dates and handles redirection.
     *
     * @param isUpdate            A boolean indicating whether this is an update operation.
     *                            Set to true if updating an existing reservation.
     * @param reservation         The Reservation object containing form data to be saved or updated.
     * @param bindingResult       The Spring BindingResult used to capture validation errors.
     * @param redirectAttributes  The Spring RedirectAttributes used for redirection
     *                            and passing attributes to the redirected view.
     *
     * @return The view name to display after processing the form submission.
     */
    @PostMapping("/save")
    public String saveReservation(@RequestParam("isUpdate") boolean isUpdate,
                                  @ModelAttribute("reservation") @Valid Reservation reservation,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        // Check if there are validation errors in the form data
        if (bindingResult.hasErrors()) {
            return "rooms/reservations/reservation-form"; // Return to the form with validation errors.
        }
        else {
            // Check if the room is bookable between check-in and check-out dates
            if (roomService.isBookable(reservation.getRoom(), reservation)) {

                // Save or update the reservation using ReservationService
                reservationService.save(reservation);

                // Redirect to the list of reservations for the associated room to prevent duplicate submissions
                redirectAttributes.addAttribute("roomNo", reservation.getRoom().getRoomNo());
                return "redirect:/rooms/reservations/list";
            } else {
                // Handle case where the room is not bookable between the dates
                redirectAttributes.addFlashAttribute("notBookable",
                        String.format("Room %d is not bookable between these dates",
                                reservation.getRoom().getRoomNo()));

                if (isUpdate) {
                    // Redirect to the update form for an existing reservation
                    redirectAttributes.addAttribute("resId", reservation.getResId());
                    return "redirect:/rooms/reservations/showFormForUpdate";
                } else {
                    // Redirect to the add reservation form with the same room number
                    redirectAttributes.addAttribute("roomNo", reservation.getRoom().getRoomNo());
                    return "redirect:/rooms/reservations/showFormForAdd";
                }
            }
        }
    }


    /**
     * Handles GET requests for deleting a reservation
     * @param resId     The id of the reservation to be deleted.
     * @param roomNo    The room number associated with the reservation.
     * @param redirectAttributes    The Spring RedirectAttributes used for redirecting
     *                              list of reservations view for the associated room.
     * @return The redirection URL to the list of reservations for the associated room.
     */
    @GetMapping("/delete")
    public String delete(@RequestParam("resId") int resId, @RequestParam("roomNo") int roomNo,
                         RedirectAttributes redirectAttributes) {

        reservationService.deleteById(resId);

        redirectAttributes.addAttribute("roomNo", roomNo);
        return "redirect:/rooms/reservations/list";
    }

    /**
     * Sets reservation form model attributes, including minimum check-in and check-out dates.
     *
     * @param model           The Spring UI model to store the model attributes.
     * @param dateFieldReadonly Whether date fields should be readonly in the form.
     */
    public void setReservationFormModel(Model model, boolean dateFieldReadonly) {
        model.addAttribute("minCheckInDate", Date.valueOf(LocalDate.now()));
        model.addAttribute("minCheckOutDate", Date.valueOf(LocalDate.now().plusDays(1)));
        model.addAttribute("dateReadonly", dateFieldReadonly);
    }

}
