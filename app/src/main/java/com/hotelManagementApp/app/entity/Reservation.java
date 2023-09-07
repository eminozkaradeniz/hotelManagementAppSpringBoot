package com.hotelManagementApp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.Objects;

/**
 * Represents a reservation entity in the hotel management application.
 * Each reservation includes details such as the customer's name, check-in date, check-out date, and associated room.
 */
@Entity
@Table(name = "reservation")
public class Reservation implements Comparable<Reservation> {

    /**
     * Unique identifier for the reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer resId;

    /**
     * The name of the customer associated with the reservation.
     */
    @Column(name = "customer")
    @NotBlank(message = "is required")
    private String customer;

    /**
     * The check-in date for the reservation.
     */
    @Column(name = "check_in")
    @NotNull(message = "is required")
    private Date checkIn;

    /**
     * The check-out date for the reservation.
     */
    @Column(name = "check_out")
    @NotNull(message = "is required")
    private Date checkOut;

    /**
     * The room associated with the reservation.
     * This is a many-to one relationship with the 'Room' entity.
     */
    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "room_no", nullable = false)
    @NotNull(message = "is required")
    private Room room;

    /**
     * Default constructor for the Reservation class.
     */
    public Reservation() {
    }

    /**
     * Constructor with parameters for creating a reservation.
     *
     * @param customer The name of the customer making the reservation.
     * @param checkIn  The check-in date for the reservation.
     * @param checkOut The check-out date for the reservation.
     */
    public Reservation(String customer, Date checkIn, Date checkOut) {
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Reservation(Integer resId, String customer, Date checkIn, Date checkOut) {
        this.resId = resId;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Returns a string representation of the Reservation object.
     *
     * @return A string representation of the reservation object, including its fields.
     */
    @Override
    public String toString() {
        return "Reservation{" +
                "resId=" + resId +
                ", customer='" + customer + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }

    /**
     * Compares this reservation with another reservation to check for date conflicts.
     *
     * @param r the reservation object to be compared.
     * @return -1 if reservations dates coincide with each other; else returns 0
     */
    @Override
    public int compareTo(Reservation r) {
        // return 1 if ids are the same
        if (Objects.equals(resId, r.resId)) {
            return 1;
        }
        if (r.checkOut.after(checkIn) && r.checkIn.before(checkOut)) {
            return -1;
        }
        return 0;
    }
}
