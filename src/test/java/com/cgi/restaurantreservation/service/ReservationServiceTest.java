package com.cgi.restaurantreservation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cgi.restaurantreservation.dto.CreateReservationRequest;
import com.cgi.restaurantreservation.dto.ReservationResponse;
import com.cgi.restaurantreservation.model.Reservation;
import com.cgi.restaurantreservation.model.RestaurantTable;
import com.cgi.restaurantreservation.model.Zone;
import com.cgi.restaurantreservation.repository.InMemoryDataStore;

class ReservationServiceTest {

    private InMemoryDataStore dataStore;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        dataStore = new InMemoryDataStore();
        reservationService = new ReservationService(dataStore);

        dataStore.getTables().add(new RestaurantTable(
                1L, "T1", 4, Zone.MAIN_HALL, 100, 100,
                false, false, false, true
        ));

        dataStore.getTables().add(new RestaurantTable(
                2L, "T2", 2, Zone.MAIN_HALL, 200, 100,
                true, true, false, true
        ));
    }

    @Test
    void shouldDetectReservationOverlap() {
        LocalDateTime requestedStart = LocalDateTime.of(2026, 3, 20, 19, 0);
        LocalDateTime requestedEnd = LocalDateTime.of(2026, 3, 20, 21, 0);

        LocalDateTime existingStart = LocalDateTime.of(2026, 3, 20, 20, 0);
        LocalDateTime existingEnd = LocalDateTime.of(2026, 3, 20, 22, 0);

        boolean overlaps = reservationService.overlaps(
                requestedStart,
                requestedEnd,
                existingStart,
                existingEnd
        );

        assertTrue(overlaps);
    }

    @Test
    void shouldReturnFalseWhenTableIsNotAvailable() {
        dataStore.getReservations().add(new Reservation(
                dataStore.nextReservationId(),
                1L,
                LocalDateTime.of(2026, 3, 20, 18, 0),
                LocalDateTime.of(2026, 3, 20, 20, 0),
                4
        ));

        boolean available = reservationService.isTableAvailable(
                1L,
                LocalDateTime.of(2026, 3, 20, 19, 0),
                LocalDateTime.of(2026, 3, 20, 21, 0)
        );

        assertFalse(available);
    }

    @Test
    void shouldCreateReservationWhenTableIsAvailable() {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setTableId(1L);
        request.setDate(LocalDate.now().plusDays(1));
        request.setTime(LocalTime.of(19, 0));
        request.setPartySize(4);

        ReservationResponse response = reservationService.createReservation(request);

        assertNotNull(response);
        assertEquals(1L, response.getTableId());
        assertEquals("Reservation created successfully.", response.getMessage());
        assertEquals(1, dataStore.getReservations().size());
    }

    @Test
    void shouldThrowExceptionWhenReservationConflicts() {
        LocalDate reservationDate = LocalDate.now().plusDays(1);

        dataStore.getReservations().add(new Reservation(
                dataStore.nextReservationId(),
                1L,
                LocalDateTime.of(reservationDate, LocalTime.of(18, 0)),
                LocalDateTime.of(reservationDate, LocalTime.of(20, 0)),
                4
        ));

        CreateReservationRequest request = new CreateReservationRequest();
        request.setTableId(1L);
        request.setDate(reservationDate);
        request.setTime(LocalTime.of(19, 0));
        request.setPartySize(4);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(request)
        );

        assertEquals("Selected table is not available for the chosen time.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPartySizeExceedsCapacity() {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setTableId(2L);
        request.setDate(LocalDate.now().plusDays(1));
        request.setTime(LocalTime.of(19, 0));
        request.setPartySize(5);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(request)
        );

        assertEquals("Party size exceeds table capacity.", exception.getMessage());
    }
}