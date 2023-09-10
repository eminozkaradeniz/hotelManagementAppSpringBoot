package com.hotelManagementApp.app.controller;

import com.hotelManagementApp.app.entity.Room;
import com.hotelManagementApp.app.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * The RoomController class handles operations related to rooms.
 * This controller provides endpoints for listing, searching, adding, updating, and deleting rooms.
 * It handles different scenarios for employees and non-employees, allowing them to view and manage rooms accordingly.
 */
@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    /**
     * Constructor injection to initialize the RoomController with a RoomService.
     *
     * @param roomService The RoomService used for room-related operations.
     */
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Handles GET requests for listing rooms.
     * This method lists rooms based on user roles. For employees, it displays available rooms for a specific date.
     * For others, it lists all rooms in the database.
     *
     * @param authentication The authentication object containing user details.
     * @param model          The Spring Model used for passing data to the view.
     * @return The view name to display the list of rooms.
     */
    @GetMapping("/list")
    public String listRooms(Authentication authentication, Model model) {

        // Check if the user is an employee
        if (authentication.getAuthorities().stream()
                .map(Object::toString)
                .anyMatch(s -> (s.equals("ROLE_EMPLOYEE")))) {

            // Default searchDate
            Date out = Date.valueOf(LocalDate.now().plusDays(1));

            setEmployeeSearchModel(out, model);

        } else {
            List<Room> rooms = roomService.findAll();
            model.addAttribute("rooms", rooms);
            model.addAttribute("currDate", LocalDate.now().toString());
        }

        return "rooms/list-rooms";
    }


    private void setEmployeeSearchModel(Date searchDate, Model theModel) {

        // Get available rooms based check-in and check-out day
        // Check-in date is current date for employees
        Date in = Date.valueOf(LocalDate.now());
        System.out.println(searchDate.toString());
        List<Room> availRooms = roomService.findAllBookableRooms(in, searchDate);

        theModel.addAttribute("rooms", availRooms);
        theModel.addAttribute("currDate", LocalDate.now().toString());

        Date searchMinDate = Date.valueOf(LocalDate.now().plusDays(1));

        theModel.addAttribute("searchMinDate", searchMinDate);
        theModel.addAttribute("searchValueDate", searchDate);
        theModel.addAttribute("searchDate", searchDate);
    }

    @GetMapping("/searchAvailableRooms")
    public String searchAvailableRooms(@RequestParam("searchDate") Date searchDate, Model model) {

        setEmployeeSearchModel(searchDate, model);
        return "rooms/list-rooms";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "rooms/room-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("roomNo") int roomNo, Model model) {
        Room room = roomService.findByRoomNo(roomNo);
        model.addAttribute("room", room);
        return "rooms/room-form";
    }

    /**
     * Handles POST requests for saving a room or updating an existing room.
     * This method processes the submission of a room form, validates the input,
     * and either saves the room or displays validation errors to the user.
     * It also checks for duplicate room numbers and handles redirection accordingly.
     *
     * @param room               The Room object containing form data to be saved or updated.
     * @param bindingResult      The Spring BindingResult used to capture validation errors.
     * @param redirectAttributes The Spring RedirectAttributes used for redirection
     *                           and passing attributes to the redirected view.
     * @return The view name to display after processing the form submission.
     */
    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") @Valid Room room, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        // Check if there are validation errors in the form data
        if (bindingResult.hasErrors()) {
            return "rooms/room-form";
        } else {
            // Attempt to save the room using RoomService
            if (roomService.save(room)) {
                redirectAttributes.addFlashAttribute("roomSaveSuccess",
                        "Room is saved successfully.");
                // Redirect to the list of rooms after a successful save
                return "redirect:/rooms/list";
            } else {
                redirectAttributes.addFlashAttribute("roomSaveFail",
                        "Room can not be saved. There is another room with the same number.");

                // Redirect to the add room form to alert the error
                return "redirect:/rooms/showFormForAdd";
            }
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("roomNo") int roomNo, RedirectAttributes redirectAttributes) {

        if (roomService.deleteByRoomNo(roomNo)) {
            redirectAttributes.addFlashAttribute("roomDeleteSuccess",
                    "Room is deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("roomDeleteFail",
                    "This room can not be deleted.");
        }

        // Redirect to the list of rooms.
        return "redirect:/rooms/list";
    }

}
