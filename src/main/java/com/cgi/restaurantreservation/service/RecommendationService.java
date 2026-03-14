package com.cgi.restaurantreservation.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cgi.restaurantreservation.model.Preference;
import com.cgi.restaurantreservation.model.RestaurantTable;

@Service
public class RecommendationService {

    private static final double BASE_SCORE = 100.0;
    private static final double CAPACITY_PENALTY_PER_EXTRA_SEAT = 10.0;
    private static final double PREFERENCE_BONUS = 15.0;

    public double calculateScore(RestaurantTable table, int partySize, List<Preference> preferences) {
        double score = BASE_SCORE - ((table.getCapacity() - partySize) * CAPACITY_PENALTY_PER_EXTRA_SEAT);

        if (preferences == null || preferences.isEmpty()) {
            return score;
        }

        for (Preference preference : preferences) {
            score += getPreferenceBonus(table, preference);
        }

        return score;
    }

    public Optional<RestaurantTable> findBestTable(List<RestaurantTable> availableTables,
                                                   int partySize,
                                                   List<Preference> preferences) {
        return availableTables.stream()
                .sorted(Comparator
                        .comparingDouble((RestaurantTable table) -> calculateScore(table, partySize, preferences))
                        .reversed()
                        .thenComparingInt(RestaurantTable::getCapacity)
                        .thenComparing(RestaurantTable::getId))
                .findFirst();
    }

    private double getPreferenceBonus(RestaurantTable table, Preference preference) {
        return switch (preference) {
            case QUIET -> table.isQuiet() ? PREFERENCE_BONUS : 0.0;
            case WINDOW -> table.isWindowSeat() ? PREFERENCE_BONUS : 0.0;
            case NEAR_KIDS_AREA -> table.isNearKidsArea() ? PREFERENCE_BONUS : 0.0;
            case ACCESSIBLE -> table.isAccessible() ? PREFERENCE_BONUS : 0.0;
        };
    }
}