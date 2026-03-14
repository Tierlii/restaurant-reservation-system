package com.cgi.restaurantreservation.dto;

import java.util.List;

public class SearchResponse {

    private List<TableAvailabilityDto> tables;
    private Long recommendedTableId;

    public SearchResponse() {
    }

    public SearchResponse(List<TableAvailabilityDto> tables, Long recommendedTableId) {
        this.tables = tables;
        this.recommendedTableId = recommendedTableId;
    }

    public List<TableAvailabilityDto> getTables() {
        return tables;
    }

    public void setTables(List<TableAvailabilityDto> tables) {
        this.tables = tables;
    }

    public Long getRecommendedTableId() {
        return recommendedTableId;
    }

    public void setRecommendedTableId(Long recommendedTableId) {
        this.recommendedTableId = recommendedTableId;
    }
}