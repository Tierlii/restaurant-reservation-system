package com.cgi.restaurantreservation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cgi.restaurantreservation.dto.CreateReservationRequest;
import com.cgi.restaurantreservation.dto.ReservationResponse;
import com.cgi.restaurantreservation.model.Reservation;
import com.cgi.restaurantreservation.model.RestaurantTable;
import com.cgi.restaurantreservation.repository.InMemoryDataStore;

@Service
public class ReservationService {

    private static final int DEFAULT_RESERVATION_DURATION_HOURS = 2;

    private final InMemoryDataStore dataStore;

    public ReservationService(InMemoryDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public boolean isTableAvailable(Long tableId, LocalDateTime requestedStart, LocalDateTime requestedEnd) {
        List<Reservation> reservations = dataStore.getReservations();

        for (Reservation reservation : reservations) {
            if (!reservation.getTableId().equals(tableId)) {
                continue;
            }

            if (overlaps(requestedStart, requestedEnd,
                    reservation.getReservationStart(),
                    reservation.getReservationEnd())) {
                return false;
            }
        }

        return true;
    }

    public boolean overlaps(LocalDateTime requestedStart,
                            LocalDateTime requestedEnd,
                            LocalDateTime existingStart,
                            LocalDateTime existingEnd) {
        return requestedStart.isBefore(existingEnd) && requestedEnd.isAfter(existingStart);
    }

    public ReservationResponse createReservation(CreateReservationRequest request) {
        validateRequest(request);

        RestaurantTable table = findTableById(request.getTableId());

        if (request.getPartySize() > table.getCapacity()) {
            throw new IllegalArgumentException("Party size exceeds table capacity.");
        }

        LocalDateTime reservationStart = LocalDateTime.of(request.getDate(), request.getTime());
        LocalDateTime reservationEnd = reservationStart.plusHours(DEFAULT_RESERVATION_DURATION_HOURS);

        if (!isTableAvailable(table.getId(), reservationStart, reservationEnd)) {
            throw new IllegalArgumentException("Selected table is not available for the chosen time.");
        }

        Reservation reservation = new Reservation(
                dataStore.nextReservationId(),
                table.getId(),
                reservationStart,
                reservationEnd,
                request.getPartySize()
        );

        dataStore.getReservations().add(reservation);

        return new ReservationResponse(
                reservation.getId(),
                reservation.getTableId(),
                reservation.getReservationStart(),
                reservation.getReservationEnd(),
                "Reservation created successfully."
        );
    }

    public RestaurantTable findTableById(Long tableId) {
        return dataStore.getTables().stream()
                .filter(table -> table.getId().equals(tableId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Table not found."));
    }

    private void validateRequest(CreateReservationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Reservation request must not be null.");
        }

        if (request.getTableId() == null) {
            throw new IllegalArgumentException("Table id is required.");
        }

        if (request.getDate() == null) {
            throw new IllegalArgumentException("Reservation date is required.");
        }

        if (request.getTime() == null) {
            throw new IllegalArgumentException("Reservation time is required.");
        }

        if (request.getPartySize() <= 0) {
            throw new IllegalArgumentException("Party size must be greater than zero.");
        }
    }
}