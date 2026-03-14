package com.cgi.restaurantreservation.dto;

import com.cgi.restaurantreservation.model.Zone;

public class TableAvailabilityDto {

    private Long id;
    private String name;
    private int capacity;
    private Zone zone;

    private int x;
    private int y;

    private boolean windowSeat;
    private boolean quiet;
    private boolean nearKidsArea;
    private boolean accessible;

    private boolean available;
    private boolean recommended;
    private Double score;

    public TableAvailabilityDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWindowSeat() {
        return windowSeat;
    }

    public void setWindowSeat(boolean windowSeat) {
        this.windowSeat = windowSeat;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    public boolean isNearKidsArea() {
        return nearKidsArea;
    }

    public void setNearKidsArea(boolean nearKidsArea) {
        this.nearKidsArea = nearKidsArea;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}