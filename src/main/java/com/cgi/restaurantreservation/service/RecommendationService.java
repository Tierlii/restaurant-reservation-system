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
    private static final double EXACT_MATCH_BONUS = 35.0;
    private static final double EXTRA_SEAT_PENALTY = 12.0;

    private static final double WINDOW_BONUS = 18.0;
    private static final double QUIET_BONUS = 20.0;
    private static final double KIDS_AREA_BONUS = 18.0;
    private static final double ACCESSIBLE_BONUS = 22.0;

    private static final double QUIET_NEAR_KIDS_PENALTY = 12.0;

    public double calculateScore(RestaurantTable table, int partySize, List<Preference> preferences) {
        double score = BASE_SCORE;

        int extraSeats = table.getCapacity() - partySize;

        if (extraSeats == 0) {
            score += EXACT_MATCH_BONUS;
        } else {
            score -= extraSeats * EXTRA_SEAT_PENALTY;
        }

        if (preferences == null || preferences.isEmpty()) {
            return round(score);
        }

        boolean wantsQuiet = preferences.contains(Preference.QUIET);

        for (Preference preference : preferences) {
            score += getPreferenceBonus(table, preference);
        }

        if (wantsQuiet && table.isNearKidsArea()) {
            score -= QUIET_NEAR_KIDS_PENALTY;
        }

        return round(score);
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
            case WINDOW -> table.isWindowSeat() ? WINDOW_BONUS : 0.0;
            case QUIET -> table.isQuiet() ? QUIET_BONUS : 0.0;
            case NEAR_KIDS_AREA -> table.isNearKidsArea() ? KIDS_AREA_BONUS : 0.0;
            case ACCESSIBLE -> table.isAccessible() ? ACCESSIBLE_BONUS : 0.0;
        };
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}