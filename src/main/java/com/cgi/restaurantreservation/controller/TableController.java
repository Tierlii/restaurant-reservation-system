package com.cgi.restaurantreservation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.restaurantreservation.dto.SearchRequest;
import com.cgi.restaurantreservation.dto.SearchResponse;
import com.cgi.restaurantreservation.model.RestaurantTable;
import com.cgi.restaurantreservation.service.TableService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/tables")
    public List<RestaurantTable> getAllTables() {
        return tableService.getAllTables();
    }

    @PostMapping("/search")
    public SearchResponse searchTables(@Valid @RequestBody SearchRequest request) {
        return tableService.searchTables(request);
    }
}