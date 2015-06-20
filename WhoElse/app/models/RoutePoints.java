package models;

import play.db.jpa.JPA;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class RoutePoints {

    @Id
    @GeneratedValue
    public Integer pointId;

    public Integer routeId;
    public Double lat;
    public Double lng;
    public String dateTime;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }
}