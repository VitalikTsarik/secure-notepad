package com.delivery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Comparator;

@Entity
@Table(name = "city_join_trip")
public class CityInTrip {

    public static ComparatorByOrder comparator = new ComparatorByOrder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public CityInTrip() {
    }

    public CityInTrip(City city, Trip trip, int order) {
        this.city = city;
        this.trip = trip;
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "city_order")
    private int order;

    public Long getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public static class ComparatorByOrder implements Comparator<CityInTrip> {
        @Override
        public int compare(CityInTrip o1, CityInTrip o2) {
            return o1.getOrder() - o2.getOrder();
        }
    }
}
