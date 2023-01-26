package com.hotelManagementApp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "no")
    @NotNull(message = "is required")
    @Min(value = 1, message = "must be greater than 0")
    @Max(value = 10001, message = "must be less than 10001")
    private Integer roomNo;

    @Column(name = "type")
    @NotBlank(message = "is required")
    private String roomType;

    @Column(name = "price")
    @NotNull(message = "is required")
    private Float price;

    @OneToMany( fetch = FetchType.LAZY,
            mappedBy = "room",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Reservation> reservations;

    public Room() {
    }

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

    @Override
    public String toString() {
        return "Room{" +
                "roomNo=" + roomNo +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                '}';
    }
}
