package com.cgi.restaurantreservation.model;

public class RestaurantTable {

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

    public RestaurantTable() {
    }

    public RestaurantTable(Long id, String name, int capacity, Zone zone,
                           int x, int y,
                           boolean windowSeat,
                           boolean quiet,
                           boolean nearKidsArea,
                           boolean accessible) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.zone = zone;
        this.x = x;
        this.y = y;
        this.windowSeat = windowSeat;
        this.quiet = quiet;
        this.nearKidsArea = nearKidsArea;
        this.accessible = accessible;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Zone getZone() {
        return zone;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWindowSeat() {
        return windowSeat;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public boolean isNearKidsArea() {
        return nearKidsArea;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWindowSeat(boolean windowSeat) {
        this.windowSeat = windowSeat;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    public void setNearKidsArea(boolean nearKidsArea) {
        this.nearKidsArea = nearKidsArea;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }
}