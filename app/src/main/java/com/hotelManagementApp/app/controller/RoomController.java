package com.hotelManagementApp.app.controller;

import com.hotelManagementApp.app.entity.Room;
import com.hotelManagementApp.app.service.RoomService;
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
    public String listRooms(Model model) {

        // get rooms from db
        List<Room> rooms = roomService.findAll();

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
