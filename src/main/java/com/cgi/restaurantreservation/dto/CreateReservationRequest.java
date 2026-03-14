package com.cgi.restaurantreservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateReservationRequest {

    @NotNull(message = "Table id is required")
    private Long tableId;

    @NotNull(message = "Reservation date is required")
    private LocalDate date;

    @NotNull(message = "Reservation time is required")
    private LocalTime time;

    @Min(value = 1, message = "Party size must be at least 1")
    
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