package com.hotelManagementApp.app.controller;

import com.hotelManagementApp.app.entity.Room;
import com.hotelManagementApp.app.service.RoomService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/list")
    public String listRooms(Authentication authentication, Model model) {

    	List<Room> rooms = null;

    	// check if the user is an employee (there must be an easy way)
    	if (authentication.getAuthorities().stream()
    			.map(a -> a.toString())
    			.anyMatch(s -> (s.equals("ROLE_EMPLOYEE")))) {
    		
    		// get available rooms
    		rooms = roomService.findAll();
    	} else {
    		// get all rooms from db
            rooms = roomService.findAll();
    	}

        // add to the spring ui model
        model.addAttribute("rooms", rooms);

        return "rooms/list-rooms";

    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {

        Room room = new Room();

        model.addAttribute("room", room);

        return "rooms/room-form-add";

    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("roomNo") int roomNo, Model model) {

        Room room = roomService.findByRoomNo(roomNo);

        model.addAttribute("room", room);

        return "rooms/room-form-update";
    }

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") Room room) {
        roomService.save(room);

        // using redirect to prevent duplicate submissions
        return "redirect:/rooms/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("roomNo") int roomNo) {

        roomService.deleteByRoomNo(roomNo);

        // redirect to /rooms/list
        return "redirect:/rooms/list";
    }

}
