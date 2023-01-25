package com.hotelManagementApp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Entity
@Table(name = "reservation")
public class Reservation implements Comparable<Reservation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer resId;

    @Column(name = "customer")
    @NotBlank(message = "is required")
    private String customer;

    @Column(name = "check_in")
    @NotNull(message = "is required")
    private Date checkIn;

    @Column(name = "check_out")
    @NotNull(message = "is required")
    private Date checkOut;

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "room_no", nullable = false)
    @NotNull(message = "is required")
    private Room room;

    public Reservation() {
    }

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
     * @param r the reservation object to be compared.
     * @return -1 if reservations dates coincides with each other , else return 0
     */
    @Override
    public int compareTo(Reservation r) {
        if (r.checkOut.after(checkIn) && r.checkIn.before(checkOut)) {
            return -1;
        }
        return 0;
    }
}
