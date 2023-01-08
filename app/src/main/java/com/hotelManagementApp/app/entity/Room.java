package com.hotelManagementApp.app.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "no")
    private Integer roomNo;

    @Column(name = "type")
    private String roomType;

    @Column(name = "price")
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
