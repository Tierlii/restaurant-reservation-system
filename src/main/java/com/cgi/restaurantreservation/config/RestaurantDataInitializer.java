package com.cgi.restaurantreservation.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.cgi.restaurantreservation.model.Reservation;
import com.cgi.restaurantreservation.model.RestaurantTable;
import com.cgi.restaurantreservation.model.Zone;
import com.cgi.restaurantreservation.repository.InMemoryDataStore;

import jakarta.annotation.PostConstruct;

@Component
public class RestaurantDataInitializer {

    private final InMemoryDataStore dataStore;
    private final Random random = new Random();

    public RestaurantDataInitializer(InMemoryDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @PostConstruct
    public void init() {
        initializeTables();
        initializeReservations();
    }

    private void initializeTables() {
        dataStore.getTables().clear();

        dataStore.getTables().add(new RestaurantTable(1L, "T1", 2, Zone.MAIN_HALL, 100, 100, true, true, false, true));
        dataStore.getTables().add(new RestaurantTable(2L, "T2", 2, Zone.MAIN_HALL, 200, 100, false, true, false, true));
        dataStore.getTables().add(new RestaurantTable(3L, "T3", 4, Zone.MAIN_HALL, 300, 100, false, false, false, true));
        dataStore.getTables().add(new RestaurantTable(4L, "T4", 4, Zone.MAIN_HALL, 400, 100, true, false, false, true));

        dataStore.getTables().add(new RestaurantTable(5L, "T5", 6, Zone.TERRACE, 100, 250, true, false, false, true));
        dataStore.getTables().add(new RestaurantTable(6L, "T6", 6, Zone.TERRACE, 200, 250, true, false, false, true));
        dataStore.getTables().add(new RestaurantTable(7L, "T7", 4, Zone.TERRACE, 300, 250, false, false, false, true));

        dataStore.getTables().add(new RestaurantTable(8L, "T8", 8, Zone.PRIVATE_ROOM, 450, 250, false, true, false, true));
        dataStore.getTables().add(new RestaurantTable(9L, "T9", 8, Zone.PRIVATE_ROOM, 550, 250, false, true, false, true));

        dataStore.getTables().add(new RestaurantTable(10L, "T10", 2, Zone.MAIN_HALL, 150, 400, true, true, true, true));
        dataStore.getTables().add(new RestaurantTable(11L, "T11", 4, Zone.MAIN_HALL, 300, 400, false, false, true, true));
        dataStore.getTables().add(new RestaurantTable(12L, "T12", 6, Zone.MAIN_HALL, 450, 400, false, false, true, true));
    }

    private void initializeReservations() {
        dataStore.getReservations().clear();

        List<RestaurantTable> tables = dataStore.getTables();

        List<LocalTime> slots = List.of(
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                LocalTime.of(16, 0),
                LocalTime.of(18, 0),
                LocalTime.of(20, 0),
                LocalTime.of(22, 0)
        );

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(14);

        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            generateReservationsForDay(current, slots, tables);
            current = current.plusDays(1);
        }
    }

    private void generateReservationsForDay(LocalDate date,
                                            List<LocalTime> slots,
                                            List<RestaurantTable> tables) {

        for (LocalTime slot : slots) {
            int numberOfReservationsForSlot = 3 + random.nextInt(4);

            for (int i = 0; i < numberOfReservationsForSlot; i++) {
                RestaurantTable table = tables.get(random.nextInt(tables.size()));

                LocalDateTime start = LocalDateTime.of(date, slot);
                LocalDateTime end = start.plusHours(2);

                boolean alreadyReserved = dataStore.getReservations().stream()
                        .anyMatch(existing ->
                                existing.getTableId().equals(table.getId())
                                        && overlaps(start, end,
                                        existing.getReservationStart(),
                                        existing.getReservationEnd())
                        );

                if (alreadyReserved) {
                    continue;
                }

                int maxPartySize = Math.max(1, table.getCapacity());
                int partySize = 1 + random.nextInt(maxPartySize);

                Reservation reservation = new Reservation(
                        dataStore.nextReservationId(),
                        table.getId(),
                        start,
                        end,
                        partySize
                );

                dataStore.getReservations().add(reservation);
            }
        }
    }

    private boolean overlaps(LocalDateTime requestedStart,
                             LocalDateTime requestedEnd,
                             LocalDateTime existingStart,
                             LocalDateTime existingEnd) {
        return requestedStart.isBefore(existingEnd) && requestedEnd.isAfter(existingStart);
    }
}
