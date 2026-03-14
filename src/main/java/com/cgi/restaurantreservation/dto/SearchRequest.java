package com.cgi.restaurantreservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cgi.restaurantreservation.model.Preference;
import com.cgi.restaurantreservation.model.Zone;

public class SearchRequest {

    private LocalDate date;
    private LocalTime time;
    private int partySize;
    private Zone zone;
    private List<Preference> preferences;

    public SearchRequest() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<Preference> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Preference> preferences) {
        this.preferences = preferences;
    }
}