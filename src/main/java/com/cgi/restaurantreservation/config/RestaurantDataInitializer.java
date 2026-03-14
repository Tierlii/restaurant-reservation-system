package com.cgi.restaurantreservation.config;

import java.time.LocalDateTime;
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

    public RestaurantDataInitializer(InMemoryDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @PostConstruct
    public void init() {

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


        Random random = new Random();

        for (int i = 0; i < 8; i++) {

            Long tableId = (long) (random.nextInt(12) + 1);

            LocalDateTime start = LocalDateTime.now()
                    .plusHours(random.nextInt(6));

            LocalDateTime end = start.plusHours(2);

            Reservation reservation = new Reservation(
                    dataStore.nextReservationId(),
                    tableId,
                    start,
                    end,
                    random.nextInt(4) + 2
            );

            dataStore.getReservations().add(reservation);
        }
    }
}