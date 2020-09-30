package com.delivery.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {
    public enum State {
        CREATING,
        STARTED,
        FINISHED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    private List<CityInTrip> routeList = new ArrayList<>();

    @OneToMany(mappedBy = "currentTrip", fetch = FetchType.LAZY)
    private List<Package> packageList = new ArrayList<>();

    private String car;

    @OneToOne
    private CityInTrip currentLocation;

    @ManyToOne
    @JoinColumn(name = "transporter_id", nullable = false)
    private User transporter;

    public Long getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<CityInTrip> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<CityInTrip> routeList) {
        this.routeList = routeList;
    }

    public List<Package> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<Package> packageList) {
        this.packageList = packageList;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public CityInTrip getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(CityInTrip currentLocation) {
        this.currentLocation = currentLocation;
    }

    public User getTransporter() {
        return transporter;
    }

    public void setTransporter(User transporter) {
        this.transporter = transporter;
    }
}
