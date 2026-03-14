package com.cgi.restaurantreservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationRequest {

    private Long tableId;
    private LocalDate date;
    private LocalTime time;
    private int partySize;

    public CreateReservationRequest() {
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }
}