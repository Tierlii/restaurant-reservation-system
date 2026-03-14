package com.cgi.restaurantreservation.dto;

import java.time.LocalDateTime;

public class ReservationResponse {

    private Long reservationId;
    private Long tableId;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private String message;

    public ReservationResponse() {
    }

    public ReservationResponse(Long reservationId,
                               Long tableId,
                               LocalDateTime reservationStart,
                               LocalDateTime reservationEnd,
                               String message) {
        this.reservationId = reservationId;
        this.tableId = tableId;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        this.message = message;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public LocalDateTime getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDateTime reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDateTime getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(LocalDateTime reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}