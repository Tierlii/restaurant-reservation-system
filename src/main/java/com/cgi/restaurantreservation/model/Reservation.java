package com.cgi.restaurantreservation.model;

import java.time.LocalDateTime;

public class Reservation {

    private Long id;
    private Long tableId;

    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;

    private int partySize;

    public Reservation() {
    }

    public Reservation(Long id,
                       Long tableId,
                       LocalDateTime reservationStart,
                       LocalDateTime reservationEnd,
                       int partySize) {
        this.id = id;
        this.tableId = tableId;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        this.partySize = partySize;
    }

    public Long getId() {
        return id;
    }

    public Long getTableId() {
        return tableId;
    }

    public LocalDateTime getReservationStart() {
        return reservationStart;
    }

    public LocalDateTime getReservationEnd() {
        return reservationEnd;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public void setReservationStart(LocalDateTime reservationStart) {
        this.reservationStart = reservationStart;
    }

    public void setReservationEnd(LocalDateTime reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }
}