package com.cgi.restaurantreservation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cgi.restaurantreservation.dto.SearchRequest;
import com.cgi.restaurantreservation.dto.SearchResponse;
import com.cgi.restaurantreservation.dto.TableAvailabilityDto;
import com.cgi.restaurantreservation.model.Preference;
import com.cgi.restaurantreservation.model.RestaurantTable;
import com.cgi.restaurantreservation.repository.InMemoryDataStore;

@Service
public class TableService {

    private static final int DEFAULT_RESERVATION_DURATION_HOURS = 2;

    private final InMemoryDataStore dataStore;
    private final ReservationService reservationService;
    private final RecommendationService recommendationService;

    public TableService(InMemoryDataStore dataStore,
                        ReservationService reservationService,
                        RecommendationService recommendationService) {
        this.dataStore = dataStore;
        this.reservationService = reservationService;
        this.recommendationService = recommendationService;
    }

    public List<RestaurantTable> getAllTables() {
        return dataStore.getTables();
    }

    public SearchResponse searchTables(SearchRequest request) {
        validateSearchRequest(request);

        LocalDateTime requestedStart = LocalDateTime.of(request.getDate(), request.getTime());
        LocalDateTime requestedEnd = requestedStart.plusHours(DEFAULT_RESERVATION_DURATION_HOURS);

        List<Preference> preferences = request.getPreferences() != null
                ? request.getPreferences()
                : Collections.emptyList();

        List<RestaurantTable> eligibleTables = dataStore.getTables().stream()
                .filter(table -> table.getCapacity() >= request.getPartySize())
                .filter(table -> request.getZone() == null || table.getZone() == request.getZone())
                .collect(Collectors.toList());

        List<RestaurantTable> availableTables = eligibleTables.stream()
                .filter(table -> reservationService.isTableAvailable(table.getId(), requestedStart, requestedEnd))
                .collect(Collectors.toList());

        Optional<RestaurantTable> recommendedTable = recommendationService.findBestTable(
                availableTables,
                request.getPartySize(),
                preferences
        );

        Long recommendedTableId = recommendedTable.map(RestaurantTable::getId).orElse(null);

        List<TableAvailabilityDto> tableDtos = new ArrayList<>();

        for (RestaurantTable table : dataStore.getTables()) {
            boolean eligible = table.getCapacity() >= request.getPartySize()
                    && (request.getZone() == null || table.getZone() == request.getZone());

            boolean available = eligible
                    && reservationService.isTableAvailable(table.getId(), requestedStart, requestedEnd);

            Double score = available
                    ? recommendationService.calculateScore(table, request.getPartySize(), preferences)
                    : null;

            TableAvailabilityDto dto = mapToDto(
                    table,
                    available,
                    table.getId().equals(recommendedTableId),
                    score
            );

            tableDtos.add(dto);
        }

        return new SearchResponse(tableDtos, recommendedTableId);
    }

    private TableAvailabilityDto mapToDto(RestaurantTable table,
                                          boolean available,
                                          boolean recommended,
                                          Double score) {
        TableAvailabilityDto dto = new TableAvailabilityDto();

        dto.setId(table.getId());
        dto.setName(table.getName());
        dto.setCapacity(table.getCapacity());
        dto.setZone(table.getZone());
        dto.setX(table.getX());
        dto.setY(table.getY());
        dto.setWindowSeat(table.isWindowSeat());
        dto.setQuiet(table.isQuiet());
        dto.setNearKidsArea(table.isNearKidsArea());
        dto.setAccessible(table.isAccessible());

        dto.setAvailable(available);
        dto.setRecommended(recommended);
        dto.setScore(score);

        return dto;
    }

    private void validateSearchRequest(SearchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Search request must not be null.");
        }

        if (request.getDate() == null) {
            throw new IllegalArgumentException("Search date is required.");
        }

        if (request.getTime() == null) {
            throw new IllegalArgumentException("Search time is required.");
        }

        if (request.getPartySize() <= 0) {
            throw new IllegalArgumentException("Party size must be greater than zero.");
        }
    }
}