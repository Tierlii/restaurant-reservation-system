package com.cgi.restaurantreservation.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.cgi.restaurantreservation.model.Preference;
import com.cgi.restaurantreservation.model.RestaurantTable;
import com.cgi.restaurantreservation.model.Zone;

class RecommendationServiceTest {

    private final RecommendationService recommendationService = new RecommendationService();

    @Test
    void shouldPreferBetterCapacityMatch() {
        RestaurantTable twoSeatTable = new RestaurantTable(
                1L, "T1", 2, Zone.MAIN_HALL, 100, 100,
                false, false, false, true
        );

        RestaurantTable fourSeatTable = new RestaurantTable(
                2L, "T2", 4, Zone.MAIN_HALL, 200, 100,
                false, false, false, true
        );

        double twoSeatScore = recommendationService.calculateScore(
                twoSeatTable, 2, List.of()
        );

        double fourSeatScore = recommendationService.calculateScore(
                fourSeatTable, 2, List.of()
        );

        assertTrue(twoSeatScore > fourSeatScore);
    }

    @Test
    void shouldIncreaseScoreWhenPreferenceMatches() {
        RestaurantTable quietWindowTable = new RestaurantTable(
                1L, "T1", 4, Zone.MAIN_HALL, 100, 100,
                true, true, false, true
        );

        RestaurantTable regularTable = new RestaurantTable(
                2L, "T2", 4, Zone.MAIN_HALL, 200, 100,
                false, false, false, true
        );

        double preferredScore = recommendationService.calculateScore(
                quietWindowTable,
                4,
                List.of(Preference.WINDOW, Preference.QUIET)
        );

        double regularScore = recommendationService.calculateScore(
                regularTable,
                4,
                List.of(Preference.WINDOW, Preference.QUIET)
        );

        assertTrue(preferredScore > regularScore);
    }

    @Test
    void shouldFindBestTableFromAvailableTables() {
        RestaurantTable table1 = new RestaurantTable(
                1L, "T1", 4, Zone.MAIN_HALL, 100, 100,
                false, false, false, true
        );

        RestaurantTable table2 = new RestaurantTable(
                2L, "T2", 4, Zone.MAIN_HALL, 200, 100,
                true, true, false, true
        );

        Optional<RestaurantTable> bestTable = recommendationService.findBestTable(
                List.of(table1, table2),
                4,
                List.of(Preference.WINDOW, Preference.QUIET)
        );

        assertTrue(bestTable.isPresent());
        assertEquals(2L, bestTable.get().getId());
    }

    @Test
    void shouldPreferSmallerCapacityWhenScoresAreEqual() {
        RestaurantTable smallerTable = new RestaurantTable(
                1L, "T1", 4, Zone.MAIN_HALL, 100, 100,
                false, false, false, true
        );

        RestaurantTable largerTable = new RestaurantTable(
                2L, "T2", 6, Zone.MAIN_HALL, 200, 100,
                false, false, false, true
        );

        Optional<RestaurantTable> bestTable = recommendationService.findBestTable(
                List.of(smallerTable, largerTable),
                4,
                List.of()
        );

        assertTrue(bestTable.isPresent());
        assertEquals(1L, bestTable.get().getId());
    }
}