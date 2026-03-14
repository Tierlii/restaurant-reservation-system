package com.cgi.restaurantreservation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.cgi.restaurantreservation.model.Reservation;
import com.cgi.restaurantreservation.model.RestaurantTable;

@Repository
public class InMemoryDataStore {

    private final List<RestaurantTable> tables = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    private final AtomicLong reservationIdGenerator = new AtomicLong(1);

    public List<RestaurantTable> getTables() {
        return tables;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Long nextReservationId() {
        return reservationIdGenerator.getAndIncrement();
    }
}