package com.hotelManagementApp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Represents a room entity in the hotel management application
 * Each room has a unique room number, room type, price, and associated reservations.
 */
@Entity
@Table(name = "room")
public class Room {

    /**
     * Unique room number for the room.
     */
    @Id
    @Column(name = "no")
    @NotNull(message = "is required")
    @Min(value = 1, message = "must be greater than 0")
    @Max(value = 10001, message = "must be less than 10001")
    private Integer roomNo;

    /**
     * The type of the room (e.g. single, double).
     */
    @Column(name = "type")
    @NotBlank(message = "is required")
    private String roomType;

    /**
     * The price per night for the room.
     */
    @Column(name = "price")
    @NotNull(message = "is required")
    private Float price;

    /**
     * List of reservations associated with this room.
     * This is a one-to-many relationship with the 'Reservation' entity.
     */
    @OneToMany( fetch = FetchType.LAZY,
            mappedBy = "room",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Reservation> reservations;

    /**
     * Default constructor for the Room class.
     */
    public Room() {
    }

    /**
     * Constructor wtih parameters for creating a room
     *
     * @param roomNo    The unique room number for the room
     * @param roomType  The type of the room
     * @param price     The price per night for the room
     */
    public Room(Integer roomNo, String roomType, Float price) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.price = price;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Returns a string representation of the Room object.
     *
     * @return A string representation of the room object, including its fields.
     */
    @Override
    public String toString() {
        return "Room{" +
                "roomNo=" + roomNo +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                '}';
    }
}
