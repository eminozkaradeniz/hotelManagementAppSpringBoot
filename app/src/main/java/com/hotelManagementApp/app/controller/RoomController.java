package com.hotelManagementApp.app.controller;

import com.hotelManagementApp.app.entity.Room;
import com.hotelManagementApp.app.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/list")
    public String listRooms(Authentication authentication, Model model) {

        // check if the user is an employee
        if (authentication.getAuthorities().stream()
                .map(Object::toString)
                .anyMatch(s -> (s.equals("ROLE_EMPLOYEE")))) {

            System.out.println("The User is an employee");

            // default searchDate
            Date out = Date.valueOf(LocalDate.now().plusDays(2));

            setEmployeeSearchModel(out, model);

        } else {
            // get all rooms from db
            List<Room> rooms = roomService.findAll();

            // add to the spring ui model
            model.addAttribute("rooms", rooms);
            model.addAttribute("currDate", LocalDate.now().toString());
        }

        return "rooms/list-rooms";
    }

    private void setEmployeeSearchModel(Date searchDate, Model theModel) {

        // get available rooms based check in and check out day
        // check in date is current date for employee
        Date in = Date.valueOf(LocalDate.now());
        System.out.println(searchDate.toString());
        List<Room> availRooms = roomService.findAvail(in, searchDate);

        // add to the spring ui model
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

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") @Valid Room room, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "rooms/room-form";
        } else {
            // save the room
            roomService.save(room);

            // use a redirect to prevent duplicate submissions
            return "redirect:/rooms/list";
        }

    }

    @GetMapping("/delete")
    public String delete(@RequestParam("roomNo") int roomNo) {

        roomService.deleteByRoomNo(roomNo);

        // redirect to /rooms/list
        return "redirect:/rooms/list";
    }

}
